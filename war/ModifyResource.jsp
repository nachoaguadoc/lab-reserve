<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>

<html>
	<head>
		<title>Resources</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main2">
	
		 <div class="container">
				<h1> Modificar Recurso</h1>
				
			<div class="top-1">
				<div class="dropdown ">
			    <a class="dropdown-toggle" 
				data-toggle="dropdown">
				<strong><c:if test="${user != null}"><c:out value="${user.nickname}"/> (admin)</c:if></strong>
					<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
			   			<li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/myreserves" />"> Mis reservas </a></li>			
				    </ul>
				</div>
			</div>	
			<a style="float: right;" href="<c:url value="/main" />">Home</a>
			
				
		<div class="container row col-md-6 col-md-offset-3">
			<c:choose>
				<c:when test="${user != null}">
					<form action="/modify?id=${resource.id}" method="post" accept-charset="utf-8">
						<div class="form-group">
							
								<label for="name">Nombre</label>
								<input type="text" class="form-control" name="name" id="name" value="${resource.name}" /></td>
							
							
								<label for="description">Descripción</label>
								<textarea rows="3" class="form-control" name="description" 
										id="description">${resource.description}</textarea>
							
							
								<label for="state">Estado</label></td>
								<textarea rows="1" class="form-control" name="state"
										id="state">${resource.state}</textarea>
								<br>
								<input type="submit"
								 value="Modificar" class="btn btn-rounded btn-border-w"/>
							
						</div>
					</form>
				</c:when>
				<c:otherwise>
	
	Please login with your Google account
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	</body>
</html>
