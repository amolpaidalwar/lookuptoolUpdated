package com.ssl.dao.impl;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssl.constants.DataToolConstants;
import com.ssl.dao.DataImportDao;
import com.ssl.model.AsinModel;
import com.ssl.model.SalesDataModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.utils.SslDataUtil;

@SuppressWarnings("unchecked")
@Repository
public class DataImportDaoImpl implements DataImportDao{

	private static final Logger LOG=Logger.getLogger(DataImportDaoImpl.class); 
	@Autowired
	@Qualifier("hibernateAnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	
	
	public boolean importAsinData(List<AsinModel> asinDataList){
		Set<String> skus = new HashSet<>();
		updateExistingAsinData(asinDataList);
		boolean status = false;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			@SuppressWarnings("unused")
			int count = 0;
				int i = 0;
				for (AsinModel asinData : asinDataList) {
				if(!skus.contains(asinData.getSku())){	
					i++;
					session.saveOrUpdate(asinData);
					if (i % 100 == 0) {
						session.flush();
						session.clear();
					}
					count++;
					skus.add(asinData.getSku());
				}else{
					List<AsinModel> duplicateRecords= new ArrayList<>();
					duplicateRecords.add(asinData);
					updateExistingAsinData(duplicateRecords);
					session.saveOrUpdate(duplicateRecords.get(0));
				}
				
				}
				transaction.commit();
				session.clear();
		status=true;
		session.close();
		} catch (HibernateException e) {
			LOG.error(DataToolConstants.CAUSE,e);
			if (transaction!=null)
			transaction.rollback();
		}
		return status;
	
	}
	
	private void updateExistingAsinData(List<AsinModel> asinDataList) {
		
		Session session = sessionFactory.openSession();
		for (AsinModel asinData : asinDataList) {
			Criteria crit = session.createCriteria(AsinModel.class);
			crit.add(Restrictions.eq("sku", asinData.getSku()));
			List<AsinModel> results = crit.list();
			if(!CollectionUtils.isEmpty(results)){
				for (AsinModel asinOldData : results) {
					asinData.setModifyTime(new Date());
					asinData.setCreatedTime(asinOldData.getCreatedTime());
					asinData.setPk(asinOldData.getPk());
				}
			}
		}
		session.close();
	}


	
	public List<AsinModel>  getAllAsinData() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria critera = session.createCriteria(AsinModel.class);
	    List<AsinModel> results = critera.list();
	    session.getTransaction().commit();
	    session.flush();
	    session.close();
	    return results;
	}
	
	public Map<String,SchemePriceModel>  getSchemePriceData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateString = sdf.format(date);
		Date today = null;
		try {
			today = sdf.parse(dateString);
		} catch (ParseException e) {
			LOG.error(DataToolConstants.CAUSE,e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date nextDay = cal.getTime();
		Session session = sessionFactory.openSession();  
		session.beginTransaction();
	    Criteria criteria = session.createCriteria(SchemePriceModel.class);
        Criterion createDate = Restrictions.between("createdTime",today,nextDay);
        Criterion modifyDate = Restrictions.between("modifiedTme",today,nextDay);
        LogicalExpression orExp = Restrictions.or(createDate,modifyDate);
        criteria.add(orExp);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<SchemePriceModel> results = criteria.list();
       
        Map<String,SchemePriceModel> priceMap = new HashMap<>();
        for (SchemePriceModel schemePriceModel : results) {
        
        	String key = String.valueOf(schemePriceModel.getSku())+"_"+SslDataUtil.getFormatter(schemePriceModel.getEffectiveDate());
        	
        	if(!priceMap.containsKey(schemePriceModel.getSku()) && (!StringUtils.isEmpty(schemePriceModel.getEventId()) || !StringUtils.isEmpty(schemePriceModel.getSchemeCode())))
        	{
        		priceMap.put(key, schemePriceModel);
        	}
		}
        session.getTransaction().commit();
        session.close();
        return priceMap;
	    
	}

	public boolean importPriceData(List<SchemePriceModel> successPrices,String fileName) {
		List<SchemePriceModel> updatedPrices = updateExistingPriceData(successPrices,fileName);
		boolean status = false;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int count = 0;
				int i = 0;
				for (SchemePriceModel schemePrice : updatedPrices) {
					i++;
					session.saveOrUpdate(schemePrice);
					if (i % 100 == 0) {
						session.flush();
						session.clear();
					}
					count++;
				}
				transaction.commit();
				session.clear();
		status=true;
		session.close();
		} catch (HibernateException e) {
			LOG.error(DataToolConstants.CAUSE,e);
			if (transaction!=null)
			transaction.rollback();
		}
		return status;
	}

	@SuppressWarnings("unused")
	private void updateExistingPriceData1(List<SchemePriceModel> successPrices) {

		Set<String> skus = new HashSet<>();
		Set<Date> dates  = new HashSet<>();
		Map<String,SchemePriceModel> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (SchemePriceModel schemePriceModel : successPrices) {
			skus.add(schemePriceModel.getSku());
			dates.add(schemePriceModel.getEffectiveDate());
			String key = String.valueOf(schemePriceModel.getSku())+"_"+sdf.format(schemePriceModel.getEffectiveDate());
			map.put(key, schemePriceModel);
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SchemePriceModel.class);
		List<SchemePriceModel> results = crit.list();
		
		for (SchemePriceModel schemePriceModel : results) {
			String key = String.valueOf(schemePriceModel.getSku())+"_"+sdf.format(schemePriceModel.getEffectiveDate());
			if(map.containsKey(key)){
				map.get(key).setCreatedTime(schemePriceModel.getCreatedTime());
			}
		}
		successPrices = new ArrayList<>(map.values());
		session.getTransaction().commit();
		session.flush();
		session.close();
	}
	
	
	private List<SchemePriceModel> updateExistingPriceData(List<SchemePriceModel> successPrices,String fileName) {

		Set<String> skus = new HashSet<>();
		Set<Date> dates  = new HashSet<>();
		Map<String,SchemePriceModel> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (SchemePriceModel schemePriceModel : successPrices) {
			if(!skus.contains(schemePriceModel.getSku())){
			 skus.add(schemePriceModel.getSku());
			}
			dates.add(schemePriceModel.getEffectiveDate());
			String key = schemePriceModel.getSku() + "_" + sdf.format(schemePriceModel.getEffectiveDate());
			map.put(key, schemePriceModel);
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SchemePriceModel.class);
	 	crit.add(Restrictions.in("sku", skus));
		List<SchemePriceModel> results = crit.list();
		
		for (SchemePriceModel schemePriceModel : results) {
			String key = schemePriceModel.getSku() + "_" + sdf.format(schemePriceModel.getEffectiveDate());
			if(map.containsKey(key)){
			 	SchemePriceModel newSchemePrice = map.get(key);
			 	newSchemePrice.setCreatedTime(schemePriceModel.getCreatedTime());
			if(fileName.contains("BNMSCHEME") || fileName.contains("bnmscheme")){	
				
				newSchemePrice.setExtDiscType(!StringUtils.isEmpty(schemePriceModel.getExtDiscType()) ? schemePriceModel.getExtDiscType() : null);
				newSchemePrice.setExtDiscValue(schemePriceModel.getExtDiscValue());
				newSchemePrice.setExtSellPrice(schemePriceModel.getExtSellPrice());
				newSchemePrice.setSslLiability(!StringUtils.isEmpty(schemePriceModel.getSslLiability()) ? schemePriceModel.getSslLiability() : null);
			}else if (fileName.contains("AMZEXTDISCOUNT") || fileName.contains("amzextdiscount")) {
				newSchemePrice.setSellPrice(schemePriceModel.getSellPrice());
				newSchemePrice.setDiscType(!StringUtils.isEmpty(schemePriceModel.getDiscType()) ? schemePriceModel.getDiscType() : null);
				newSchemePrice.setDiscValue(schemePriceModel.getDiscValue());
				newSchemePrice.setMrp(schemePriceModel.getMrp());
				newSchemePrice.setPromoDesc(!StringUtils.isEmpty(schemePriceModel.getPromoDesc()) ? schemePriceModel.getPromoDesc() : null);
				newSchemePrice.setEventId(!StringUtils.isEmpty(schemePriceModel.getEventId()) ? schemePriceModel.getEventId() : null);
				newSchemePrice.setSchemeCode(!StringUtils.isEmpty(schemePriceModel.getSchemeCode()) ? schemePriceModel.getSchemeCode() : null);
			}
			map.put(key, newSchemePrice);
			}
		}
		List<SchemePriceModel> updatedPrices = new ArrayList<>(map.values());
		session.getTransaction().commit();
		session.flush();
		session.close();
		return updatedPrices;
	}

	public boolean importSalesData(List<SalesDataModel> successSalesData) {
		
		List<SalesDataModel> updatedList = UpdateExistingSalesData(successSalesData);
		boolean status = false;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int count = 0;
				int i = 0;
				for (SalesDataModel salesDataModel : updatedList) {
					i++;
					session.saveOrUpdate(salesDataModel);
					if (i % 100 == 0) {
						session.flush();
						session.clear();
					}
					count++;
				}
				transaction.commit();
				session.clear();
		status=true;
		session.close();
		} catch (HibernateException e) {
			LOG.error(DataToolConstants.CAUSE,e);
			if (transaction!=null)
			transaction.rollback();
		}
		return status;
	}

	private List<SalesDataModel>  UpdateExistingSalesData(List<SalesDataModel> successSalesData) {
		
		Set<String> skus = new HashSet<>();
		Map<String,SalesDataModel> uniqueSales = new HashMap<>();
		for (SalesDataModel salesDataModel : successSalesData) {
			skus.add(salesDataModel.getSku());
			uniqueSales.put(salesDataModel.getSku(), salesDataModel);
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SalesDataModel.class);
		crit.add(Restrictions.in("sku", skus));
		List<SalesDataModel> results = crit.list();
		
		for (SalesDataModel salesDataModel : results) {
			
			if(uniqueSales.containsKey(salesDataModel.getSku())){
				uniqueSales.get(salesDataModel.getSku()).setPk(salesDataModel.getPk());
			}
		}
		
		return new ArrayList<>(uniqueSales.values());
	}

	@Override
	public Map<String,SchemePriceModel>  getSchemePriceForSkuandDates(List<String> skus, List<Date> dates,Map<String,String> checkskuandDate) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(SchemePriceModel.class);
       // Criterion skuCriteria = Restrictions.in("sku",skus);
        Criterion dateCriteria = Restrictions.in("effectiveDate",dates);
     //   LogicalExpression orExp = Restrictions.and(skuCriteria,dateCriteria);
     //   criteria.add(orExp);
      //  criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(dateCriteria);
        List<SchemePriceModel> results = criteria.list();
        Map<String,SchemePriceModel> priceMap = new HashMap<>();
        for (SchemePriceModel schemePriceModel : results) {
        	String key = schemePriceModel.getSku()+"_"+SslDataUtil.getFormatter(schemePriceModel.getEffectiveDate());
        	if(checkskuandDate.containsKey(key)){
        		priceMap.put(key, schemePriceModel);
        	}
		}
        session.getTransaction().commit();
        session.close();
        return priceMap;
        
	}
}
