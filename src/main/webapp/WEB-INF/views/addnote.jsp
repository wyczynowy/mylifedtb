<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Adding new note</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.png" />
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/css/jasny-bootstrap.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min.js"></script>
</head>
<body>

<div style="width: 50%; height: 100%; margin: 0 auto;">
	<div style="width: 50%; height: 95%; position: absolute; top: 5%;">
		<form:form action="${pageContext.request.contextPath}/${noteType}/addfillednote" modelAttribute="newnote" method="post" enctype="multipart/form-data">
	
		<div class="form-group">
			 <label for="comment">Nazwa notatki:</label> 
			 <form:input path="adviceName" id="adviceName" class="form-control"></form:input>
			 <!--<c:if test="${pageContext.request.method=='POST'}"><form:errors path="${newnote.adviceName}" /></c:if>-->
		 </div>
		
		 <div class="form-group">
			 <label for="comment">Opis notatki:</label> 
			 <form:textarea path="adviceDescription" id="adviceDescription" class="form-control" rows="5"></form:textarea>
			 <!--<c:if test="${pageContext.request.method=='POST'}"><form:errors path="${newnote.adviceDescription}" /></c:if>-->
		 </div>
		 
		<!-- <label class="btn btn-primary" for="my-file-selector">
    		<input id="my-file-selector" type="file" name="file" style="display:none;">
    	Dodaj plik
    	</label> -->
    	
    	<!-- <div style="position:relative;">
        	<a class='btn btn-primary' href='javascript:;'>
            	Dodaj plik...
            	<input type="file" style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:transparent;color:transparent;' name="file" size="40"  onchange='$("#upload-file-info").html($(this).val());'>
        	</a>
       		 &nbsp;
        	<span class='label label-info' id="upload-file-info"></span>
		</div> -->
		<br>
		<label>Załączniki:</label> 
		<div class="fileinput fileinput-new input-group" data-provides="fileinput">
		  <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
		  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="file"></span>
		  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
		</div>
		
		<div class="fileinput fileinput-new input-group" data-provides="fileinput">
		  <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
		  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="file2"></span>
		  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
		</div>
		
		<div class="fileinput fileinput-new input-group" data-provides="fileinput">
		  <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
		  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="file3"></span>
		  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
		</div>
		
		<div class="fileinput fileinput-new input-group" data-provides="fileinput">
		  <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
		  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="file4"></span>
		  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
		</div>
		
		<div class="fileinput fileinput-new input-group" data-provides="fileinput">
		  <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
		  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="file5"></span>
		  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
		</div>
				
		<button type="button" class="btn btn-success" class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/${noteType}/'">Wroc</button>
		 <input class="btn btn-warning" type="submit" value="Zapisz"/>
		</form:form>
	</div>
</div>

</body>
</html>