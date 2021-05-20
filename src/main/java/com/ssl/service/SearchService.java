package com.ssl.service;

import java.util.List;

import com.ssl.data.PriceFeed;
import com.ssl.model.AsinModel;

public interface SearchService {

	public List<AsinModel> getAsinData(String sku,String ean,String asin,String dept,int page,int max);
	
	public List<PriceFeed> getPriceFeedData(String startDate,String endDate);
	
	public int getAsinDatCount(String sku,String ean,String asin,String dept);
	
	 
}
