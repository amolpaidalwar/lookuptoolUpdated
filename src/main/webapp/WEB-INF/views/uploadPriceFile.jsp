
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>

<style>
.price-container{background-color: #fff; border-radius: 4px;margin-top: 20px;}
.price-container h1{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.price-desc {padding: 20px;}
.uploadbtn{position: absolute;top: 65%;left:32%;width:10%;background-image: linear-gradient(to right, rgb(255, 93, 59) 0px, rgb(255, 44, 101) 100%);color: white;font-size: 18px;}

</style>
</head>
<body>

<div class="price-container">
	<h1>Upload Scheme Price Data</h1>
	<div class="price-desc">
		<c:if test="${UpLoadFile eq 'Please Insert File'}">
	
		</c:if>
		<form method="POST" action="uploadPrice" enctype="multipart/form-data">
			<label>File to upload :</label> <input type="file" name="file"><br /> 
			<input type="submit" value="Upload" class="form-control uploadbtn" />
		</form>
	
	</div>
	
	<a style="    padding: 0 0 0 21px;text-decoration: none;" href="downloadHeaders?code=BNMSCHEME"><b>Download CSV Header Format For BNMScheme</b></a>
	<a style="    padding: 0 0 0 21px;text-decoration: none;" href="downloadHeaders?code=AMZEXTDISCOUNT"> <b>Download CSV Header Format For AMZEXTDISCOUNT</b></a>
</div>	
</body>
</html>