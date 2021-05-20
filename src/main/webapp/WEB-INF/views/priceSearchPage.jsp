<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring pagination using data tables</title>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>

<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script> 

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>	


<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script> 
    
     <!-- <script src="https://code.jquery.com/jquery-1.10.2.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
	
<script type="text/javascript">
	//Plug-in to fetch page data 
	jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
		return {
			"iStart" : oSettings._iDisplayStart,
			"iEnd" : oSettings.fnDisplayEnd(),
			"iLength" : oSettings._iDisplayLength,
			"iTotal" : oSettings.fnRecordsTotal(),
			"iFilteredTotal" : oSettings.fnRecordsDisplay(),
			"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
					.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
			"iTotalPages" : oSettings._iDisplayLength === -1 ? 0 : Math
					.ceil(oSettings.fnRecordsDisplay()
							/ oSettings._iDisplayLength)
		};
	};

	$(document).ready(function() {

		$("#submitPriceForm").click(function() {
			$('#priceTable thead').css('color','black');
			
			$("#priceTable").dataTable().fnDestroy();
	
			$("#priceTable").dataTable({

				"bSort" : false,
				"scrollY":        "300px",
		        "scrollCollapse": true,
				"searching" : false,
				"bProcessing" : true,
				"bServerSide" : true,
				"sort" : "position",
				//bStateSave variable you can use to save state on client cookies: set value "true" 
				"bStateSave" : false,
				//Default: Page display length
				"iDisplayLength" : 10,
				//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
				"iDisplayStart" : 0,
				"fnDrawCallback" : function() {
					//Get page numer on client. Please note: number start from 0 So
					//for the first page you will see 0 second page 1 third page 2...
					//Un-comment below alert to see page number
					//alert("Current page number: "+this.fnPagingInfo().iPage);    
				},
				"sAjaxSource" : "getShemePriceData",

				"fnServerParams" : function(aoData) {
					var sku = $("#sku").val();
					var eventId = $("#eventId").val();
					var sCode = $("#sCode").val();
					var dateSubmitted = $( "#datepicker-price" ).datepicker({ dateFormat: 'dd/mm/yy' }).val();
					aoData.push({
						name : "sku",
						value : sku
					});
					aoData.push({
						name : "eventId",
						value : eventId
					});
					aoData.push({
						name : "sCode",
						value : sCode
					});
					aoData.push({
						name : "dateSubmitted",
						value : dateSubmitted
					});
				},
				"aoColumns" : [ 
				{
					"mData" : "sku"
				}, 
				{
					"mData" : "effectiveDate"
				}, 
				{
					"mData" : "mrp"
				},
				{
					"mData" : "discType"
				}, 
				{
					"mData" : "discValue"
				}, 
				{
					"mData" : "sellPrice"
				},
				
				{
					"mData" : "extDiscType"
				}, 
				{
					"mData" : "extDiscValue"
				},
				{
					"mData" : "extSellPrice"
				},
				 {
					"mData" : "sslLiability"
				}, 
				{
					"mData" : "schemeCode"
				},
				{
					"mData" : "eventId"
				},
				{
					"mData" : "promoDesc"
				},
				]
			});

		});

	});
</script>

<script>  
         $(function() {  
        
        	 var dateSubmitted = $( "#datepicker-price" ).datepicker({ dateFormat: 'dd/mm/yy' }).val();  
         });  
      </script>  
<style>

.dwnld-assign{margin:10px;}

.dwnld-fields a{text-decoration:none;color:rgb(51, 51, 51);}

.dwnld-fields a:hover{color:#ff5d3b;}    
#priceTable_wrapper{margin: 30px;}
#priceTable_wrapper thead{background: #6d6e72;color: #ffffff;}

#priceTable_wrapper tbody tr.odd{background-color: #f8f9ee;}

.submitForm{background-image: linear-gradient(to right, rgb(255, 93, 59) 0px, rgb(255, 44, 101) 100%);text-align: center;color: white;font-size: 18px;}

.search-container{background-color: #fff; border-radius: 4px;margin-top: 20px;}

.search-fields{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}

.dwnld-fields{margin:0; padding: 20px;display: inline-block;}
.submit-form{    padding-top: 24px;}
.sales-desc {padding: 20px;}
.btn{border-radius: 0px;padding: 3px 12px; }
.btn-primary{background-image: linear-gradient(to right, rgb(255, 93, 59) 0px, rgb(255, 44, 101) 100%);border-color: transparent;}
.btn-primary:hover{background-color: transparent;border-color: transparent;}
.submit-form a:hover{color:white;}
</style>
</head>
<body>
<div class="search-container">
	<div class="search-fields col-md-12">
		<h1 style="margin-bottom: 21px;margin-top: 0px;">Search and Download</h1>
		<div class="row">
			<div class="col-md-2">
				<label>SKU :</label> <input type="text" name="sku" id="sku" class="form-control"/> 
			</div>
			<div class="col-md-2">
				<label>EVENT ID :</label><input type="text" name="eventId" id="eventId" class="form-control"/> 
			</div>
			<div class="col-md-2">
				<label>SCHEME CODE :</label><input type="text" name="sCode" id="sCode" class="form-control"/> 
			</div>
			<div class="col-md-2">
				<label>EFFECTIVE DATE :</label><input type="text" name="effDate" id="datepicker-price" class="form-control"/> 
			</div>
			<div class="col-md-2 submit-form">
				<input type="submit" name="Submit" id="submitPriceForm" class="form-control submitForm" 	/>
			</div>
			
			<div class="col-md-2 submit-form">
				<a href="schmePriceSearch" style="text-decoration:none;" class="form-control submitForm ">Reset	</a> 
			</div>
		</div>
	</div>
	
	<div class="dwnld-fields">
		
		<h5><a href="downloadSearchScheme">Download Scheme Price Sheet</a></h5>
	</div>
		
	<div class="asinsearch-table">
		<form:form action="" method="GET">
			<table id="priceTable">
					<thead style="color:white;">
						<tr>
							<th>SKU</th>	
							<th>Eff Date</th>
							<th>MRP</th>
							<th>DISC TYPE</th>
							<th>DISC VALUE</th>
							<th>SELLING PRICE</th>
							<th>EXT DISC Type</th>
							<th>EXT DISC Value</th>
							<th>EXT SELL Price</th>
							<th>LIABILITY</th>
							
							<th>SCHEME CODE</th>
							<th>EVENT ID</th>
							<th>DESCRIPTION</th>
						</tr>
					</thead>
				</table>
		</form:form>
	</div>
</div>
</body>
</html>