package com.ssl.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssl.model.AsinModel;
import com.ssl.model.SalesDataModel;
import com.ssl.model.SchemePriceModel;

public interface DataImportDao {

	public boolean importAsinData(List<AsinModel> asinDataList);
	
	public List<AsinModel>  getAllAsinData();
	
	public Map<String,SchemePriceModel>  getSchemePriceData();
	
	public boolean importPriceData(List<SchemePriceModel> successPrices,String fileName);
	
	public boolean importSalesData(List<SalesDataModel> successSalesData);

	public  Map<String,SchemePriceModel> getSchemePriceForSkuandDates(List<String> skus, List<Date> dates,Map<String,String> checkskuandDate);
}
