<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>Adding new note</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="Shortcut icon" href="${pageContext.request.contextPath}/resources/pictures/LogoEL_blue.jpg" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/css/jasny-bootstrap.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min.js"></script>
</head>
<body>

<div class="horizontal-center" style="width: 50%; margin: 0 auto;">
	<div class="vertical-center" style="">
		<form:form action="${pageContext.request.contextPath}/${noteType}/updateFilledNote?id=${advice.id}" modelAttribute="advice" method="post" enctype="multipart/form-data">
		 
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
		 
		<label>Zaznacz załączniki do usunięcia:</label>
		<br>
			
		<c:forEach var="attachment" items="${adviceAttachmentsList}">
				<input type="checkbox" name="checkbox_${attachment.id}">  <a href="${pageContext.request.contextPath}${relativeUrlToFiles}${attachment.fileName}" target="_blank">${attachment.fileName}</a><br>
		</c:forEach>
		
		<c:if test="${adviceAttachmentsValue < 5}">
			 <br>
			 <label>Dodaj załączniki: (max: ${5 - adviceAttachmentsValue})</label>
			 <br> 
			<c:forEach begin="1" end="${5 - adviceAttachmentsValue}" varStatus="loop">
				<!-- Index: ${loop.index} -->
				<div class="fileinput fileinput-new input-group" data-provides="fileinput">
				  <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
				  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="file${loop.index}"></span>
				  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
				</div>
			</c:forEach>
		</c:if>
		<br>
		<c:if test="${adviceAttachmentsValue == 5}">
			<div class="alert alert-info alert-dismissable">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  <strong>Maksymalna ilość załączników osiągnięta !</strong> Aby dodać inny załącznik należy najpierw usunąć jeden z istniejących, a następnie podczas kolejnej edycji dodać nowy załącznik.
			</div>
		</c:if>
		 <button type="button" class="btn btn-success" class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/${noteType}/'">Wroc</button>
		 <input class="btn btn-warning" type="submit" value="Zapisz zmiany" />
		</form:form>
		<br/>
	</div>
</div>
>


</body>
</html>