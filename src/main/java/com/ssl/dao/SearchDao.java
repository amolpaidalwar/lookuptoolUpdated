package com.ssl.dao;

import java.util.Date;
import java.util.List;

import com.ssl.model.AsinModel;
import com.ssl.model.SchemePriceModel;

public interface SearchDao {

	public List<AsinModel> getAsinData(String sku,String ean,String asin,String dept,int PageNumber,int max);
	
	public int getAsinDataCount(String sku,String ean,String asin,String dept);
	
	public List<SchemePriceModel> getSchemePriceData(Date startDate,Date endDate);
	
	public List<AsinModel> getAllSearchedAsinData(String sku,String ean,String asin,String dept);

	public List<SchemePriceModel> getSchemePrice(String sku, String eventId, String sCode, String dateSubmitted,
			Integer pageNumber, Integer pageDisplayLength);

	public int getPriceDataCount(String sku, String eventId, String sCode, String dateSubmitted);
	
	public List<SchemePriceModel> getAllSearchPriceData(String sku, String eventId, String sCode, String dateSubmitted);
	
	/////////////////
	
	public List<SchemePriceModel> getSearchPriceData(String sku, String dateSubmitted);
	
	public List<SchemePriceModel> getPriceData(String sku,String dateSubmitted,Integer pageNumber, Integer pageDisplayLength);
	
	public int getDataCount(String sku,String dateSubmitted);
	
}
