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

	<div class="horizontal-center" style="width: 20%; top: 20; margin: 0 auto;">
	<h1><span class="label label-default">My Life database </span></h1>
	</div>

	<nav style="top: 20px; min-width: 1000px;" class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Electroline</a>
	    </div>
	    <ul class="nav navbar-nav">
	      <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
			<li class="dropdown">
			  <a class="dropdown-toggle" data-toggle="dropdown" href="#">Systemy operacyjne
			  <span class="caret"></span></a>
			  <ul class="dropdown-menu">
			    <li><a href="${pageContext.request.contextPath}/windows/">Windows</a></li>
			    <li><a href="${pageContext.request.contextPath}/linux/">Linux</a></li>
			    <li><a href="#">iOS</a></li>
			  </ul>
			</li>
			<li class="dropdown">
			  <a class="dropdown-toggle" data-toggle="dropdown" href="#">Jezyki programowania
			  <span class="caret"></span></a>
			  <ul class="dropdown-menu">
			    <li><a href="${pageContext.request.contextPath}/java/">Java</a></li>
			    <li><a href="${pageContext.request.contextPath}/c/">C</a></li>
			  </ul>
			</li>
			<li class="dropdown">
			  <a class="dropdown-toggle" data-toggle="dropdown" href="#">Srodowiska
			  <span class="caret"></span></a>
			  <ul class="dropdown-menu">
			    <li><a href="${pageContext.request.contextPath}/eclipse/">Eclipse</a></li>
			  </ul>
			</li>
			<li class="dropdown">
			  <a class="dropdown-toggle" data-toggle="dropdown" href="#">Elektronika
			  <span class="caret"></span></a>
			  <ul class="dropdown-menu">
			  <li><a href="${pageContext.request.contextPath}/electronicsgeneral/">Ogolne</a></li>
			    <li><a href="${pageContext.request.contextPath}/microprocessors/">Mikroprocesory</a></li>
			  </ul>
			</li>
	      <li><a href="${pageContext.request.contextPath}/mysql/">MySQL</a></li>
   			<security:authorize access="hasRole('ROLE_SUPERADMIN')">	
				<li><a href="${pageContext.request.contextPath}/additional/">Dodatkowe</a></li>	
			</security:authorize>
	    </ul>
	  </div>
	</nav>
</body>
</html>