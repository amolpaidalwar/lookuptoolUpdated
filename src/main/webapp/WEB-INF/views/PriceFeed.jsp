<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
  <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">  

      <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
      <script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script> 
 <script>  
         $(function() {  
        
        	 var dateSubmitted = $( "#datepicker-1" ).datepicker({ dateFormat: 'dd/mm/yy' }).val();  
         });  
      </script>  
      
       <script>  
         $(function() {  
        
        	 var dateSubmitted = $( "#datepicker-2" ).datepicker({ dateFormat: 'dd/mm/yy' }).val();  
         });  
      </script> 
      
      <style>
      .dwnload-container{background-color: #fff; border-radius: 4px;margin-top: 20px;}

		.pricefree{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
		
		.pricefeed h3{display: inline-block;width: 100%;padding: 0 20px;text-decoration:none;}

      </style>
       
</head>
<body>
<div class="dwnload-container">
	<div class="col-md-12 pricefree">
		<form action="submitDate">
			<div class="col-md-4">
				<label> Prices From Date  
				<span class="glyphicon glyphicon-calendar"></span></label><input class="form-control" type="text" id="datepicker-1"	name="startDate"/>
			</div>
			<div class="col-md-4">
				<label> Prices To Date  <span class="glyphicon glyphicon-calendar"></span></label>
				<input class="form-control" type="text" id="datepicker-2" name="endDate">
			</div>
			<div class="col-md-2">
				
				<input style="margin-top: 24px;background-image: linear-gradient(to right, rgb(255, 93, 59) 0px, rgb(255, 44, 101) 100%);color: white;font-size: 18px;" class="form-control" type="submit" name="Enter">
			</div>	
		
		</form>
	</div>

	<div class="pricefeed">
		<c:choose>
	       <c:when test="${ sessionScope.FeedPage eq 'page only' }">
			 <h3 style="color:green;">Select Start and End Dates </h3>
			</c:when>
	       
			<c:when test="${ sessionScope.differDays eq 'exeeded' }">
			
			 <h3 style="color:red;">Start Date and End date should not exeed 30 days </h3>
			</c:when>
			
			<c:when test="${ sessionScope.priceFeedSize eq 0 and sessionScope.differDays ne  'exeeded'}">
			
			 <h3 style="color:red;">Data Not found for the given Date</h3>
			</c:when>
			
			<c:when test="${ sessionScope.priceFeedSize ne 0}">
			
			 <a href="downloadPriceFeed"><h3 style="color:green;">Download Price Feed <span class="glyphicon glyphicon-download"></h3></abbr></a>
			</c:when>
			
		</c:choose>
	
	</div>
		
</div>

</body>
</html>