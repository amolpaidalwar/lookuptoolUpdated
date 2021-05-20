<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    $('#example1').DataTable({
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
.asin-result{background-color: #fff; border-radius: 4px;margin-top: 20px;}
.asian-data-count{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.result-header h3{display: inline-block;width: 100%;padding: 0 34px;}
#example1_wrapper{margin: 30px;}
#example1_wrapper thead{background: #6d6e72;color: #ffffff;}
#example1_wrapper tbody tr.odd{background-color: #f8f9ee;}
.asin-result a{text-decoration:none;color:rgb(51, 51, 51);}
.asin-result a:hover{color:#ff5d3b;}
</style>

</head>
<body>
<div class="asin-result">
		<div class="asian-data-count col-md-12">
			<div class="col-md-4">
				<h4>Total Records :<c:out value="${sessionScope.totalAsinCount}" /></h4>
			
			</div>
			<div class="col-md-4">
				<c:if test="${sessionScope.asinSuccessCount gt 0}">
				<h4><a href="downLoadSuccessorErrorAsin?code=success">	Success Count :
						<c:out value="${sessionScope.asinSuccessCount}" />
					</a></h4> 
				</c:if>	
			
			</div>
			<div class="col-md-4">
				<c:if test="${sessionScope.asinErrorCount gt 0}">	
				<h4><a href="downLoadSuccessorErrorAsin?code=error">
						Error Count :
						<c:out value="${sessionScope.asinErrorCount}" />
					</a></h4>
			</c:if>	
			
			</div>

	</div>
		<p style="color:white;">p</p>
	<div class="asin-table">
		<c:if test="${sessionScope.asinErrorCount gt 0}">
				<div class="result-header">
		<h3>Error Records</h3>
	</div>
			<table id="example1" class="display">
				<thead>
					<tr>
						<th>SKU</th>
						<th>EAN</th>
						<th>PRICE</th>
						<th>ASIN</th>
						<th>HSN CODE</th>
						<th>DEPARTMENT</th>
						<th>IS ACTIVE</th>
					</tr>
				</thead>
		
				<tbody>
					<c:forEach items="${errorAsinData}" var="asinData">
						<tr>	
							<td width="">${asinData.getSku()}</td>
							<td width="">${asinData.getEan()}</td>
							<td width="">${asinData.getMrp()}</td>
							<td width="">${asinData.getAsin()}</td>
							<td width="">${asinData.getHsnCode()}</td>
							<td width="">${asinData.getDept()}</td>
							<td width="">${asinData.getIsActive()}</td>
						</tr>
					</c:forEach>
				
				</tbody>
			</table>
		</c:if>	
	</div>
</div>
</body>
</html>