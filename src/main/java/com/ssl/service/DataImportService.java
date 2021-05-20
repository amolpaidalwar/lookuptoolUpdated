package com.ssl.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.ssl.data.SalesData;
import com.ssl.data.SchemePriceData;
import com.ssl.model.AsinModel;

public interface DataImportService {
	public Map<String, List<AsinModel>> importAsinData(File file, String userName);
	public Map<String, List<SchemePriceData>> importPriceData(File file, String userName);
	public Map<String, List<SalesData>> importSalesData(File file, String name);
}
