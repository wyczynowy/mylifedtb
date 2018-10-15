<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="pl-PL">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/BATI_LOGO_KULA_RED_bez_tla.ico" />
	<!-- <title>additional</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
</head>
<body>
	<%@include file="includes/header_top.jsp" %>
		<div class="horizontal-center" style="width: 10%; min-width: 182px; top: 20px; margin: 0 auto;">
			<h1><span class="label label-default">Dodatkowe</span></h1>
		</div>
	<%@include file="includes/header_bottom.jsp" %>
	
	<security:authorize access="hasRole('ROLE_SUPERADMIN')">
		<div class="row">
			<div class="col-lg-2"></div>

			<div class="col-lg-8" style="margin-top: 50px;">
				<div class="row" style="margin-bottom: 20px;">
					<div style = "padding-left: 50px"><button class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/additional/adduser'">Dodaj użytkokwnika</button></div>
				</div>
				<div class="panel panel-default">
				  <div class="panel-heading" style="font-size: 18px">Użytkownicy</div>
				  <div class="panel-body">
				  	<c:forEach var="user" items="${userList}">
						<div class="panel panel-default">
							<!-- <div class="panel-heading"> -->
								<!-- <div class="panel-title"> -->
									<div style="display: inline;" class="row">
										<div style="display: inline" class="col-lg-3">
											<button class="btn btn-danger btn-xs" onclick="window.location.href='${pageContext.request.contextPath}/additional/deleteuser?userId=${user.userId}'">Usun</button>
											<button class="btn btn-warning btn-xs" onclick="window.location.href='${pageContext.request.contextPath}/additional/edituser?userId=${user.userId}'">Edytuj</button>
										</div>
										<div style="display: inline" class="col-lg-3">
											Login: <span style="font-size: 16px; font-weight: 600">${user.username}</span>
										</div>
										<div style="display: inline" class="col-lg-4">
											Rola: <c:forEach var="authority" items="${authorityList}">
											<c:if test="${authority.id == user.userId}">
												<span style="font-size: 16px; font-weight: 600">
														<c:if test="${authority.authority == 'ROLE_USER'}">Użytkownik</c:if>
														<c:if test="${authority.authority == 'ROLE_ADMIN'}">Administrator</c:if>
														<c:if test="${authority.authority == 'ROLE_SUPERADMIN'}">Super administrator</c:if>
												</span>
											</c:if>
										  </c:forEach>
										</div>
										<div style="display: inline" class="col-lg-2">
											Stan: <c:if test="${user.enabled == 0}"><span style="min-width: 10; min-height:10; background-color: red; color: red; border-radius: 4px">[o]</span></c:if>
										    <c:if test="${user.enabled == 1}"><span style="min-width: 10; min-height:10; background-color: green; color: green; border-radius: 4px">[o]</span></c:if>	
										</div>
									</div>
									<!-- Login: <span style="font-size: 16px; font-weight: 600">${user.username}</span> 
									Rola: <c:forEach var="authority" items="${authorityList}">
											<c:if test="${authority.id == user.userId}">
												<span style="font-size: 16px; font-weight: 600">
														<c:if test="${authority.authority == 'ROLE_USER'}">Użytkownik</c:if>
														<c:if test="${authority.authority == 'ROLE_ADMIN'}">Administrator</c:if>
														<c:if test="${authority.authority == 'ROLE_SUPERADMIN'}">Super administrator</c:if>
												</span>
											</c:if>
										  </c:forEach>
									Stan: <c:if test="${user.enabled == 0}"><span style="min-width: 10; min-height:10; background-color: red; color: red; border-radius: 4px">[o]</span></c:if>
										  <c:if test="${user.enabled == 1}"><span style="min-width: 10; min-height:10; background-color: green; color: green; border-radius: 4px">[o]</span></c:if> -->	  
								<!-- </div> -->
							<!-- </div> -->
						</div>
				  	</c:forEach>
				  </div>
				</div>
			</div>
			<div class="col-lg-2"></div>
		</div>
	</security:authorize>
</body>
</html>