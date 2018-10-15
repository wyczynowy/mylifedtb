<%@page import="java.util.*" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="false" %>
<html style="height: 100vh;">
<head>
	<title>Home</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.png" />
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
	<script src="resources/scripts/myScripts.js"></script>
</head>
<body style="background-color: #ffffff;">
<%@include file="includes/header_top.jsp" %>
	<div class="horizontal-center" style="width: 20%; top: 20; margin: 0 auto;">
	<h1><span class="label label-default">My Life database </span></h1>
	</div>
<%@include file="includes/header_bottom.jsp" %>

	<p id="demo">A Paragraph</p>
	
	<button class="btn btn-danger btn-xs" type="button" onclick="turn('demo', 'on')">ON</button>
	<button class="btn btn-success btn-xs" type="button" onclick="turn('demo', 'off')">OFF</button>
	
	<button class="btn btn-info btn-xs" type="button" onclick="sendToServer('message','demo')">Send to Server</button>
	<button id="mybutton" type="button" onclick="$('#demo').hide()">Ukryj</button>
	
	<div style=" width: 10%; height: 100%; min-width: 300px; margin: 0 auto;">
		<div style="height: 10%; min-height: 410px; padding-top: 10vh; margin: 0 auto;">
			<img  style="width: 300px; height: 300px;" src="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.png" alt="LogoEL" />
		</div>
	</div>

</body>
</html>
