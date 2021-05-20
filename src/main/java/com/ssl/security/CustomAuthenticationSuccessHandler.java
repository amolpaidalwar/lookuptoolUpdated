package com.ssl.security;

import com.ssl.dao.impl.UserDaoImpl;
import com.ssl.model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private UserDaoImpl userDaoImpl;
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(authentication);
        
		String username =  authentication.getName();
		String password = (String) authentication.getCredentials();
		UserModel user = userDaoImpl.checkUserExist(username,"Get Details");
        
        if(user!=null){
        	request.getSession().setAttribute("user", user.getFirstName());
        }
        
		if (!response.isCommitted()) {
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}
	}

	protected String determineTargetUrl(Authentication authentication) {
		return authentication.getAuthorities().stream().filter(Objects::nonNull).map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList()).contains("ROLE_ADMIN") ? "/home" : "/login";
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
}
