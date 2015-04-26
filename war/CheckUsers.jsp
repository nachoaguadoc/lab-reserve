<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Usuarios</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<meta charset="utf-8">
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<script src="js/jquery.datetimepicker.js"></script>
		<link rel="stylesheet" type="text/css" href="js/jquery.datetimepicker.css"/ >

      	<link href="css/bootstrap.min.css" rel="stylesheet">
      	<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap-select.min.css">
      	<script src="js/bootstrap-select.js"></script>
        <script src="js/bootstrap.min.js"></script>
      
		<link rel="stylesheet" type="text/css" media="screen" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.0/css/bootstrap-datepicker.css">
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.0/js/bootstrap-datepicker.js"></script>
		
		
	</head>
	<body class="main2">
	 <div class="container">
			<div>
				<h1>Usuarios</h1>
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
				    		    <a href="<c:url value="/main" />"><button class="btn btn-default btn-round btn-border-w pull-right" data-toggle="tooltip" data-placement="bottom" title="Home" >
		    <span class="glyphicon glyphicon-chevron-left">
		    </span>
		    </button></a>
				</div>
			</div>
			
	
		<p>Hay un total de <c:out value="${fn:length(users)}" />
		Usuarios usando la instalación.</p> 
		<div class="container row">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>ID</th>
						<th>Email</th>
						<th>Rol Actual</th>
						<th>Nuevo Rol</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="appUser">
						<tr>
							<th>${appUser.id}</th>
							<th>${appUser.email}</th>
							<th>${appUser.priority}</th>
							<th>
								<form action="/signup" method="post" accept-charset="utf-8">
									<input type="hidden" name="userId" value="${appUser.id}">
									<span><select class="selectpicker col-md-4" id="priority" name="priority"></span>
								 	  <option value="1">1 - Alumno</option>
									  <option value="2">2 - Profesor</option>
									  <option value="3">3 - Coordinador</option>
									  <option value="4">4 - Director</option>
									</select>
									<input class="btn btn-default btn-round btn-border-w" type="submit" value="Cambiar" />
								</form>
							</th>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
		</div>
	</body>
</html>
