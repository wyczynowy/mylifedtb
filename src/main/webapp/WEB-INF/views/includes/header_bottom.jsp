<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
	<title>Electroline</title>
</head>
<body>

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