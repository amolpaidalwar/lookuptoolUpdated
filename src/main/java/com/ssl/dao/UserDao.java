package com.ssl.dao;

import com.ssl.model.UserModel;

public interface UserDao {

	UserModel checkUserExist(String userName,String password);
	
}
