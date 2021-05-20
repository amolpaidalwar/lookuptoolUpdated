<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<!--  <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
      <script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script --> 
      
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>
<!-- <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script> -->
<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script> 

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>
<script type="text/javascript">
$(document).ready(function() {
    $('#example').DataTable();
} );
</script>


</head>
<body>

<form action="getAsinData" method="POST">
			<label>SKU :</label>	<input type="text" name="sku"> 
			<label style="margin: 28px 0 0 47px;">Department :</label>	<input type="text" name="dept" ><br> 
			<label>ASIN :</label>	<input type="text"name="asin"> 
			<label style="margin: 16px 0 0 84px;">EAN :</label>	<input type="text" name="ean"> <br>
				<input style="margin: 20px 0 2px 216px" type="submit" name="Submit">
</form>

<c:if test="${paginationSize gt 0}">
	<table id="example" class="display" style="width:100%">
	<thead>	<tr>
			<th>SKU</th>
			<th>EAN</th>
			<th>PRICE</th>
			<th>ASIN</th>
			<th>HSN CODE</th>
			<th>DEPARTMENT</th>
			<th>IS ACTIVE</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${paginationData}" var="pagination">
			<tr>
				<td width="">${pagination.getSku()}</td>
				<td width="">${pagination.getEan()}</td>
				<td width="">${pagination.getMrp()}</td>
				<td width="">${pagination.getAsin()}</td>
				<td width="">${pagination.getHsnCode()}</td>
				<td width="">${pagination.getDept()}</td>
				<td width="">${pagination.getIsActive()}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</c:if>
<c:if test="${paginationSize gt 0}">
 <a href="downloadAsinCsv">Download Asin</a>	
</c:if>
<c:if test="${paginationSize eq 0 and data eq 'No data'}">
   No data Found
</c:if>

</body>
</html>