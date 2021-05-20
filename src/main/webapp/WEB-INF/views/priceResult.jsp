<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>

<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script> 

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>	
<script type="text/javascript">
$(document).ready(function() {
    $('#examplePrices').DataTable({
    	scrollY:        "300px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         true,
        columnDefs: [
            { width: '10%', targets: 0 }
        ],
        fixedColumns: true
    });
} );
</script>
<style>
.price-result{background-color: #fff; border-radius: 4px;margin-top: 20px;}
.price-data-count{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.result-header h3{display: inline-block;width: 100%;padding: 0 34px;}
#examplePrices_wrapper{margin: 30px;}
#examplePrices_wrapper thead{background: #6d6e72;color: #ffffff;}
#examplePrices_wrapper tbody tr.odd{background-color: #f8f9ee;}
.price-data-count a{text-decoration:none;color:rgb(51, 51, 51);}
.price-data-count a:hover{color:#ff5d3b;}
</style>
</head>
<body>
<div class="price-result">

<div class="price-data-count col-md-12">
			<div class="col-md-4">
				<h4>
					Total Records :
					<c:out value="${sessionScope.totalPrieCount}" />
				</h4>
			
			</div>
			<div class="col-md-4">
				<c:if test="${sessionScope.successPricesCount gt 0 }">
				<a href="downLoadSuccessorErrorPrice?code=success"><h4>
						Success Count :
						<c:out value="${sessionScope.successPricesCount}" />
					</h4></a>
			</c:if>	
			
			</div>
			<div class="col-md-4">
					<c:if test="${ sessionScope.errorPriceCount gt 0}" >	
				<a href="downLoadSuccessorErrorPrice?code=error"><h4>
						Error Count :
						<c:out value="${sessionScope.errorPriceCount}" />
					</h4></a>				</c:if>	
			
			</div>
	</div>
	<p style="color:white;">p</p>
	
	<div class="price-table">
	
	

	<c:if test="${ sessionScope.errorPriceCount gt 0}" >
	   <div class="result-header">
		<h3>Error Records</h3>
	</div>
	  
	
			<table id="examplePrices" class="display" style="width:70%">
			<thead>
				<tr>
					<th>SKU</th>
					<th>PRICE</th>
					<th>EFFECTIVE DATE</th>
					
					<th>SELLING PRICE</th>
					<th>DISCOUNT TYPE</th>
					<th>DISCOUNT VALUE</th>
					
					<th>EXT. SELLING PRICE</th>
					<th>EXT. DISCOUNT TYPE</th>
					<th>EXT. DISCOUNT  VALUE</th>
					
					
					<th>SSL LIABILITY</th>
					<th>SCHEME CODE</th>
					<th>EVENT ID</th>
					<th>PROMOION DESCRIPTION</th>
					
				</tr>
</thead>
<tbody>
				<c:forEach items="${errorPrices}" var="price">
					<tr style="">
						<td>${price.getSku()}</td>
						<td>${price.getMrp()}</td>
						<td>${price.getEffectiveDate()}</td>
						
						<td>${price.getSellPrice()}</td>
						<td>${price.getDiscType()}</td>
						<td>${price.getDiscValue()}</td>
						
						<td>${price.getExtSellPrice()}</td>
						<td>${price.getExtDiscType()}</td>
						<td>${price.getExtDiscValue()}</td>
						
						<td>${price.getSslLiability()}</td>
						
						<td>${price.getSchemeCode()}</td>
						<td>${price.getEventId()}</td>
						<td>${price.getPromoDesc()}</td>
						
						
						
					</tr>
				</c:forEach>
				</tbody>
			</table>
</c:if>


	</div>
</div>
</body>
</html>