<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en" >

<head>
  <meta charset="UTF-8">
  
  	 <link rel="stylesheet" href="resources/css/style.css">
      
     <link rel="stylesheet" href="resources/css/bootstrap.min.css">
     <script src="resources/js/bootstrap.min.js"></script>
     <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
     <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

<style>

body{background-color: #e2e2e2;}
.login-container {border-radius: 5px; background-color: #fff; margin: 5% 30%;}
.login-head {margin: 0; text-align: center; padding: 30px 0 0;}
.login-fields {padding: 0 30px;}
.login-button {padding: 0 30px 30px;;}
</style>
<link rel="icon" 
      type="image/png" 
      href="resources/images/favicon.png"/>
</head>
 
<body>

<div class="header-logo">
		<img src="resources/images/logo.png" alt="shopperstopLogo" class="img-logo">

</div>

<div class="login-container">
	<h3 class="login-head">Sign In</h3>
	<hr />
	 <form class="omb_loginForm"  id="loginForm" action="<c:url value='j_spring_security_check'/>" method="post">
		<div class="login-fields">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-user"></i></span>
				<input type="text" class="form-control" name="username" placeholder="Enter your username">
			</div>
			<span class="help-block"></span>
								
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-lock"></i></span>
				<input  type="password" class="form-control" name="password" placeholder="Enter your password">
			</div>
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
	
			 <span class="help-block"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></span>
		
	  </c:if>
		</div>
                <hr/>
		 
		<div class="login-button">
			<button class="btn btn-lg btn-primary btn-block sigin-btn" type="submit">Login</button>
		</div>
	</form>
</div>

  <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />

</body>

</html>
