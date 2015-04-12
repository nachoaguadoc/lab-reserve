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
				<h1> Modificar Grupo</h1>
						
				<c:if test="${flashMessageError != null }">
<div class="alert alert-danger alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <span class="sr-only">Error:</span>
${flashMessageError}
</div>
	</c:if>
	
		<div class="alert alert-danger alert-dismissible" role="alert" id="alerta" hidden>
 	 		<span class="glyphicon glyphicon-exclamation-sign"></span>
			<span id="ename" class="errores">Error: Nombre de grupo en blanco</span>
			<span id="edescription" class="errores">Error: Descripción en blanco</span>
			<span id="erecursos" class="errores">Error: Seleccione al menos un recurso</span>
		</div>
				
			<div class="top-1">
				<div class="dropdown ">
			    <a class="dropdown-toggle" 
				data-toggle="dropdown">
				<strong><c:if test="${user != null}"><c:out value="${user.nickname}"/> (admin)</c:if></strong>
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
			
				
		<div class="container row col-md-6 col-md-offset-3">
			<c:choose>
				<c:when test="${user != null}">
					<form action="/modifyGroup?id=${group.id}" method="post" accept-charset="utf-8" onSubmit="return valida()">
						<div class="form-group">
							
								<label for="name">Nombre</label>
								<input type="text" class="form-control" name="name" id="name" value="${group.name}" /></td>
							
							
								<label for="description">Descripción</label>
								<textarea rows="3" class="form-control" name="description" 
										id="description">${group.description}</textarea>
							
							
								<label for="resources">Recursos</label>
								<c:forEach items="${resources}" var="resource">

									<c:set var="contains" value="" />
									<c:forEach var="res" items="${group.resources}">
									  <c:if test="${res eq resource.id}">
									    <c:set var="contains" value="checked" />
									  </c:if>
									</c:forEach>
								   <div class="checkbox">
							        <label>
										<input type="checkbox" name="resources" value="${resource.id}" ${contains}> ${resource.name}
							        </label>
							      </div>
								</c:forEach>								


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
<script>
function valida(){
	// dejar return para desactivar validación en el cliente
	//return;
	
	$("#alerta").hide();
	$("span.errores").hide();	
	
	if($("#name").val()==''){
		$("#alerta").show();
		$("#ename").show();
		return false;
	}
	if($("#description").val()==''){
		$("#alerta").show();
		$("#edescription").show();
		return false;
	}
	
	if($("[name=resources]:checked").length <= 0){
		$("#alerta").show();
		$("#erecursos").show();
		return false;
	}
}
</script>