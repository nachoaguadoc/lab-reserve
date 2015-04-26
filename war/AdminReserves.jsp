<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Reservas del recurso</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main2">
	 <div class="container">
			<div>
				<h1>Reservas totales - Nombre del recurso: ${resource.name }</h1>
				<c:if test="${flashMessageSuccess != null }">
				<div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>¡Hecho!</strong> ${flashMessageSuccess}
				</div>
				</c:if>				
			<div class="top-1">
				<div class="dropdown ">
			    <a class="dropdown-toggle" 
				data-toggle="dropdown">
				<strong><c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if></strong>
					<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
					    <li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/myreserves" />"> Mis reservas </a></li>
						<li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/config" />"> Configuración </a></li>
					    <li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/requests" />"> Peticiones </a></li>							    	
					    <li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/users" />"> Usuarios </a></li>
				    </ul>
				</div>
			</div>
		<a href="<c:url value="/main" />"><button class="btn btn-default btn-round btn-border-w pull-right" data-toggle="tooltip" data-placement="bottom" title="Home" >
		    <span class="glyphicon glyphicon-chevron-left">
		    </span>
		    	</button></a>
			
			
	
		<p>You have a total number of <c:out value="${fn:length(reserves)}" />
		Reserves.</p> 
		<div class="container row">
		
		
			<table class="table table-hover">
				<thead>
					<tr>
						<th> Author</th>
						<th> Fecha</th>
						<th> Horas</th>
						<th> Priodidad </th>
						<th> Cambios</th>
						<th> Cancelar </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${reserves}" var="reserve">
						<tr>
							<th>${reserve.author}</th>
							<th>${reserve.date}</th>
							<th>${reserve.initHour} - ${reserve.finalHour}</th>
							<th>${reserve.priority } </th>
							<th>
								<a class="btn btn-default btn-round btn-border-w" href="<c:url value="/update?id=${reserve.id}" />">Cambiar</a>
							</th>
							<th>		
								<form action="/cancel?id=${reserve.id}" method="post" accept-charset="utf-8">
									<input type="hidden" name="resourceID" id="resourceID" value="${reserve.resourceID}" /></td>	
                					<input type="hidden" name="resourceName" id="resourceName" value="${reserve.resourceName}" /></td>
									<input class="btn btn-default btn-round btn-border-w" type="submit" value="Cancelar" />
								</form>
							</th>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
		
			
			</div>
		</div>
	</body>
</html>
