package com.ssl.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssl.constants.DataToolConstants;
import com.ssl.dao.DataImportDao;
import com.ssl.data.SalesData;
import com.ssl.data.SchemePriceData;
import com.ssl.data.transformation.DataTransformation;
import com.ssl.model.AsinModel;
import com.ssl.model.SalesDataModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.service.DataImportService;
import com.ssl.utils.SslDataUtil;
import com.ssl.utils.SslDateUtils;

@Service
public class DataImportServiceImpl implements DataImportService {

	private static final Logger LOG = Logger.getLogger(DataImportServiceImpl.class);
	@Autowired
	private DataImportDao dataImportDaoImpl;

	private static final String SLAES_DATA = "sales";
	private static final String EMPTY = "";
	private static final String EVENT_ID = "EventID";
	private static final String SCHEME_CODE = "Scheme Code";
	private static final String DISC_TYPE = "Discount Type";
	private static final String EXT_DISC_TYPE = "Extended Discount Type";

	/**
	 * 
	 * @param file
	 * @param userName
	 * @return
	 */

	public Map<String, List<AsinModel>> importAsinData(File file, String userName){

		List<AsinModel> errorAsinDataList = new ArrayList<>();
		Map<String, AsinModel> successUniqueAsinMap = new HashMap<>();
		Map<String, List<AsinModel>> allAsinDataMap = new HashMap<>();
		CSVParser parser = SslDataUtil.getParser(file);
		List<CSVRecord> records = null;

		try {
			records = parser != null ? parser.getRecords() : new ArrayList<>();
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			closeParser(parser);
		}
		LOG.info("Total Records in File ::"+records.size());
		for (CSVRecord csvRecord : records) {
			LOG.info("Record Number ::"+csvRecord.getRecordNumber());
			AsinModel asinData = new AsinModel();
			if(StringUtils.isEmpty(csvRecord.get(0)) && StringUtils.isEmpty(csvRecord.get(1)) && StringUtils.isEmpty(csvRecord.get(2))  && StringUtils.isEmpty(csvRecord.get(3)))
			     continue;

			asinData.setSku( (csvRecord.get("sku").length() < 21) ? csvRecord.get("sku") : DataToolConstants.SKU_EMPTY);
			asinData.setMrp(SslDataUtil.roundDecimalValue(csvRecord.get("mrp")));
			asinData.setDept(csvRecord.get("dept"));
			asinData.setHsnCode(csvRecord.get("hsn code"));
			asinData.setDept(csvRecord.get("dept"));
			asinData.setEan(csvRecord.get("ean"));
			asinData.setCreatedTime(new Date());
			asinData.setModifyTime(new Date());
			asinData.setUpdatedBy(userName);
			String active = csvRecord.get("isActive");
			asinData.setIsActive((!StringUtils.isEmpty(active) && (active.equalsIgnoreCase("true")
					|| active.equalsIgnoreCase("yes") || active.equalsIgnoreCase("1"))) ? 1 : 0);
			asinData.setAsin(
					!StringUtils.isEmpty(csvRecord.get("asin")) ? csvRecord.get("asin") : DataToolConstants.ASIN_EMPTY);
			if (asinData.getSku().equals(DataToolConstants.SKU_EMPTY) || asinData.getAsin().equals(DataToolConstants.ASIN_EMPTY) || asinData.getMrp() == -1) {
				errorAsinDataList.add(asinData);
			} else {
				successUniqueAsinMap.put(asinData.getSku(), asinData);
			}
		}
		List<AsinModel> successAsinDataList = new ArrayList<>(successUniqueAsinMap.values());
		allAsinDataMap.put(DataToolConstants.ASIN_SUCCESS_DATA, successAsinDataList);
		allAsinDataMap.put(DataToolConstants.ASIN_ERROR_DATA, errorAsinDataList);
		if(!CollectionUtils.isEmpty(successAsinDataList))
			dataImportDaoImpl.importAsinData(successAsinDataList);
		return allAsinDataMap;
	}

	/**
	 * 
	 * @param file
	 * @param userName
	 * @return
	 */
	public Map<String, List<SchemePriceData>> importPriceData(File file, String userName) {

		Map<String, List<SchemePriceData>> pricesMap = new HashMap<>();
		List<SchemePriceModel> errorPrrices = new ArrayList<>();
		List<AsinModel> asinList = dataImportDaoImpl.getAllAsinData();
		Map<String, Double> skuPrice = new HashMap<>();
		Map<String, SchemePriceModel> uniquePrices = new HashMap<>();

		for (AsinModel asinData : !CollectionUtils.isEmpty(asinList) ? asinList : new ArrayList<AsinModel>()) {
			skuPrice.put(asinData.getSku(), asinData.getMrp());
		}
		CSVParser parser = SslDataUtil.getParser(file);
		if (parser != null) {
			List<CSVRecord> records = new ArrayList<>();
			try {
				records = parser.getRecords();
			} catch (IOException e) {
				LOG.error(DataToolConstants.CAUSE, e);
			} finally {
				closeParser(parser);
			}
			
			if (file.getName().toUpperCase().contains("BNMSCHEME")) {
				loadBmsPricesData(records,userName,skuPrice,errorPrrices,uniquePrices);
			}else if(file.getName().toUpperCase().contains("AMZEXTDISCOUNT")){
				loadAmzextDiscountData(records,userName,errorPrrices, uniquePrices);
			}
			List<SchemePriceModel> successPrices = new ArrayList<>(uniquePrices.values());
			if(!CollectionUtils.isEmpty(successPrices))
				dataImportDaoImpl.importPriceData(successPrices,file.getName());
			pricesMap.put(DataToolConstants.SUCCESS_PRICES, DataTransformation.populateSchemePriceData(successPrices));
			pricesMap.put(DataToolConstants.ERROR_PRICES, DataTransformation.populateSchemePriceData(errorPrrices));
		}
		return pricesMap;
	}

	private void getAllPriceRecords(SchemePriceModel schemePrice, List<Date> dates, List<SchemePriceModel> errorPrrices,
			Map<String, SchemePriceModel> uniquePrices) {

		for (Date date : dates) {
			SchemePriceModel newSchemePrice = populatePriceData(schemePrice);
			newSchemePrice.setEffectiveDate(date);

			if (newSchemePrice.getSku().equals(DataToolConstants.SKU_EMPTY) || newSchemePrice.getDiscValue() == -1 ||(!StringUtils.isEmpty(newSchemePrice.getDiscType()) && newSchemePrice.getDiscType().equals(DataToolConstants.DISC_TYPE_EMPTY) )||
					((!StringUtils.isEmpty(newSchemePrice.getSchemeCode()) || !StringUtils.isEmpty(newSchemePrice.getEventId()))
							&& newSchemePrice.getSchemeCode().equals(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY)	&& newSchemePrice.getEventId().equals(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY))
					|| newSchemePrice.getExtSellPrice() == -1 || newSchemePrice.getExtDiscValue() == -1
					|| (!StringUtils.isEmpty(newSchemePrice.getExtDiscType())
							&& newSchemePrice.getExtDiscType().equals(DataToolConstants.EXT_DISC_TYPE_EMPTY)
							|| (!StringUtils.isEmpty(newSchemePrice.getSslLiability()) && newSchemePrice
									.getSslLiability().equals(DataToolConstants.SSL_LIABILITY_EMPTY)))) {
				errorPrrices.add(newSchemePrice);
			} else {

				if (!StringUtils.isEmpty(newSchemePrice.getSchemeCode()) && newSchemePrice.getSchemeCode().equals(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY))
					newSchemePrice.setSchemeCode(null);
				if (!StringUtils.isEmpty( newSchemePrice.getEventId()) && newSchemePrice.getEventId().equals(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY))
					newSchemePrice.setEventId(null);
				uniquePrices.put(String.valueOf(schemePrice.getSku()) + "_" + SslDataUtil.getFormatter(date),
						newSchemePrice);
			}
		}
	}

	private void calculateSellingPrice(SchemePriceModel schemePrice, Map<String, Double> skuPrice) {

		double mrp = skuPrice.get(schemePrice.getSku());
		double sellingPrice;
		if (schemePrice.getDiscType().equalsIgnoreCase("Percentage")) {
			sellingPrice = mrp -  (mrp * schemePrice.getDiscValue() / 100);
			schemePrice.setMrp(mrp);
			schemePrice.setSellPrice(sellingPrice);
		} else {
			sellingPrice = schemePrice.getDiscValue();
			schemePrice.setMrp(mrp);
			schemePrice.setSellPrice(sellingPrice);
		}

	}

	private SchemePriceModel populatePriceData(SchemePriceModel schemePrice) {
		SchemePriceModel newSchemePrice = new SchemePriceModel();
		newSchemePrice.setSku(schemePrice.getSku());
		newSchemePrice.setSchemeCode(schemePrice.getSchemeCode());
		newSchemePrice.setEventId(schemePrice.getEventId());
		newSchemePrice.setMrp(schemePrice.getMrp());
		newSchemePrice.setDiscType(schemePrice.getDiscType());
		newSchemePrice.setDiscValue(schemePrice.getDiscValue());
		newSchemePrice.setExtDiscType(schemePrice.getExtDiscType());
		newSchemePrice.setExtDiscValue(schemePrice.getExtDiscValue());
		newSchemePrice.setSellPrice(schemePrice.getSellPrice());
		newSchemePrice.setExtSellPrice(schemePrice.getExtSellPrice());
		newSchemePrice.setPromoDesc(schemePrice.getPromoDesc());
		newSchemePrice.setSslLiability(schemePrice.getSslLiability());
		newSchemePrice.setCreatedTime(schemePrice.getCreatedTime());
		newSchemePrice.setModifiedBy(schemePrice.getModifiedBy());
		newSchemePrice.setModifiedTme(schemePrice.getModifiedTme());

		return newSchemePrice;
	}

	public Map<String, List<SalesData>> importSalesData(File file, String name) {

		List<SalesDataModel> successSalesData = new ArrayList<>();
		List<SalesDataModel> errorSalesData = new ArrayList<>();
		Map<String, List<SalesData>> allSalesData = new HashMap<>();

		CSVParser parser = SslDataUtil.getParser(file);
		if (parser != null) {
			List<CSVRecord> records = new ArrayList<>();
			try {
				records = parser.getRecords();
			} catch (IOException e) {
				LOG.error(DataToolConstants.CAUSE, e);
			} finally {
				closeParser(parser);
			}
			loadSalesData(records,successSalesData,errorSalesData,name);
			if(!CollectionUtils.isEmpty(successSalesData))
				dataImportDaoImpl.importSalesData(successSalesData);
			allSalesData.put(DataToolConstants.SUCCESS_SALES, DataTransformation.populateSalesData(successSalesData));
			allSalesData.put(DataToolConstants.ERROR_SALES, DataTransformation.populateSalesData(errorSalesData));
		}

		return allSalesData;
	}
	
	
	private void loadSalesData(List<CSVRecord> records,List<SalesDataModel> successSalesData,List<SalesDataModel> errorSalesData,String name){
		
		List<String> skus = new ArrayList<>();
		List<Date> dates = new ArrayList<>();
		Map<String,String> checkSkuDateMap = new HashMap<>();
		for (CSVRecord csvRecord : records) {
			
			if(StringUtils.isEmpty(csvRecord.get(0)) && StringUtils.isEmpty(csvRecord.get(1)) && StringUtils.isEmpty(csvRecord.get(2))  && StringUtils.isEmpty(csvRecord.get(3)))
			     continue;
			
			SalesDataModel salesModel = new SalesDataModel();
			salesModel.setInvoiceNumber(csvRecord.get("Invoice Number"));
			salesModel.setConditionType(csvRecord.get("Condition"));
			salesModel.setOrderId(csvRecord.get("Order Id"));
			salesModel.setShipmentId(csvRecord.get("Shipment Id"));
			salesModel.setShipmentItemId(csvRecord.get("Shipment Item Id"));
			salesModel.setQuantity(!StringUtils.isEmpty(csvRecord.get("Quantity")) ?  Integer.parseInt(csvRecord.get("Quantity")) : 0);
			salesModel.setItemSerialNumber(csvRecord.get("Item Serial Number"));
			salesModel.setAsin(csvRecord.get("ASIN"));
			salesModel.setSku(csvRecord.get("SKU"));
			salesModel.setProductTaxCode(csvRecord.get("Product tax code"));
			salesModel.setBillingCity(csvRecord.get("Billing City"));
			salesModel.setBillingState(csvRecord.get("Billing State"));
			salesModel.setBillingCountry(csvRecord.get("Billing Country"));
			salesModel.setBillingPostalCode(csvRecord.get("Billing Postal Code"));
			salesModel.setShipFromCity(csvRecord.get("Ship From City"));
			salesModel.setShipFromState(csvRecord.get("Ship From State"));
			salesModel.setShipFromCountry(csvRecord.get("Ship From Country"));
			salesModel.setShipFromPostalCode(csvRecord.get("Ship From Postal Code"));
			salesModel.setShipToCity(csvRecord.get("Ship To City"));
			salesModel.setShipToState(csvRecord.get("Ship To State"));
			salesModel.setShipToCountry(csvRecord.get("Ship To Country"));
			salesModel.setShipToPostalCode(csvRecord.get("Ship To Postal Code"));
			salesModel.setTaxType(csvRecord.get("Tax Type"));
			salesModel.setTaxRate(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Tax Rate"), SLAES_DATA));
			salesModel.setPrincipalAmount(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Principal Amount"), SLAES_DATA));
			salesModel.setPrincipalTax(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Principal Tax"), SLAES_DATA));
			salesModel.setShippingAmount(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Shipping Amount"), SLAES_DATA));
			salesModel.setShippingTax(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Shipping Tax"), SLAES_DATA));
			salesModel.setGiftWrapAmount(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Gift Wrap Amount"), SLAES_DATA));
			salesModel.setGiftWrapAmount(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Gift Wrap Amount"), SLAES_DATA));
			salesModel.setGiftWrapTax(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Gift Wrap Tax"), SLAES_DATA));
			salesModel
					.setItemPromoDiscount(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Item Promo Discount"), SLAES_DATA));
			salesModel.setShippingPromoDiscount(
					SslDataUtil.rePlaceSplCharacters(csvRecord.get("Shipping Promo Discount"), SLAES_DATA));
			salesModel.setWarehouseID(csvRecord.get("Warehouse ID"));
			salesModel.setDeliveryServiceLevel(csvRecord.get("Delivery Service Level"));
			salesModel.setFulfillmentChannel(csvRecord.get("Fulfillment Channel"));
			salesModel.setPaymentMethodCode(csvRecord.get("Payment Method Code"));
			salesModel.setTransactionDate(SslDateUtils.getFormatter(csvRecord.get("Transaction Date")));
			salesModel.setShipmentDate(csvRecord.get("Shipment Date"));
			salesModel.setOrderDate(SslDateUtils.getFormatter(csvRecord.get("Order Date")));
			salesModel.setCreateTime(new Date());
			salesModel.setModifyTime(new Date());
			salesModel.setModifiedBy(name);
			salesModel.setTransactionType(csvRecord.get("Transaction Type"));
		
			if (salesModel.getSku().equals(DataToolConstants.SKU_EMPTY) || null == salesModel.getOrderDate()) {
				errorSalesData.add(salesModel);
			} else {
				skus.add(salesModel.getSku());
				dates.add(salesModel.getOrderDate());
				checkSkuDateMap.put(salesModel.getSku() + "_" + SslDataUtil.getFormatter(salesModel.getOrderDate()),
						salesModel.getSku() + "_" + SslDataUtil.getFormatter(salesModel.getOrderDate()));
				successSalesData.add(salesModel);
			}
		}
		
		 Map<String,SchemePriceModel> map =  dataImportDaoImpl.getSchemePriceForSkuandDates(skus,dates,checkSkuDateMap);
		 
		 for (SalesDataModel salesModel : successSalesData) {
			 SchemePriceModel price = map.get(salesModel.getSku()+"_"+SslDataUtil.getFormatter(salesModel.getOrderDate()));
			if (null != price) {
				salesModel.setSellingPrice(price.getSellPrice());
				salesModel.setExtSellPrice(price.getExtSellPrice());
				salesModel.setEventId(!StringUtils.isEmpty(price.getEventId()) ? price.getEventId() : null);
				salesModel.setSchemeCode(!StringUtils.isEmpty(price.getSchemeCode()) ? price.getSchemeCode() : null);
			}
		}
		 
	}

	
	private void loadBmsPricesData(List<CSVRecord> records,String userName,Map<String, Double> skuPrice,List<SchemePriceModel> errorPrrices,Map<String, SchemePriceModel> uniquePrices){
		
		for (CSVRecord csvRecord : records) {
			
			if(StringUtils.isEmpty(csvRecord.get(0)) && StringUtils.isEmpty(csvRecord.get(1)) && StringUtils.isEmpty(csvRecord.get(2))  && StringUtils.isEmpty(csvRecord.get(3)))
			     continue;
			
			SchemePriceModel schemePrice = new SchemePriceModel();
			if (csvRecord.get("SKU").length() < 21) {
				schemePrice.setSku(csvRecord.get("SKU"));
			} else {
				schemePrice.setSku(DataToolConstants.SKU_EMPTY);
			}
			schemePrice.setCreatedTime(new Date());
			schemePrice.setModifiedTme(new Date());
			schemePrice.setModifiedBy(userName);
			
			schemePrice.setSchemeCode(
					!StringUtils.isEmpty(csvRecord.get(SCHEME_CODE)) && csvRecord.get(SCHEME_CODE).length() < 21
							? csvRecord.get(SCHEME_CODE) : DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY);
			schemePrice.setEventId(
					!StringUtils.isEmpty(csvRecord.get(EVENT_ID)) && csvRecord.get(EVENT_ID).length() < 21
							? csvRecord.get(EVENT_ID) : DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY);
			schemePrice.setDiscType(
					!StringUtils.isEmpty(csvRecord.get(DISC_TYPE)) && csvRecord.get(DISC_TYPE).length() < 21
							? csvRecord.get(DISC_TYPE) : DataToolConstants.DISC_TYPE_EMPTY);

			schemePrice.setDiscValue(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Discount Value"), EMPTY));

			schemePrice.setPromoDesc(csvRecord.get("Promotion Description"));
			if (skuPrice.containsKey(schemePrice.getSku())) {
				calculateSellingPrice(schemePrice, skuPrice);
			}
			List<Date> dates = SslDateUtils.getEffectiveDates(csvRecord.get("Promo Valid FromDateTime"),
					csvRecord.get("Promo Valid ToDateTime"));
			if (!CollectionUtils.isEmpty(dates)) {
				getAllPriceRecords(schemePrice, dates, errorPrrices, uniquePrices);
			} else {
				schemePrice.setEffectiveDate(null);
				errorPrrices.add(schemePrice);
			}
		}
	}
	
	private void loadAmzextDiscountData(List<CSVRecord> records,String userName,List<SchemePriceModel> errorPrrices,Map<String, SchemePriceModel> uniquePrices){
		
		for (CSVRecord csvRecord : records) {

			if(StringUtils.isEmpty(csvRecord.get(0)) && StringUtils.isEmpty(csvRecord.get(1)) && StringUtils.isEmpty(csvRecord.get(2))  && StringUtils.isEmpty(csvRecord.get(3)))
			     continue;
			
			SchemePriceModel schemePrice = new SchemePriceModel();
			if (csvRecord.get("SKU").length() < 21) {
				
				schemePrice.setSku(csvRecord.get("SKU"));
			} else {
				schemePrice.setSku(DataToolConstants.SKU_EMPTY);
			}
			schemePrice.setCreatedTime(new Date());
			schemePrice.setModifiedTme(new Date());
			schemePrice.setModifiedBy(userName);


			schemePrice.setSslLiability(!StringUtils.isEmpty(csvRecord.get("SSL Liability"))
					? csvRecord.get("SSL Liability") : DataToolConstants.SSL_LIABILITY_EMPTY);

			schemePrice.setExtSellPrice(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Extended Selling Price"), EMPTY));
			schemePrice.setExtDiscType(!StringUtils.isEmpty(csvRecord.get(EXT_DISC_TYPE))
					&& csvRecord.get(EXT_DISC_TYPE).length() < 21 ? csvRecord.get(EXT_DISC_TYPE)
							: DataToolConstants.EXT_DISC_TYPE_EMPTY);
			schemePrice.setExtDiscValue(SslDataUtil.rePlaceSplCharacters(csvRecord.get("Extended Discount Value"), EMPTY));

			List<Date> dates = SslDateUtils.getEffectiveDates(csvRecord.get("Promo Valid From DateTime"),
					csvRecord.get("Promo Valid To DateTime"));
			if (!CollectionUtils.isEmpty(dates)) {
				getAllPriceRecords(schemePrice, dates, errorPrrices, uniquePrices);
			} else {
				schemePrice.setEffectiveDate(null);
				errorPrrices.add(schemePrice);
			}
			
		}
		
	}
	
	private void closeParser(CSVParser parser) {
		if (parser != null) {
			try {
				parser.close();
			} catch (IOException e) {
				LOG.error(DataToolConstants.CAUSE, e);
			}
		}
	}

}
