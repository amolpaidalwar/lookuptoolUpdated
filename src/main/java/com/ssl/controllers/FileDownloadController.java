package com.ssl.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ssl.constants.DataToolConstants;
import com.ssl.data.AsinData;
import com.ssl.data.PriceFeed;
import com.ssl.data.SalesData;
import com.ssl.data.SchemePriceData;
import com.ssl.data.transformation.DataTransformation;
import com.ssl.model.AsinModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.service.impl.SearchServiceImpl;
import com.ssl.utils.SslDateUtils;

@Controller
public class FileDownloadController {

	private static Logger log=Logger.getLogger(FileDownloadController.class); 
	
	private  static final String SUCCESS= "success";
	private static final String ATTACHMENT = "attachment; filename=\"%s\"";
	private static final String HEADER_KEY = "Content-Disposition";
	
	@Autowired
	private SearchServiceImpl searchServiceImpl;
	
	 @SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadSalesData")
	public void downloadSalesData(@RequestParam("code") String code, HttpServletRequest request,HttpServletResponse response) {
		String csvFileName = "";
		response.setContentType("text/csv");
		HttpSession session = request.getSession();
		List<SalesData> salesDatalList = null;

		if (code.equals(SUCCESS)) {
			salesDatalList = (List<SalesData>) session.getAttribute(DataToolConstants.SUCCESS_SALES);
			csvFileName = "Sales_Success.csv";
		} else {
			salesDatalList = (List<SalesData>) session.getAttribute(DataToolConstants.ERROR_SALES);
			csvFileName = "Sales_Error.csv";
		}
		String headerValue = String.format(ATTACHMENT, csvFileName);
		response.setHeader(HEADER_KEY, headerValue);
		ICsvBeanWriter csvWriter = null;

		String[] header = { "invoiceNumber", "transactionDate", "transactionType", "condition", "orderId", "shipmentId",
				"shipmentDate", "orderDate", "shipmentItemId", "quantity", "itemSerialNumber", "asin", "sku",
				"productTaxCode", "billingCity", "billingState", "billingCountry", "billingPostalCode", "shipFromCity",
				"shipFromState", "shipFromCountry", "shipFromPostalCode", "shipToCity", "shipToState", "shipToCountry",
				"shipToPostalCode", "taxType", "taxRate", "principalAmount", "principalTax", "shippingAmount",
				"shippingTax", "giftWrapAmount", "giftWrapTax", "itemPromoDiscount", "shippingPromoDiscount",
				"warehouseID", "deliveryServiceLevel", "fulfillmentChannel", "paymentMethodCode", "sellingPrice",
				"extSellPrice", "schemeCode", "eventId" };

		if (!CollectionUtils.isEmpty(salesDatalList)) {
			writeCsvFile(header, salesDatalList, csvFileName, response);
		}

	}
	    
	 
	 
	    @RequestMapping(value="/submitDate")
	public String submitDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		if (SslDateUtils.searchDatesCalculation(startDate, endDate)) {
			session.setAttribute("priceFeedDate", startDate + "_" + endDate);
			List<PriceFeed> feeds = searchServiceImpl.getPriceFeedData(startDate, endDate);
			session.setAttribute("priceFeed", feeds);
			session.setAttribute("priceFeedSize", feeds.size());
			session.setAttribute("differDays", "");
		} else {
			session.setAttribute("differDays", "exeeded");
		}
		session.setAttribute("FeedPage", "");
		return "PriceFeed";
	}
	    
	    @RequestMapping(value="/getPriceFeed")
	    public String getPriceFeedPage(HttpServletRequest request){
	    	HttpSession session = request.getSession();
	    	session.setAttribute("FeedPage", "page only");
	    	return "PriceFeed";
	    }
	    
	    
	    @SuppressWarnings("unchecked")
		@RequestMapping(value="/downloadPriceFeed")
	    public void downloadPriceFeed(HttpServletRequest request,HttpServletResponse response){
	    	
	    	HttpSession session = request.getSession();
	    	
	    	String date = (String) session.getAttribute("priceFeedDate");
	    	String csvFileName = "Price_Feed_"+date.replace("/", "_")+".csv";
	    	
	         String headerValue = String.format(ATTACHMENT,
	                 csvFileName);
	         response.setHeader(HEADER_KEY, headerValue);
	         List<PriceFeed> feeds = (List<PriceFeed>) session.getAttribute("priceFeed");
	         String[] header = {"item_sku","standard_price","sale_price","start_date","end_date"};
	         if(!CollectionUtils.isEmpty(feeds)){
	        	 writeCsvFile(header,feeds,csvFileName,response);
	         }
	         
	    }
	    
	    
	    @RequestMapping(value="/downLoadSuccessorErrorAsin")
	    public void downloadAsinSuccessData(@RequestParam("code") String code,HttpServletRequest request,HttpServletResponse response){
	    	
	    	HttpSession session = request.getSession();
	    	String csvFileName = "";
	    	
	   
	        // paginationData
	         List<AsinData> asinData = null;
	         if(code.equals(SUCCESS)){
	           asinData = (List<AsinData>)session.getAttribute(DataToolConstants.ASIN_SUCCESS_DATA);
	           csvFileName = "AsinData_Success.csv";
	         }
	           else{
	        	 asinData = (List<AsinData>)session.getAttribute(DataToolConstants.ASIN_ERROR_DATA);
	        	 csvFileName = "AsinData_Error.csv";
	           }

		         String headerValue = String.format(ATTACHMENT,
		                 csvFileName);
		         response.setHeader(HEADER_KEY, headerValue);
	        	 String[] header = {"sku","asin","ean","mrp","hsnCode","dept","isActive","createdTime","modifyTime","updatedBy"};
	         if(!CollectionUtils.isEmpty(asinData)){
	        	 writeCsvFile(header,asinData,csvFileName,response);
	         }
	    }
	    
	    
	    @RequestMapping(value="/downLoadSuccessorErrorPrice")
	    public void downloadPriceSuccessData(@RequestParam("code") String code,HttpServletRequest request,HttpServletResponse response){
	    	
	    	HttpSession session = request.getSession();
	    	String csvFileName = "";
	    	
	         List<SchemePriceData> priceData = null;
	         if(code.equals(SUCCESS)){
	        	 priceData = (List<SchemePriceData>)session.getAttribute(DataToolConstants.SUCCESS_PRICES); 
	        	 csvFileName = "SchemePrice_success.csv";
	         }else{
	        	 priceData = (List<SchemePriceData>)session.getAttribute(DataToolConstants.ERROR_PRICES); 
	        	 csvFileName = "SchemePrice_error.csv";
	         
	         }
	         String headerValue = String.format(ATTACHMENT,
	                 csvFileName);
	         response.setHeader(HEADER_KEY, headerValue);
	          
	         
	         String[] header = {"sku","effectiveDate","mrp","discType","discValue","sellPrice","extDiscType","extDiscValue","extSellPrice","sslLiability",
	        		 "schemeCode","eventId","promoDesc"};
	         ICsvBeanWriter csvWriter =  null;

	         if(!CollectionUtils.isEmpty(priceData)){
	        	 writeCsvFile(header,priceData,csvFileName,response);
	         }
	    }
	    
	    
	@RequestMapping(value="/downloadAsinCsv")
	public void downloadAsinCsv(HttpServletRequest request, HttpServletResponse response) {

	    	List<AsinModel> asinModelList = new ArrayList<>();	
		HttpSession session = request.getSession();
		String csvFileName = "AsinData.csv";
		String headerValue = String.format(ATTACHMENT, csvFileName);
		response.setHeader(HEADER_KEY, headerValue);

		String sku = (String) session.getAttribute("sku");
		String ean = (String) session.getAttribute("ean");
		String dept = (String) session.getAttribute("dept");
		String asin = (String) session.getAttribute("asin");
		if(session.getAttribute("downloadOption").equals("true"))
			asinModelList = searchServiceImpl.getAsinData(sku, ean, asin, dept);
		List<AsinData> asinDataList = DataTransformation.populateAsinData(asinModelList);

		String[] header = { "sku", "asin", "ean", "mrp", "hsnCode", "dept", "isActive", "createdTime", "modifyTime",
				"updatedBy" };
		ICsvBeanWriter csvWriter = null;
		if (!CollectionUtils.isEmpty(asinDataList)) {
			writeCsvFile(header, asinDataList, csvFileName, response);
		}
	}
	    
	    private void writeCsvFile(String[] header,List<?> dataList,String csvFileName,HttpServletResponse response){
	    	
	    	 String headerValue = String.format(ATTACHMENT,
	                 csvFileName);
	         response.setHeader(HEADER_KEY, headerValue);
	    	 try(ICsvBeanWriter csvWriter =  new CsvBeanWriter(response.getWriter(),
		                CsvPreference.STANDARD_PREFERENCE);) {
	 				csvWriter.writeHeader(header);
	 				for (Object object : dataList) {
						if(object instanceof SchemePriceData){
							SchemePriceData data =(SchemePriceData)object;
							csvWriter.write(data, header);
						}else if (object instanceof AsinData) {
							AsinData data = (AsinData)object;
							csvWriter.write(data, header);
						}else if (object instanceof PriceFeed) {
							PriceFeed data = (PriceFeed)object;
							csvWriter.write(data, header);
						}else if (object instanceof SalesData) {
							SalesData data = (SalesData)object;
							csvWriter.write(data, header);
						}
					}
	 				csvWriter.close();
	 			} catch (IOException e) {
	 				log.error(DataToolConstants.CAUSE,e);
	 			}
	    }
	    
	    
	    
	    
	    @RequestMapping(value="/downloadSearchScheme")
		public void downloadSearchSchemeCsv(HttpServletRequest request, HttpServletResponse response) {

		    	List<SchemePriceModel> asinModelList = new ArrayList<>();	
			HttpSession session = request.getSession();
			String csvFileName = "SCHEME_PRICE.csv";
			String headerValue = String.format(ATTACHMENT, csvFileName);
			response.setHeader(HEADER_KEY, headerValue);

			String sku = (String) session.getAttribute("sku");
			String eventId = (String) session.getAttribute("eventId");
			String sCode = (String) session.getAttribute("sCode");
			String dateSubmitted = (String) session.getAttribute("dateSubmitted");
			if(session.getAttribute("priceDownloadOption").equals("true"))
				asinModelList = searchServiceImpl.getAllSchemeData(sku, eventId, sCode, dateSubmitted);
			List<SchemePriceData> asinDataList = DataTransformation.populateSchemePriceData(asinModelList);

			String[] header = {"sku","effectiveDate","mrp","discType","discValue","sellPrice","extDiscType","extDiscValue","extSellPrice","sslLiability",
	        		 "schemeCode","eventId","promoDesc"};
			ICsvBeanWriter csvWriter = null;
			if (!CollectionUtils.isEmpty(asinDataList)) {
				writeCsvFile(header, asinDataList, csvFileName, response);
			}
		}
	    
	    
	    @RequestMapping(value="/downloadHeaders")
	    public void downloadCsvHeaders(@RequestParam("code") String code,HttpServletRequest request, HttpServletResponse response){
	    	
	    	String outputResult = "";
	    	if(code.equals("asin")){
	    		outputResult = "sku,asin,ean,mrp,hsn code,dept,isActive";
	    	}else if (code.equals("BNMSCHEME")) {
	    		outputResult ="SKU,Brand,Discount Type,Discount Value,Promo Valid FromDateTime,Promo Valid ToDateTime,Scheme Code,EventID,Promotion Description";
			}else if (code.equals("AMZEXTDISCOUNT")) {
				outputResult ="SKU,Extended Selling Price,SSL Liability,Extended Discount Type,Extended Discount Value,Promo Valid From DateTime,Promo Valid To DateTime";
			}else if (code.equals("sales")) {
			outputResult = "Invoice Number,Transaction Date,Transaction Type,Condition,Order Id,Shipment Id,Shipment Date,Order Date,Shipment Item Id,Quantity,Item Serial Number,ASIN,SKU,Product tax code,Billing City,Billing State,Billing Country,Billing Postal Code,Ship From City,Ship From State,Ship From Country,Ship From Postal Code,Ship To City,Ship To State,Ship To Country,Ship To Postal Code,Tax Type,Tax Rate,Principal Amount,Principal Tax,Shipping Amount,Shipping Tax,Gift Wrap Amount,Gift Wrap Tax,Item Promo Discount,Shipping Promo Discount,Warehouse ID,Delivery Service Level,Fulfillment Channel,Payment Method Code";
			}
	    	    response.setContentType("text/csv");
	    	    response.setHeader("Content-Disposition", "attachment; filename="+code+".csv");
	    	    try
	    	    {
	    	        OutputStream outputStream = response.getOutputStream();
	    	        outputStream.write(outputResult.getBytes());
	    	        outputStream.flush();
	    	        outputStream.close();
	    	    }
	    	    catch(Exception e)
	    	    {
	    	    	log.error("Cause is {}",e);
	    	    }
	    }
		    
}
