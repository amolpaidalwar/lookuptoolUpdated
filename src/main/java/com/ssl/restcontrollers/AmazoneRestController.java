package com.ssl.restcontrollers;
/*
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssl.data.AsinData;
import com.ssl.data.SchemePriceData;
import com.ssl.data.transformation.DataTransformation;
import com.ssl.json.AsinJsonObject;
import com.ssl.json.SchemePriceJsonObject;
import com.ssl.model.AsinModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.service.impl.SearchServiceImpl;

 

@SuppressWarnings("unused")
@RestController
public class AmazoneRestController {
	
	@Autowired
	private SearchServiceImpl searchServiceImpl;
	
	private static Logger LOG=Logger.getLogger(AmazoneRestController.class);
	
	@GetMapping(value = "/getShemePriceData",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody String getSchemePriceData(HttpServletRequest  request,@RequestParam("sku") String sku,
			@RequestParam("eventId") String eventId, @RequestParam("sCode") String sCode, @RequestParam("dateSubmitted") String dateSubmitted)
	{

		HttpSession session = request.getSession();

		session.setAttribute("sku", sku);
		session.setAttribute("eventId", eventId);
		session.setAttribute("sCode", sCode);
		session.setAttribute("dateSubmitted", dateSubmitted);
		session.setAttribute("priceDownloadOption", "true");

		// Fetch the page number from client
		Integer pageNumber = 0;
		if (null != request.getParameter("iDisplayStart"))
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		List<SchemePriceModel> asinSList = searchServiceImpl.getSchemePriceData(sku, eventId, sCode, dateSubmitted, pageNumber, pageDisplayLength);
		int count = 0;
		if(!CollectionUtils.isEmpty(asinSList))
			count = searchServiceImpl.getPriceDatCount(sku, eventId, sCode, dateSubmitted);
		session.setAttribute("paginationSize", count);

		List<SchemePriceData> dataList = new ArrayList<>();
		
		if(!CollectionUtils.isEmpty(asinSList)){
			dataList = DataTransformation.populateSchemePriceData(asinSList);	
		}
		SchemePriceJsonObject jsonObject = new SchemePriceJsonObject();
		jsonObject.setiTotalDisplayRecords(count);
		jsonObject.setiTotalRecords(count);
		jsonObject.setAaData(dataList);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}
	
	//////////////////////////////////////////////////////////////////
	
	@GetMapping(value = "/getShemePriceData",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPriceData(HttpServletRequest  request,@RequestParam("sku") String sku,@RequestParam("dateSubmitted") String dateSubmitted)
	{
		HttpSession session = request.getSession();

		session.setAttribute("sku", sku);
		session.setAttribute("dateSubmitted", dateSubmitted);
		session.setAttribute("priceDownloadOption", "true");

		// Fetch the page number 
		Integer pageNumber = 0;
		if (null != request.getParameter("iDisplayStart"))
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		List<SchemePriceModel> SList = searchServiceImpl.getPriceData(sku,dateSubmitted, pageNumber, pageDisplayLength);
		int count = 0;
		if(!CollectionUtils.isEmpty(SList))
			count = searchServiceImpl.getDataCount(sku,dateSubmitted);
		session.setAttribute("paginationSize", count);

		List<SchemePriceData> dataList = new ArrayList<>();
		
		if(!CollectionUtils.isEmpty(SList)){
			dataList = DataTransformation.populateSchemePriceData(SList);	
		}
		SchemePriceJsonObject jsonObject = new SchemePriceJsonObject();
		jsonObject.setiTotalDisplayRecords(count);
		jsonObject.setiTotalRecords(count);
		jsonObject.setAaData(dataList);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}
	 
}
*/
