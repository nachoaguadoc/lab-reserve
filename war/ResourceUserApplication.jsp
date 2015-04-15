<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Recursos</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		
		
		<meta charset="utf-8">
	</head>
	<body class="main2">
	 <div class="container">
				<h1>Recursos</h1>
				<c:if test="${flashMessageSuccess != null }">
					<div class="alert alert-success alert-dismissible" role="alert">
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					  <span class="sr-only">¡Hecho!:</span>
					${flashMessageSuccess}
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
					    <li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/signup" />"> Perfil </a></li>						
						<li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a></li>
					
				    </ul>
				</div>
			</div>	
			
				<p>Hay un total de  <c:out value="${fn:length(groups)}" />
		Grupos de recursos.</p> 

		<div class="pre-container row groups">
	 
		<c:forEach items="${groups}" var="group">
		
			<c:set var="names" value="" />
			<c:forEach var="res" items="${group.resources}">
				<c:forEach var="res2" items="${resources}">				
				  <c:if test="${res eq res2.id}">

				    <c:set var="names" value="${names} ${res2.name} <br />" />
				  </c:if> 
				</c:forEach>
			</c:forEach>
						
			<div class="container col-md-3 height resource" data-toggle="popover" data-html="true" data-trigger="hover" title="Recursos" data-placement="right" data-content="${names}">
				<h4><c:out value="${group.name}" /></h4>
				<hr>
				<p><c:out value="${group.description}" /></p>
				
				<span>
					<a class="btn btn-default btn-round btn-border-w" href="<c:url value="/reserveGroup?id=${group.id}" />">Reservar</a>
				</span>
			</div>
		</c:forEach>
		<br>
		</div>
		<hr>
		<p>Hay un total de  <c:out value="${fn:length(resources)}" />
		recursos disponibles.</p> 
		<div class="container row">
			<c:forEach items="${resources}" var="resource">
				<div class="container col-md-3 height resource">
					<h4><c:out value="${resource.name}" /></h4>
					<hr>
					<p><c:out value="${resource.description}" /></p>
					<h5><c:out value="${resource.state}" /></h5>
					<span>
						<a class="btn btn-default btn-round btn-border-w" href="<c:url value="/resource?id=${resource.id}" />">Reservar</a>
					</span>
				</div>
			</c:forEach>

			</div>
		</div>
	</body>
</html>


<script>


jQuery(function () {
	  jQuery('[data-toggle="popover"]').popover()
	})

</script>
