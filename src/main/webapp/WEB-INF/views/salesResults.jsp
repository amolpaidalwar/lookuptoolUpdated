<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>
<!-- <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script> -->
<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script> 

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>
<script type="text/javascript">
$(document).ready(function() {
    $('#exampleSales').DataTable({
    	scrollY:        "350px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         true,
        columnDefs: [
            { width: '3%', targets: 0 }
        ],
        fixedColumns:  {
            heightMatch: 'none'
        }
    });
} );
</script>
<style>
.sales-result{background-color: #fff; border-radius: 4px;margin-top: 20px;}
.sales-data-count{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.result-header h3{display: inline-block;width: 100%;padding: 0 34px;}
#exampleSales_wrapper{padding-top: 17px;margin: 30px;}
#exampleSales_wrapper thead{background: #6d6e72;color: #ffffff;}
#exampleSales_wrapper tbody tr.odd{background-color: #f8f9ee;}
.sales-data-count a{text-decoration:none;color:rgb(51, 51, 51);}
.sales-data-count a:hover{color:#ff5d3b;}
</style>

</head>
<body>
<div class="sales-result">

	<div class="sales-data-count col-md-12">
		
			<div class="col-md-4">
				<h4>Total Records: <c:out value="${sessionScope.totalSalesCount}"/></h4>
			
			</div>
		<c:if test="${ sessionScope.successSalesCount gt 0}" >	
			<div class="col-md-4">
				<a href="downloadSalesData?code=success"><h4>Success Count : <c:out value="${sessionScope.successSalesCount}"/></h4></a>
			
			</div>
		</c:if>
		<c:if test="${ sessionScope.errorSalesCount gt 0}" >	
			<div class="col-md-4">
				<a href="downloadSalesData?code=error"><h4>Error Count : <c:out value="${sessionScope.errorSalesCount}"/></h4></a>
			
			</div>
		</c:if>	
	
	</div>
	
	<p style="color:white;">p</p>
	<div class="sales-table">
	
	
	
	
	<c:if test="${sessionScope.errorSalesCount ne 0}">
				<div class="result-header">
		<h3>Error Records</h3>
	</div>
			
			
			 <table id="exampleSales" class="display">
			 <thead>
				<tr>
					<th>SKU</th>
					<th>ASIN</th>
					<th>ORDER DATE</th>
					
					<th>ORDER ID</th>
					<th>INVOICE NUMBER</th>
					<th>TRANSACTION DATE</th>
				</tr>
				</thead>

<tbody>
				<c:forEach items="${errorSales}" var="sales">
					<tr>
						<td>${sales.getSku()}</td>
						<td>${sales.getAsin()}</td>
						<td>${sales.getOrderDate()}</td>
						
						<td>${sales.getOrderId()}</td>
						<td>${sales.getInvoiceNumber()}</td>
						<td>${sales.getTransactionDate()}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
	</c:if>		
		</div>
		
</div>
</body>
</html>