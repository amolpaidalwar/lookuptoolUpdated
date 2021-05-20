package com.ssl.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
public class SearchController {

	private static Logger LOG = Logger.getLogger(SearchController.class);
	@Autowired
	private SearchServiceImpl searchServiceImpl;

	@GetMapping(value = "/asinSearch")
	public String getAsinSearchPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("paginationSize", 0);
		session.setAttribute("downloadOption", "false");
		return "pagination";
	}

	@GetMapping(value = "/getAsinData", produces = "application/json")
	public @ResponseBody String getAsinSearchData(HttpServletRequest request, @RequestParam("sku") String sku,
			@RequestParam("ean") String ean, @RequestParam("asin") String asin, @RequestParam("dept") String dept) {

		HttpSession session = request.getSession();

		session.setAttribute("sku", sku);
		session.setAttribute("asin", asin);
		session.setAttribute("dept", dept);
		session.setAttribute("ean", ean);
		session.setAttribute("downloadOption", "true");

		// Fetch the page number from client
		Integer pageNumber = 0;
		if (null != request.getParameter("iDisplayStart"))
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		List<AsinModel> asinSList = searchServiceImpl.getAsinData(sku, ean, asin, dept, pageNumber, pageDisplayLength);
		int count = 0;
		if (!CollectionUtils.isEmpty(asinSList))
			count = searchServiceImpl.getAsinDatCount(sku, ean, asin, dept);
		session.setAttribute("paginationSize", count);

		List<AsinData> dataList = DataTransformation.populateAsinData(asinSList);

		AsinJsonObject jsonObject = new AsinJsonObject();
		jsonObject.setiTotalDisplayRecords(count);
		jsonObject.setiTotalRecords(count);
		jsonObject.setAaData(dataList);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}

	@GetMapping(value = "/schmePriceSearch")
	public String getSchemePriceSearchPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("paginationSize", 0);
		session.setAttribute("priceDownloadOption", "false");
		return "priceSearchPage";
	}

	@GetMapping(value = "/getShemePriceData", produces = "application/json")
	public @ResponseBody String getSchemePriceData(HttpServletRequest request, @RequestParam("sku") String sku,
			@RequestParam("eventId") String eventId, @RequestParam("sCode") String sCode,
			@RequestParam("dateSubmitted") String dateSubmitted) {

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

		List<SchemePriceModel> asinSList = searchServiceImpl.getSchemePriceData(sku, eventId, sCode, dateSubmitted,
				pageNumber, pageDisplayLength);
		int count = 0;
		if (!CollectionUtils.isEmpty(asinSList))
			count = searchServiceImpl.getPriceDatCount(sku, eventId, sCode, dateSubmitted);
		session.setAttribute("paginationSize", count);

		List<SchemePriceData> dataList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(asinSList)) {
			dataList = DataTransformation.populateSchemePriceData(asinSList);
		}
		SchemePriceJsonObject jsonObject = new SchemePriceJsonObject();
		jsonObject.setiTotalDisplayRecords(count);
		jsonObject.setiTotalRecords(count);
		jsonObject.setAaData(dataList);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}

	@GetMapping(value = "/getPriceData", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPriceData(HttpServletRequest request, @RequestParam("sku") String sku,
			@RequestParam("dateSubmitted") String dateSubmitted) {
		HttpSession session = request.getSession();

		session.setAttribute("sku", sku);
		session.setAttribute("dateSubmitted", dateSubmitted);
		session.setAttribute("priceDownloadOption", "true");

		// Fetch the page number
		Integer pageNumber = 0;
		// Default page length is 10 records per page
		Integer pageDisplayLength = 10;
		if (null != request.getParameter("iDisplayStart"))
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;
		if (null != request.getParameter("iDisplayLength"))
			pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		List<SchemePriceModel> SList = searchServiceImpl.getPriceData(sku, dateSubmitted, pageNumber,
				pageDisplayLength);
		int count = 0;
		if (!CollectionUtils.isEmpty(SList))
			count = searchServiceImpl.getDataCount(sku, dateSubmitted);
		session.setAttribute("paginationSize", count);

		List<SchemePriceData> dataList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(SList)) {
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
