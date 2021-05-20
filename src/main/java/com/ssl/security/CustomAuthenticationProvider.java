package com.ssl.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.ssl.dao.impl.UserDaoImpl;
import com.ssl.model.UserModel;

public class CustomAuthenticationProvider implements AuthenticationProvider{

	private UserDaoImpl userDaoImpl;
	
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		String username =  auth.getName();
		String password = (String) auth.getCredentials();
		UserModel user = userDaoImpl.checkUserExist(username, password);
		if(user !=null){
			List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
			grantedAuth.add(new GrantedAuthorityImpl(user.getRoles()));
			return new UsernamePasswordAuthenticationToken(username, password, grantedAuth);
		}
		
/*		if ((username.equals("vijay") || username.equals("pranjal")) && password.equals("12345")) {
			List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
			grantedAuth.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
			return new UsernamePasswordAuthenticationToken(username, password, grantedAuth);

		}*/ else {
			throw new BadCredentialsException("Invalid Email-Id / Password ");
		}
	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
}
