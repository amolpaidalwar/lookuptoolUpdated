<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
<style>
   body{background-color: #e2e2e2;
   font-family: 'Roboto', sans-serif;
   
   }
   
   .left-menu-container {background-color: #fff; margin: 20px; border-radius: 4px;}
   
   .left-menu-container ul {padding: 0;}
   
   .left-menu-container ul li {list-style: none;  border-bottom: 1px solid #e2e2e2;}
   
   .left-menu-container ul li a span {padding-right: 10px;}
   
   .left-menu-container ul li a {display: inline-block; width: 100%; line-height: 50px; text-decoration: none; color: rgb(51, 51, 51);
   	padding: 0 20px;}
   	
   .left-menu-container ul li a:hover {background-color: #f1f1f1;}
	
</style>

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
</head>
  

<body>

<div class="left-menu-container">
	<ul class="">
		<li><a href="uploadAsin"><span class="glyphicon glyphicon-upload"></span> Upload ASIN Data</a></li>
		<li ><a href="uploadPrice"><span class="glyphicon glyphicon-cloud-upload"></span> Upload Price Data</a></li>
		<li ><a href="uploadSalesReport"><span class="glyphicon glyphicon-open-file"></span> Upload Sales Data</a></li>
		<li ><a href="asinSearch"><span class="glyphicon glyphicon-search"></span> ASIN SEARCH</a></li>
		<li ><a href="schmePriceSearch"><span class="glyphicon glyphicon-search"></span> SCEHEME PRICE SEARCH</a></li>
		<li ><a href="getPriceFeed"><span class="glyphicon  glyphicon-download-alt"></span> Price Feed Download</a></li>
	</ul>
</div>

<!-- <nav class="navbar navbar-inverse sidebar" role="navigation">
    <div class="container-fluid">
    	
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="uploadAsin">Upload ASIN Data<span class="pull-right hidden-xs showopacity glyphicon glyphicon-upload"></span></a></li>
				<li ><a href="uploadPrice">Upload Price Data<span class="pull-right hidden-xs showopacity glyphicon glyphicon-cloud-upload"></span></a></li>
				<li ><a href="uploadSalesReport">Upload Sales Data<span class="pull-right hidden-xs showopacity glyphicon glyphicon-open-file"></span></a></li>
				
				
				<li ><a href="search">ASIN SEARCH<span class="pull-right hidden-xs showopacity glyphicon glyphicon-search"></span></a></li>
				<li ><a href="getPriceFeed">Price Feed Download<span class="pull-right hidden-xs showopacity glyphicon  glyphicon-download-alt"></span></a></li>
				
			</ul>
		</div>
	</div>
</nav> -->


</body>
     
</html>