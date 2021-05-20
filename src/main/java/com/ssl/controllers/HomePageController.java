package com.ssl.controllers;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ssl.constants.DataToolConstants;
import com.ssl.data.SalesData;
import com.ssl.data.SchemePriceData;
import com.ssl.data.transformation.DataTransformation;
import com.ssl.exception.MultipartException;
import com.ssl.model.AsinModel;
import com.ssl.service.DataImportService;
import com.ssl.utils.SslDataUtil;

@Controller
public class HomePageController {
	
	private static final Logger LOG=Logger.getLogger(HomePageController.class);
	private static final String ERROR = "Please uploaded File";
	
	@Autowired
	private DataImportService dataImportServiceImpl;

	
	/**
	 * Login Form Url Method
	 * 
	 * @param model
	 * @return loginForm
	 */
	@RequestMapping({ "", "/", "/login" })
	public String login(ModelMap model, HttpSession session) {
		
		LOG.info("************Login Page*******************");
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(HttpSession session){
		return "homePage";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadAsin")
	public String getUploadProductsPage(){
		
		return "uploadAsinFile";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadPrice")
	public String getUploadPricePage(){
		
		return "uploadPriceFile";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadSalesReport")
	public String getUploadSalesReport(){
		
		return "uploadSalesReport";
	}
	
	
	/**
	 * 
	 * @param file
	 * @param request
	 * @param auth
	 * @return
	 * @throws MultipartException 
	 */
	@RequestMapping(value = "/uploadAsin", method = RequestMethod.POST)
	public String upLoadAsinData(@RequestParam("file") MultipartFile file,HttpServletRequest request,Authentication auth) throws MultipartException{
	

		File originalFile = null;
		if(!file.isEmpty()){
			originalFile = SslDataUtil.convertMfileToFile(file);
		}else{
			throw new MultipartException("404", ERROR);
		}
		 Map<String,List<AsinModel>> allAsinMap = dataImportServiceImpl.importAsinData(originalFile, auth.getName());
		 HttpSession session=request.getSession();
		 session.setAttribute(DataToolConstants.ASIN_SUCCESS_DATA, DataTransformation.populateAsinData(allAsinMap.get(DataToolConstants.ASIN_SUCCESS_DATA)));
		 session.setAttribute(DataToolConstants.ASIN_ERROR_DATA, DataTransformation.populateAsinData(allAsinMap.get(DataToolConstants.ASIN_ERROR_DATA)));
		 session.setAttribute("asinSuccessCount", allAsinMap.get("successAsinData").size());
		 session.setAttribute("asinErrorCount", allAsinMap.get("errorAsinData").size());
		 session.setAttribute("totalAsinCount", allAsinMap.get("errorAsinData").size()+ allAsinMap.get("successAsinData").size());
		return "asinResult";
	}
	
	/**
	 * 
	 * @param file
	 * @param request
	 * @param auth
	 * @return
	 * @throws MultipartException 
	 */
	@RequestMapping(value = "/uploadPrice", method = RequestMethod.POST)
	public String uploadPrices(
			@RequestParam("file") MultipartFile file,HttpServletRequest request,Authentication auth) throws MultipartException{
		
		File originalFile = null;
		
		if(!file.isEmpty()){
			originalFile = SslDataUtil.convertMfileToFile(file);
		}else{
			throw new MultipartException("404", ERROR);
		}
		 Map<String,List<SchemePriceData>> allPrices = dataImportServiceImpl.importPriceData(originalFile, auth.getName());
		 HttpSession session=request.getSession();
		 if(allPrices.size()!=0){
		 session.setAttribute(DataToolConstants.SUCCESS_PRICES,allPrices.get(DataToolConstants.SUCCESS_PRICES));
		 session.setAttribute(DataToolConstants.ERROR_PRICES,allPrices.get(DataToolConstants.ERROR_PRICES));
		 session.setAttribute("successPricesCount", allPrices.get(DataToolConstants.SUCCESS_PRICES).size());
		 session.setAttribute("errorPriceCount", allPrices.get(DataToolConstants.ERROR_PRICES).size());
		 session.setAttribute("totalPrieCount", allPrices.get(DataToolConstants.ERROR_PRICES).size()+allPrices.get(DataToolConstants.SUCCESS_PRICES).size());
		
		    return "priceResult";
		 }else{
			 session.setAttribute("UpLoadFile", "Please Check the File");
			 return "uploadPriceFile";
		 }
		
	}
	
	/**
	 * 
	 * @param file
	 * @param request
	 * @param auth
	 * @return
	 * @throws MultipartException 
	 */
	@RequestMapping(value = "/uploadSalesReport", method = RequestMethod.POST)
	public String upLoadSalesReport(@RequestParam("file") MultipartFile file,HttpServletRequest request,Authentication auth) throws MultipartException{
		
		File originalFile = null;
		if(!file.isEmpty()){
			originalFile = SslDataUtil.convertMfileToFile(file);
		}else{
			throw new MultipartException("404", ERROR);
		}
		HttpSession session=request.getSession();
		 Map<String,List<SalesData>> allSales = dataImportServiceImpl.importSalesData(originalFile, auth.getName());
		 session.setAttribute(DataToolConstants.SUCCESS_SALES,allSales.get(DataToolConstants.SUCCESS_SALES));
		 session.setAttribute(DataToolConstants.ERROR_SALES, allSales.get(DataToolConstants.ERROR_SALES));
		 session.setAttribute("successSalesCount", allSales.get(DataToolConstants.SUCCESS_SALES).size());
		 session.setAttribute("errorSalesCount", allSales.get(DataToolConstants.ERROR_SALES).size());
		 session.setAttribute("totalSalesCount", allSales.get(DataToolConstants.ERROR_SALES).size()+allSales.get(DataToolConstants.SUCCESS_SALES).size());
		 return "salesResults";
	}
	
	
	@ExceptionHandler(MultipartException.class)
	public ModelAndView handleCustomException(MultipartException ex) {
		ModelAndView model = new ModelAndView("errorpage");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
		return model;

	}
	
}


