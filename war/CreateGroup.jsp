<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>


<!DOCTYPE html>


<html>
	<head>
		<title>Groups - New group </title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>		
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main2">
	
		<div class="container">
				<h1>Nuevo Grupo</h1>				
			<div class="top-1">
				<div class="dropdown ">
			    <a class="dropdown-toggle" 
				data-toggle="dropdown">
				<strong><c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if></strong>
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
					<form action="/createGroup" method="post" accept-charset="utf-8" class="form">
						<div class="form-group">
							
								<label for="name">Nombre</label>
								<input type="text" class="form-control" name="name" id="name" placeholder="Nombre del grupo de recursos"/>
					
						
								<label for="description">Descripción</label>
								<textarea class="form-control" rows="3" name="description"
										id="description" placeholder="Breve descripción del grupo"></textarea>
										
							    <label for="resources">Recursos</label>
								<c:forEach items="${resources}" var="resource">
								   <div class="checkbox">
							        <label>
										<input type="checkbox" name="resources" value="${resource.id}"> ${resource.name}
							        </label>
							      </div>
								</c:forEach>

							    <br>
								<input type="submit" value="Crear" class="btn btn-rounded btn-border-w" />
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