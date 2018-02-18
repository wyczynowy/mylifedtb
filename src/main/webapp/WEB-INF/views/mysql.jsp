<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>mySQL</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.png" />
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
</head>
<body style="min-width: 1024px;">

	<%@include file="includes/header.jsp" %>

	<div class="row">
		<div class="horizontal-center" style="width: 10%; margin: 0 auto;">
			<h1><span class="label label-info">mySQL</span></h1>
		</div>
	</div>
	<div class="row"><br/></div>

	<div class="row">
		<div class="col-sm-2">
			<div class="horizontal-center" style="width: 10%; min-width:120px; padding-top: 5%; margin: 0 auto">
				<img  style="width: 120px; height:120px; margin-top: 25px; margin-bottom: 25px" src="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.png" alt="LogoELblue" />
			</div>
		</div>
		<div class="col-sm-8">
			<div class="row">
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<div class="col-lg-3"><button class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/mysql/addnote'">Dodaj notatke +</button></div>
				</security:authorize>
			</div>
			<br/>
			<div class="row">				
				<div class="panel-group" id="accordion">		
					<c:forEach var="advice" items="${sqlAdvices}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="panel-title">
									<security:authorize access="hasRole('ROLE_ADMIN')">
										<button class="btn btn-danger btn-xs" onclick="window.location.href='${pageContext.request.contextPath}/mysql/deleteNote?adviceId=${advice.id}'">Usun</button>
										<button class="btn btn-warning btn-xs" onclick="window.location.href='${pageContext.request.contextPath}/mysql/editNote?adviceId=${advice.id}'">Edytuj</button>				
									</security:authorize>
									<a data-toggle="collapse" data-parent="#accordion" href="#${advice.id}">${advice.adviceName}</a>
								</div>
							</div>
							<div id="${advice.id}" class="panel-collapse collapse">
								<div class="panel-body">
									${advice.adviceDescription}
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-sm-2"></div>
	</div>
</body>
</html>