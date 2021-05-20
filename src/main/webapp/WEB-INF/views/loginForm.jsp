<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>OrderBOT</title>
<spring:url value="${urls.getForLookupPath('/resources/app/css/style.css')}" var="styleCSS" />
<link href="${styleCSS}" rel="stylesheet" />
<link rel="icon" href="resources/app/images/hpFavicon.png" type="image/gif">
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script>
$(document).on('ready',function(){
	localStorage.removeItem('tab');
});
</script>
</head>
<body>
	<div class="loginTopHeader">
		<!-- <div class="headerLogin">
			<div class="logo">
				<img src="images/hp.png">
				<img src="resources/app/images/orderbot-logo.png">
			</div>
			<p class="text">OrderBOT</p>
			<div class="clear"></div>
		</div> -->
		<div class="clear"></div>
		
		 
		<div class="form">
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      			<font color="red">
        			<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
      			</font>
    		</c:if> 		
    		<c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
    		
			<form id="loginForm" action="<c:url value='j_spring_security_check'/>" method="post">
				<table style="width:100%;">
					<tr>
						<td colspan="2"><input type="text" placeholder="Enter Email" name='username' class="email loginText" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="password" placeholder="Enter Password" name='password' class="password loginText" /></td>
					</tr>
					<tr>
						<td><input type="submit" class="loginBtn" value="LOGIN" name="LOGIN" /></td>
						<td><div id="forgotPassword"><a href="forgotPassword" class="forgotPwd">Forgot Password?</a></div></td>
					</tr>
					<tr>
						<td><div id="signup" class="signUp"><span>New to hp OrderBOT? </span><a href="registration" class="">Signup</a></div></td>
						<td>&nbsp;</td>
					</tr>
				</table>					
			</form>
		</div>
		</div>
</body>
</html>
