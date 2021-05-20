package com.ssl.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssl.dao.SearchDao;
import com.ssl.data.PriceFeed;
import com.ssl.data.transformation.DataTransformation;
import com.ssl.model.AsinModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.utils.SslDataUtil;
import com.ssl.utils.SslDateUtils;

@SuppressWarnings("unused")
@Service
public class SearchServiceImpl {


	private static Logger LOG=Logger.getLogger(SearchServiceImpl.class);
	@Autowired
	private SearchDao searchDaoImpl;
	
	public List<AsinModel> getAsinData(String sku,String ean,String asin,String dept,int page,int max){
		return searchDaoImpl.getAsinData(sku, ean, asin, dept,page,max);
	}
	
	public List<AsinModel> getAsinData(String sku,String ean,String asin,String dept){
		return searchDaoImpl.getAllSearchedAsinData(sku, ean, asin, dept);
	}

	public List<PriceFeed> getPriceFeedData(String startDate,String endDate) {
		
		Date sDate = null;
		Date eDate = null;
		
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
			
			sDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			c.add(Calendar.DAY_OF_MONTH, -6);
			eDate = c.getTime();
			endDate  = SslDataUtil.getFormatter(sDate);
			startDate  = SslDataUtil.getFormatter(eDate);
		}
		 sDate = SslDateUtils.getFormatter(startDate);
		 eDate = SslDateUtils.getFormatter(endDate);	
		 
		 if(sDate.getTime()>eDate.getTime()){
			 Date tempDate = sDate;
			 sDate = eDate;
			 eDate = tempDate;
		 }
		List<SchemePriceModel> models = searchDaoImpl.getSchemePriceData(sDate,eDate);
		return DataTransformation.populatePriceFeed(models);
	
	}
	
	public int getAsinDatCount(String sku,String ean,String asin,String dept){
		return searchDaoImpl.getAsinDataCount(sku, ean, asin, dept);
	}

	public List<SchemePriceModel> getSchemePriceData(String sku, String eventId, String sCode, String dateSubmitted,
			Integer pageNumber, Integer pageDisplayLength) {
		
		return searchDaoImpl.getSchemePrice(sku, eventId, sCode, dateSubmitted,pageNumber,pageDisplayLength);
	}

	public int getPriceDatCount(String sku, String eventId, String sCode, String dateSubmitted) {
		return searchDaoImpl.getPriceDataCount(sku, eventId, sCode, dateSubmitted);
	}
	
	public List<SchemePriceModel> getAllSchemeData(String sku,String eventId,String schemeCode,String date){
		return searchDaoImpl.getAllSearchPriceData( sku,  eventId,  schemeCode,date);
	}
	
	///////////////////////////
	
	public List<SchemePriceModel> getSearchPriceData(String sku, String dateSubmitted){
		return searchDaoImpl.getSearchPriceData(sku, dateSubmitted);
	}
	
	public List<SchemePriceModel> getPriceData(String sku,String dateSubmitted,Integer pageNumber, Integer pageDisplayLength) {
	 return searchDaoImpl.getPriceData(sku, dateSubmitted, pageNumber, pageDisplayLength) ;
	}
	
	public int getDataCount(String sku, String dateSubmitted) {
		return searchDaoImpl.getDataCount(sku, dateSubmitted);
	}
}
