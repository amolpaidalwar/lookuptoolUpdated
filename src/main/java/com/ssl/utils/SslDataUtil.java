package com.ssl.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssl.constants.DataToolConstants;

public class SslDataUtil {

	private static final Logger LOG=Logger.getLogger(SslDataUtil.class);
	private SslDataUtil(){
		
	}
	
	public static double rePlaceSplCharacters(String csvValue,String dataType) {

		double value;
		try {
			String discValue = !StringUtils.isEmpty(csvValue) ? csvValue : "";
			if (!StringUtils.isEmpty(discValue)) {
				discValue = discValue.contains("%") ? discValue.replace("%", "").trim() : discValue;
			}
			value = Double.parseDouble(discValue);
		} catch (Exception e) {
			if(dataType.equals("sales")){
				value = 0;
			}else{
				value = -1;
			}
		}
		return value;
	}

	public static long getSkuValue(String csvValue){
		long sku;
		try{
			sku = Long.valueOf(csvValue);
		}catch(Exception e){
			sku = -1;
		}
		return sku;
	}
	
	public static String getFormatter(Date date) {
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try{
			dateString = sdf.format(date);
		}catch(Exception e){
			LOG.error(DataToolConstants.CAUSE,e);
		}
		return dateString;
	}
	
	
	
	public static File convertMfileToFile(final MultipartFile mFile) {
	    File file = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + mFile.getOriginalFilename());
	    try {
	        if(file.exists()) {
	            file.delete();
	        }
	        mFile.transferTo(file);
	    } catch (IOException fnfE) {
	       LOG.error(DataToolConstants.CAUSE,fnfE);
	    }
	    return file;
	}
	
	
	
	
	public static CSVParser getParser(File file) {
		CSVParser parser = null;
		try {
			parser = new CSVParser( new FileReader(file), CSVFormat.RFC4180.withFirstRecordAsHeader().withDelimiter(',')
					.withAllowMissingColumnNames().withIgnoreHeaderCase().withTrim());
		} catch (IOException e) {
			LOG.error(DataToolConstants.CAUSE,e);
		}
		return parser;
	}
	
	
	public static double roundDecimalValue(String csvValue){
		
		String[] values = csvValue.split("\\.");
		double value = -1;
		String firstValue = values.length > 0 ? values[0] : "";
		if(firstValue.length()<11){
			try{
				value = Double.parseDouble(csvValue);
			}catch(Exception e){
				value = -1;
			}
			DecimalFormat df = new DecimalFormat("#.#####");
			df.setRoundingMode(RoundingMode.CEILING);
			value = Double.parseDouble(df.format(value));
		}
		return 	value;	
	}
}
