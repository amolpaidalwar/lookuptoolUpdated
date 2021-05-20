<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<style>
.error-container{background-color: #fff; border-radius: 4px;margin-top: 20px;padding: 20px;color: red;}
</style>
<body>
<div class="error-container">
	<c:if test="${not empty errMsg}">
		<h2>${errMsg}</h2>
	</c:if>
</div>

</body>
</html>