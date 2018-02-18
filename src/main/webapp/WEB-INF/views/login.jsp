<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.png" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<title>Login</title>
</head>
<body>
	<div style="width: 21%; height: 100%; min-width: 283px; margin: 0 auto;">
		<div style="height: 70%; position: absolute; top: 30%;">
			<div style="width: 34%; margin: 0 auto;"><h2><span class="label label-default">Login</span></h2></div>
			<div style="min-height: 10px;"></div>

		<c:url value="/login" var="loginUrl"/>
		<form action="${loginUrl}" method="post">
			<c:if test="${param.error != null}">
				<p>
					Invalid username and password.
				</p>
			</c:if>
			<c:if test="${param.logout != null}">
				<p>
					You have been logged out.
				</p>
			</c:if>
			<p>
				<label for="username">Username</label>
				<input type="text" id="username" name="username"/>
			</p>
			<p>
				<label for="password">Password</label>
				<input type="password" id="password" name="password"/>
			</p>
			<input type="hidden"
				name="${_csrf.parameterName}"
				value="${_csrf.token}"/>
			<button type="submit" class="btn">Log in</button>
		</form>
			
		</div>
	</div>

</body>
</html>