<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
.header-logo{
	background-color:black;
}
.img-logo{
	margin-left: 46px;
}
.header-logo a{
	text-decoration:none;
}
.header-logo a:hover{
	color:#ff5d3b;
}
</style>

<body>

<div class="header-logo col-md-12">
		<div class="col-md-6">
			<a href="home"><img src="resources/images/logo.png" alt="shopperstopLogo" class="img-logo"></a>
		</div>
		<div class="col-md-6">
			<ul class="list-inline logout-div pull-right">
			  <li>
			  	<c:if test="${not empty sessionScope.user}">
               		<h4 style="color: white;"> ${sessionScope.user}</h4>
				</c:if>
			  </li>
			  <li>
			  	<a href="logout"  role="button" aria-expanded="false">
					<h4 style="color: white;">Logout</h4>
        		 </a>
			  </li>
			  
			</ul>
		</div>
		
</div>

    
</body>
</html>