<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<title>Electroline</title>
</head>
<body>

<div style="height: 20px" class="row"></div>
<div class="row">
	<div class="col-lg-8"></div>
	<div style ="height: 5px;" class="col-lg-3">
		<!--<c:url var="logoutUrl" value="/logout"/>-->
		<form action="${pageContext.request.contextPath}/logout" method="post">
		<div  class="alert alert-info" style="width: 60%; float:left;"><strong>Zalogowany:</strong> <security:authentication property="principal.username" /></div>
		<div style="width: 35%; padding: 10px 0; float:right;" ><input type="submit" class="btn btn-md btn-danger" value="Log out" /></div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
	<div class="col-lg-1"></div>

</div>

</body>
</html>