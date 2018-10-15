<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/BATI_LOGO_KULA_RED_bez_tla.ico" />
	<title>Editing user</title>
</head>
<body>

	<div class="horizontal-center" style="width: 10%; min-width:356px; top: 20; margin: 0 auto;">
		<h1><span class="label label-default">Edytowanie użytkownika</span></h1>
	</div>

	<div style="width: 50%; height: 100vh; margin: 0 auto;">
		<div style="height: 10%; min-height: 550px; margin-top: 10%;">
			<form:form action="${pageContext.request.contextPath}/additional/updatefilleduserform?userId=${user.userId}" modelAttribute="user" method="post">
			
			<c:set var = "presentPassword" scope = "request" value = "${user.password}"/>
			<input type="hidden" name="presentPassword" value="${presentPassword}"></input>
			
			<div class="form-group">
				 <label for="comment">Login:</label> 
				 <form:input path="username" id="username" class="form-control" rows="1" required="required"></form:input>
				 <!--<c:if test="${pageContext.request.method=='POST'}"><form:errors path="${newnote.adviceName}" /></c:if>-->
			 </div>
			
			 <div class="form-group">
				 <label for="comment">Hasło:</label> 
				 <form:password value="${user.password}" path="password" id="password" class="form-control" rows="1" required="required"></form:password>
				 <!--<c:if test="${pageContext.request.method=='POST'}"><form:errors path="${newnote.adviceDescription}" /></c:if>-->
			 </div>
			 
 			 <div class="form-group">
				 <label for="comment">Aktywny:</label>
	 			 <select class="form-control" name="enabled" id="sel1" size="2" required>
					  <option <c:if test = "${user.enabled == 0}">selected</c:if> value="0">Nieaktywny</option>
					  <option <c:if test = "${user.enabled == 1}">selected</c:if> value="1">Aktywny</option>
		  		 </select> 
				 <!--<form:input path="enabled" id="enabled" class="form-control" rows="1"></form:input>-->
				 <!--<c:if test="${pageContext.request.method=='POST'}"><form:errors path="${newnote.adviceDescription}" /></c:if>-->
			 </div>
		 
			<div class="form-group">
			  <label for="sel2">Rola:</label>
			  <select class="form-control" name="authority" id="sel2" size="3" required>
				  <option <c:if test = "${authority.authority == 'ROLE_USER'}">selected</c:if> value="ROLE_USER">Użytkownik</option>
				  <option <c:if test = "${authority.authority == 'ROLE_ADMIN'}">selected</c:if> value="ROLE_ADMIN">Administrator</option>
				  <option <c:if test = "${authority.authority == 'ROLE_SUPERADMIN'}">selected</c:if> value="ROLE_SUPERADMIN">Super administrator</option>
			  </select>
			</div>
			 			
			 <input class="btn btn-warning" type="submit" value = "Zapisz" />
			 <input class="btn btn-success" type="button" value = "Wróć" onclick="window.location.href='${pageContext.request.contextPath}/additional/'"/>
			</form:form>
		</div>
</div>
</body>
</html>