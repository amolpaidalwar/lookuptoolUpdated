
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
<style>
.sales-container{background-color: #fff; border-radius: 4px;margin-top: 20px;}
.sales-container h1{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.sales-desc {padding: 20px;}
.uploadbtn{position: absolute;top: 65%;left:32%;width:10%;background-image: linear-gradient(to right, rgb(255, 93, 59) 0px, rgb(255, 44, 101) 100%);color: white;font-size: 18px;}



</style>
</head>
<body>

<div class="sales-container">
	<h1>Upload Sales Report</h1>
	<div class="sales-desc">
		<form method="POST" action="uploadSalesReport" enctype="multipart/form-data">
			<label>File to upload :</label> <input type="file" name="file"><br /> 
			<input type="submit" value="Upload" class="form-control uploadbtn">
		</form>	
	</div>
	<a style="    padding: 0 0 0 21px;text-decoration: none;" href="downloadHeaders?code=sales"><b>Download CSV Header Format</b></a>
</div>

</body>
</html>