
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
<style>
.asian-container{background-color: #fff; border-radius: 4px;margin-top: 20px;}
.asian-container h1{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.asian-desc {padding: 20px;}
.uploadbtn{position: absolute;top: 65%;left:32%;width:10%;background-image: linear-gradient(to right, rgb(255, 93, 59) 0px, rgb(255, 44, 101) 100%);color: white;font-size: 18px;}
.asian-data-count{margin:0; padding: 20px; border-bottom: 1px solid #e2e2e2;}
.asin-result{background-color: #fff; border-radius: 4px;margin-top: 20px;}
</style>
</head>
<body>
<div class="asian-container">
	<h1>Upload ASIN Data</h1>
	<div class="asian-desc">
		<form method="POST" action="uploadAsin" enctype="multipart/form-data">
			<label>File to upload :</label> <input type="file" name="file"><br />
			 <input class="form-control uploadbtn" type="submit" value="Upload"> 
		</form>
		</div>
		<a style="    padding: 0 0 0 21px;text-decoration: none;" href="downloadHeaders?code=asin"><b>Download CSV Header Format</b></a>
	</div>
			
</body>
</html>