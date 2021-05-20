package com.ssl.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssl.dao.SearchDao;
import com.ssl.model.AsinModel;
import com.ssl.model.SchemePriceModel;
import com.ssl.utils.SslDateUtils;

@SuppressWarnings({"unchecked","unused","rawtypes"})
@Repository
public class SearchDaoImpl implements SearchDao {


	private static Logger LOG = Logger.getLogger(SearchDaoImpl.class);
	@Autowired
	@Qualifier("hibernateAnnotatedSessionFactory")
	private SessionFactory sessionFactory;

	
	public List<AsinModel> getAsinData(String sku, String ean, String asin, String dept, int pageNumber, int max) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(AsinModel.class);
		getQueryForParams( crit, sku,  ean,  asin,  dept);
		List<AsinModel> results = null;
			if (pageNumber > 0) {
				crit.setFirstResult((pageNumber - 1) * max);
			} else {
				crit.setFirstResult(0);
			}
			crit.setMaxResults(max);
			results = crit.list();
			session.getTransaction().commit();

			
		session.flush();
		session.close();
		return results;
	}
	
	
	public List<AsinModel> getAllSearchedAsinData(String sku, String ean, String asin, String dept) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(AsinModel.class);
		getQueryForParams( crit, sku,  ean,  asin,  dept);
		List<AsinModel> results = crit.list();
			session.getTransaction().commit();
		session.flush();
		session.close();
		return results;
	}
	

	public int getAsinDataCount(String sku, String ean, String asin, String dept) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(AsinModel.class);
		getQueryForParams( crit, sku,  ean,  asin,  dept);
		List results = crit.list();
		session.getTransaction().commit();
		session.flush();
		session.clear();
		return results.size();

	}

	public List<SchemePriceModel> getSchemePriceData(Date startDate, Date endDate) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria priceCrit = session.createCriteria(SchemePriceModel.class);
		
		priceCrit.add(Restrictions.between("effectiveDate",startDate, endDate));
		
		List<SchemePriceModel> priceResults = priceCrit.list();

		Criteria asinCriteria = session.createCriteria(AsinModel.class);
		/*asinCriteria.add(Restrictions.isNotNull("asin"));
		asinCriteria.add(Restrictions.eq("isActive", 1));*/
		LogicalExpression expression = Restrictions.and(Restrictions.isNotNull("asin"), Restrictions.eq("isActive", 1));
		asinCriteria.add(expression);
		
		List<AsinModel> asinList = asinCriteria.list();

		Map<String, AsinModel> asinMap = new HashMap<>();

		for (AsinModel asinModel : asinList) {
			asinMap.put(asinModel.getSku(), asinModel);
		}
		List<SchemePriceModel> finalList = new ArrayList<>();
		for (SchemePriceModel schemePriceModel : priceResults) {
			if (asinMap.containsKey(schemePriceModel.getSku())) {
				finalList.add(schemePriceModel);
			}
		}
		session.getTransaction().commit();
		session.flush();
		session.close();
		return finalList;
	}
	
	private void getQueryForParams(Criteria crit,String sku, String ean, String asin, String dept){
		
		if (!StringUtils.isEmpty(sku)) {
			try{	
				crit.add(Restrictions.eq("sku",sku));
			}catch(Exception e){
				crit.add(Restrictions.eq("sku",0));
			}
		}
		if (!StringUtils.isEmpty(ean)) {
			crit.add(Restrictions.eq("ean", ean));
		}
		if (!StringUtils.isEmpty(asin)) {
			crit.add(Restrictions.eq("asin", asin));
		}
		if (!StringUtils.isEmpty(dept)) {
			crit.add(Restrictions.eq("dept", dept));
		}
	}


	@Override
	public List<SchemePriceModel> getSchemePrice(String sku, String eventId, String sCode, String dateSubmitted,
			Integer pageNumber, Integer pageDisplayLength) {

		boolean status = false;
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(SchemePriceModel.class);
		createPriceCriteria(crit , sku,  eventId,  sCode,  dateSubmitted);
		List<SchemePriceModel> results = null;
		

		if(!StringUtils.isEmpty(sku) || !StringUtils.isEmpty(eventId) || !StringUtils.isEmpty(sCode) || !StringUtils.isEmpty(dateSubmitted)){
			if (pageNumber > 0) {
				crit.setFirstResult((pageNumber - 1) * pageDisplayLength);
			} else {
				crit.setFirstResult(0);
			}
			crit.setMaxResults(pageDisplayLength);
			results = crit.list();
			session.getTransaction().commit();
		}
	session.flush();
	session.close();
	return results;
	}
	
	
	@Override
	public int getPriceDataCount(String sku, String eventId, String sCode, String dateSubmitted) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SchemePriceModel.class);
		createPriceCriteria( crit, sku,  eventId,  sCode,  dateSubmitted);
		List<SchemePriceModel> results = null;
		
		if(!StringUtils.isEmpty(sku) || !StringUtils.isEmpty(eventId) || !StringUtils.isEmpty(sCode) || !StringUtils.isEmpty(dateSubmitted)){
			results = crit.list();
		}
		session.getTransaction().commit();
		session.flush();
		session.clear();
		return  !CollectionUtils.isEmpty(results) ? results.size() : 0;

	}
	
	private void createPriceCriteria(Criteria criteria,String sku, String eventId, String sCode, String dateSubmitted){
		
		
		if (!StringUtils.isEmpty(sku)) {
			try{	
				criteria.add(Restrictions.eq("sku",sku));
			}catch(Exception e){
				criteria.add(Restrictions.eq("sku",0));
			}
		}
		if (!StringUtils.isEmpty(eventId)) {
			criteria.add(Restrictions.eq("eventId", eventId));
		}
		if (!StringUtils.isEmpty(sCode)) {
			criteria.add(Restrictions.eq("schemeCode", sCode));
		}
		
		Date d  = SslDateUtils.getFormatter(dateSubmitted);
		if(d != null){
			criteria.add(Restrictions.eq("effectiveDate", d));
		}
	}



	@Override
	public List<SchemePriceModel> getAllSearchPriceData(String sku, String eventId, String sCode,
			String dateSubmitted) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SchemePriceModel.class);
		createPriceCriteria( crit, sku,  eventId,  sCode,  dateSubmitted);
		List<SchemePriceModel> results = null;
		
		if(!StringUtils.isEmpty(sku) || !StringUtils.isEmpty(eventId) || !StringUtils.isEmpty(sCode) || !StringUtils.isEmpty(dateSubmitted)){
			results = crit.list();
		}
		session.getTransaction().commit();
		session.flush();
		session.clear();
		return  !CollectionUtils.isEmpty(results) ? results : new ArrayList<>();
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	private void createDataCriteria(Criteria criteria, String sku, String dateSubmitted) {

		if (!StringUtils.isEmpty(sku)) {
			try {
				criteria.add(Restrictions.eq("sku",sku));
			} catch (Exception e) {
				criteria.add(Restrictions.eq("sku",0));
			}
		}
		Date d = SslDateUtils.getFormatter(dateSubmitted);
		if (d != null) {
			criteria.add(Restrictions.eq("effectiveDate", d));
		}
	}
	

	@Override
	public List<SchemePriceModel> getSearchPriceData(String sku, String dateSubmitted) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SchemePriceModel.class);
		
		createDataCriteria(crit, sku,dateSubmitted);
		List<SchemePriceModel> results = null;
		    
		if(!StringUtils.isEmpty(sku) || !StringUtils.isEmpty(dateSubmitted)){
			results = crit.list();
		}
		session.getTransaction().commit();
		session.flush();
		session.clear();
		return  !CollectionUtils.isEmpty(results) ? results : new ArrayList<>();
		 
	}
	
	@Override
	public List<SchemePriceModel> getPriceData(String sku, String dateSubmitted, Integer pageNumber,Integer pageDisplayLength) {
		boolean status = false;
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(SchemePriceModel.class);
		createDataCriteria(crit, sku, dateSubmitted);
		List<SchemePriceModel> results = null;

		if (!StringUtils.isEmpty(sku) || !StringUtils.isEmpty(dateSubmitted)) {
			if (pageNumber > 0) {
				crit.setFirstResult((pageNumber - 1) * pageDisplayLength);
			} else {
				crit.setFirstResult(0);
			}
			crit.setMaxResults(pageDisplayLength);
			results = crit.list();
			session.getTransaction().commit();
		}
		session.flush();
		session.close();
		return results;
	}

	@Override
	public int getDataCount(String sku, String dateSubmitted) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(SchemePriceModel.class);
		createDataCriteria(crit, sku, dateSubmitted);
		List<SchemePriceModel> results = null;

		if (!StringUtils.isEmpty(sku) || !StringUtils.isEmpty(dateSubmitted)) {
			results = crit.list();
		}
		session.getTransaction().commit();
		session.flush();
		session.clear();
		return !CollectionUtils.isEmpty(results) ? results.size() : 0;
	}
	

}
