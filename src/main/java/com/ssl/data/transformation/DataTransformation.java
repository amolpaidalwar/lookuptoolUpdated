package com.ssl.data.transformation;

import com.ssl.constants.DataToolConstants;
import com.ssl.data.AsinData;
import com.ssl.data.PriceFeed;
import com.ssl.data.SalesData;
import com.ssl.data.SchemePriceData;
import com.ssl.model.AsinModel;
import com.ssl.model.SalesDataModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.utils.SslDataUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataTransformation {

	private DataTransformation(){
		
	}
	
	
	public static SchemePriceModel populatePriceData(SchemePriceModel schemePrice){
		
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
		newSchemePrice.setModifiedTme( schemePrice.getModifiedTme());
		
		return newSchemePrice;
		
	}
	
	public static List<AsinData> populateAsinData(List<AsinModel> asinModelList){
		
		List<AsinData> asinDataList = new ArrayList<>();
		for (AsinModel asinModel : !CollectionUtils.isEmpty(asinModelList) ? asinModelList : new ArrayList<AsinModel>()) {
			AsinData data = new AsinData();
			data.setSku(asinModel.getSku());
			if(asinModel.getMrp()==-1){
				data.setMrp(DataToolConstants.MRP_EMPTY);
			}else{
				data.setMrp(String.valueOf(asinModel.getMrp()));
			}
			data.setAsin(asinModel.getAsin());
			data.setDept(asinModel.getDept());
			data.setEan(asinModel.getEan());
			data.setHsnCode(asinModel.getHsnCode());
			data.setCreatedTime(SslDataUtil.getFormatter(asinModel.getCreatedTime()));
			data.setModifyTime(SslDataUtil.getFormatter(asinModel.getModifyTime()));
			data.setUpdatedBy(asinModel.getUpdatedBy());
			data.setIsActive(String.valueOf(asinModel.getIsActive()));
			asinDataList.add(data);
		}
		return asinDataList;
	}
	
	
	public static List<SchemePriceData> populateSchemePriceData(List<SchemePriceModel> list){

		List<SchemePriceData> schemePriceList = new ArrayList<>();
		
		for (SchemePriceModel model : list) {
			SchemePriceData data =  new SchemePriceData();
			data.setSku(model.getSku());
			if(!StringUtils.isEmpty(model.getDiscType())  && model.getDiscType().equals(DataToolConstants.DISC_TYPE_EMPTY)){
				data.setDiscType(DataToolConstants.DISC_TYPE_EMPTY);
			}else{
				data.setDiscType(model.getDiscType());
			}
			if(model.getDiscValue()==-1){
				data.setDiscValue(DataToolConstants.DISC_VALUE_EMPTY);
			}else{
				data.setDiscValue(String.valueOf(model.getDiscValue()));
			}
			if(null == model.getEventId() || model.getEventId().equals(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY)){
				data.setEventId(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY);
			}else{
				data.setEventId(model.getEventId());
			}
			if(null ==  model.getSchemeCode() || model.getSchemeCode().equals(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY)){
				data.setSchemeCode(DataToolConstants.SCHEME_CODE_EVENT_ID_EMPTY);
			}else{
				data.setSchemeCode(model.getSchemeCode());
			}
			if(model.getExtSellPrice()==-1){
				data.setExtSellPrice(DataToolConstants.EXT_SELL_PRICE_EMPTY);
			}else{
				data.setExtSellPrice(String.valueOf(model.getExtSellPrice()));
			}
			
			if(null == model.getExtDiscType() ||  model.getExtDiscType().equals(DataToolConstants.EXT_DISC_TYPE_EMPTY)){
				data.setExtDiscType(DataToolConstants.EXT_DISC_TYPE_EMPTY);
			}else{
				
				data.setExtDiscType(model.getExtDiscType());
			}
			if(model.getExtDiscValue()==-1){
				data.setExtDiscValue("Extended discount Empty");
			}else{
				data.setExtDiscValue(String.valueOf(model.getExtDiscValue()));
			}
			if(null == model.getSslLiability() || model.getSslLiability().equals(DataToolConstants.SSL_LIABILITY_EMPTY)){
				data.setSslLiability(DataToolConstants.SSL_LIABILITY_EMPTY);
			}else{
				data.setSslLiability(model.getSslLiability());
			}	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(null == model.getEffectiveDate()){
				data.setEffectiveDate("data Format sholud be   following <br> (ddmmyyyy,ddmmyy,dmmmyy,dmmyy,dd-mm-yyyy,dd/mm/yyyy)");
			}else{
				data.setEffectiveDate(sdf.format(model.getEffectiveDate()));
			}
			if(model.getSellPrice()==-1){
				data.setSellPrice("Check ASIN DATA");
			}else{
				data.setSellPrice(String.valueOf(model.getSellPrice()));
			}
			if(model.getMrp()==-1){
				data.setMrp("Check ASIN DATA");
			}else{
				data.setMrp(String.valueOf(model.getMrp()));
			}
			data.setPromoDesc(model.getPromoDesc());
			schemePriceList.add(data);
		}
		return schemePriceList;
	}
	
	public static List<SalesData> populateSalesData(List<SalesDataModel> salesList){
		
		List<SalesData> salesDataList = new ArrayList<>();
		
		for (SalesDataModel model : salesList) {
			
			SalesData data = new SalesData();
			 
			data.setSku(model.getSku());
			
			data.setOrderDate(!StringUtils.isEmpty(SslDataUtil.getFormatter(model.getOrderDate()))
					? SslDataUtil.getFormatter(model.getOrderDate())
					: "Check Order Date and Date Format");
			
			data.setAsin(model.getAsin());
			data.setBillingCity(model.getBillingCity());
			data.setBillingCountry(model.getBillingCountry());
			data.setBillingPostalCode(model.getBillingPostalCode());
			data.setBillingState(model.getBillingState());
			data.setCondition(model.getConditionType());
			data.setDeliveryServiceLevel(model.getDeliveryServiceLevel());
			data.setEventId(model.getEventId());
			data.setExtSellPrice(String.valueOf(model.getExtSellPrice()));
			data.setFulfillmentChannel(model.getFulfillmentChannel());
			data.setGiftWrapAmount(String.valueOf(model.getGiftWrapAmount()));
			data.setGiftWrapTax(String.valueOf(model.getGiftWrapTax()));
			data.setInvoiceNumber(model.getInvoiceNumber());
			data.setItemPromoDiscount(String.valueOf(model.getItemPromoDiscount()));
			data.setItemSerialNumber(model.getItemSerialNumber());
			data.setOrderId(model.getOrderId());
			data.setPaymentMethodCode(model.getPaymentMethodCode());
			data.setPrincipalAmount(String.valueOf(model.getPrincipalAmount()));
			data.setPrincipalTax(String.valueOf(model.getPrincipalTax()));
			data.setProductTaxCode(model.getProductTaxCode());
			data.setQuantity(String.valueOf(model.getQuantity()));
			data.setSchemeCode(model.getSchemeCode());
			data.setSellingPrice(String.valueOf(model.getSellingPrice()));
			data.setShipFromCity(model.getShipFromCity());
			data.setShipFromCountry(model.getShipFromCountry());
			data.setShipFromPostalCode(model.getShipFromPostalCode());
			data.setShipFromState(model.getShipFromState());
			data.setShipmentDate(model.getShipmentDate());
			data.setShipmentId(model.getShipmentId());
			data.setShipmentItemId(model.getShipmentId());
			data.setShippingPromoDiscount(String.valueOf(model.getShippingPromoDiscount()));
			data.setShippingTax(String.valueOf(model.getShippingTax()));
			data.setShipToCity(model.getShipToCity());
			data.setShipToCountry(model.getShipToCountry());
			data.setShipToPostalCode(model.getShipToPostalCode());
			data.setShipToState(model.getShipToState());
			
			data.setTaxRate(String.valueOf(model.getTaxRate()));
			data.setTaxType(model.getTaxType());
			data.setTransactionDate(SslDataUtil.getFormatter(model.getTransactionDate()));
			data.setTransactionType(model.getTransactionType());
			data.setWarehouseID(model.getWarehouseID());
			salesDataList.add(data);
		}
		return salesDataList;
	}


	public static List<PriceFeed> populatePriceFeed(List<SchemePriceModel> models) {
		
		List<PriceFeed> priceFeeds = new ArrayList<>();
		for (SchemePriceModel model : models) {
			PriceFeed feed = new PriceFeed();
			
			feed.setItem_sku(model.getSku());
			
			feed.setStandard_price(String.valueOf(model.getMrp()));
			if(model.getExtSellPrice()!=0){
			  feed.setSale_price(String.valueOf(model.getExtSellPrice()));
			}else{
				feed.setSale_price(String.valueOf(model.getSellPrice()));
			}
			Date date = model.getEffectiveDate();
			feed.setStart_date(SslDataUtil.getFormatter(date));
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, 1);
			feed.setEnd_date(SslDataUtil.getFormatter(c.getTime()));
			priceFeeds.add(feed);
		}
		
		return priceFeeds;
		
	}
	
	
}
