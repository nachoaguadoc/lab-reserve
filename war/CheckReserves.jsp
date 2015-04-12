<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Resources</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main2">
	 <div class="container">
			<div>
				<h1>Mis reservas</h1>
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
			<a style="float: right;" href="<c:url value="/main" />">Home</a>
			
	
		<p>You have a total number of <c:out value="${fn:length(reserves)}" />
		Reserves.</p> 
		<div class="container row">
		
			<c:forEach items="${reserves}" var="reserve">
				<a><div class="container col-md-2 reserve height">
					<p>Resource: <c:out value="${reserve.resourceName}" /></p>
					<p>Date: <c:out value="${reserve.date}" /></p>
					<p>Init time: <c:out value="${reserve.initHour}" /></p>
					<p>Final time: <c:out value="${reserve.finalHour}" /></p>
					<div class="buttons"></div>
					<span>
						<a class="btn btn-default" href="<c:url value="/update?id=${reserve.id}" />">Cambiar</a>
					</span>
					<span>
						<a class="btn btn-default" href="<c:url value="/cancel?id=${reserve.id}" />">Cancelar</a>
					</span>
					</div>
				</div></a>
			</c:forEach>
			</div>
		</div>
	</body>
</html>
