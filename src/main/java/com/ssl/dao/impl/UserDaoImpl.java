package com.ssl.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.ssl.model.UserModel;
@SuppressWarnings({"unused","rawtypes"})
public class UserDaoImpl{

	
	private static Logger LOG = Logger.getLogger(UserDaoImpl.class);
	/*@Autowired
	@Qualifier("hibernateAnnotatedSessionFactory")*/
	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public UserModel checkUserExist(String userName,String password) {
		
		UserModel user = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(UserModel.class);
		crit.add(Restrictions.eq("userId", userName));
		if(!password.equals("Get Details"))
			crit.add(Restrictions.eq("password", password));
		
		List list = crit.list();
		session.close();
		return  (!CollectionUtils.isEmpty(list) && list.size()>0 ) ? (UserModel)list.get(0) : null;
	}

}
