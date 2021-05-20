package com.ssl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.ssl.constants.DataToolConstants;

public class SslDateUtils {

	private static final Logger LOG = Logger.getLogger(SslDateUtils.class);
	private SslDateUtils(){
		
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getEffectiveDates(String startDate, String endDate) {

		List<Date> dates = null;
		if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			dates = new ArrayList<>();
			Date sDate = null;
			Date eDate = null;
			Calendar c = Calendar.getInstance();
			sDate = SslDateUtils.getFormatter(startDate);
			eDate = SslDateUtils.getFormatter(endDate);

			if (sDate != null && eDate != null) {
				c.setTime(sDate);
				while (eDate.getTime() >= c.getTime().getTime()) {
					dates.add(c.getTime());
					c.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
			return dates;
		} else {
			return dates;
		}
	}
	
	
	
	public static boolean searchDatesCalculation(String startDate,String endDate){
		boolean status = true;
		Date sDate = null;
		Date eDate = null;
		sDate = SslDateUtils.getFormatter(startDate);
		eDate = SslDateUtils.getFormatter(endDate);
		
		if(sDate!=null && eDate != null){
		 /*float futureaPastDays =   (today.getTime() -  sDate.getTime())/(1000 * 60 * 60 * 24);
		 float exeedDays  =  (today.getTime() -  eDate.getTime())/(1000 * 60 * 60 * 24);
		 if(futureaPastDays<=30  && exeedDays <= 30){
			 status = true;
		 }else{
			 status = false;
		 }*/
		   float differ =   (eDate.getTime() -  sDate.getTime())/(1000 * 60 * 60 * 24);
		   if(differ <= 30 || differ >= -30){
			   status = true;
		   }else{
			   status = false;
		   }	 
		}
		 return status;
	}
	
	
	public static Date changeDateFormat(String csvValue) {
		String dateString = csvValue;
		if (csvValue.contains("-"))
			dateString = csvValue.replaceAll("-", "/");
		String[] monthdays = dateString.split("/");

		String day = monthdays[0];
		String month = monthdays.length > 1 ? monthdays[1] : "";
		int monthValue;
		int dayValue;

		try {
			monthValue = Integer.valueOf(month);
			dayValue = Integer.valueOf(day);
		} catch (Exception e) {
			monthValue = 13;
			dayValue = 32;
		}
		Date date = null;

		if (monthValue < 13 && dayValue < 32) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				LOG.error(DataToolConstants.CAUSE, e);
			}
		}
		return date;
	}
	
	
	public static Date databaseDateFormatter(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reqiuredFormat = sdf.format(date);
		Date requiredDate = null;
		
		try {
			requiredDate = sdf.parse(reqiuredFormat);
		} catch (ParseException e) {
			LOG.error(e);
		}
		return requiredDate;
	}
	
	public static Date getFormatter(String string){
		
		SimpleDateFormat sdf = null;
		Date givenDate = null;
		
		if(string.length()==8 && !string.contains("/") && !string.contains("-")){
			sdf = new SimpleDateFormat("ddMMyyyy");
			try {
				givenDate = sdf.parse(string);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if (string.length()==7) {
			sdf = new SimpleDateFormat("dMMyyyy");
			try {
				givenDate = sdf.parse(string);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if (string.length()==6) {
			sdf = new SimpleDateFormat("ddMMyy");
			try {
				givenDate = sdf.parse(string);
			} catch (ParseException e) {
				sdf = new SimpleDateFormat("dMMMyy");
				try {
					givenDate = sdf.parse(string);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		}else if (string.contains("/") ) {

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				givenDate = sdf.parse(string);
			} catch (ParseException e) {
				
			}
		
		}else if (string.contains("-") ) {

			sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				givenDate = sdf.parse(string);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if (string.length()==5) {
			string = "0"+string;
			sdf = new SimpleDateFormat("ddMMyy");
			try {
				givenDate = sdf.parse(string);
			} catch (ParseException e) {
					e.printStackTrace();
			}
		}
		Date rd  = null;
		SimpleDateFormat rdf = new SimpleDateFormat("ddMMyyyy");
		
	if(givenDate != null)	{
		try {
			String requiredDate = rdf.format(givenDate);
			rd = rdf.parse(requiredDate);
		} catch (ParseException e) {
			rd = null;
		}
	}
			return rd;
	}
}
