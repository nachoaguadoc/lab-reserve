<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>


<!DOCTYPE html>


<html>
	<head>
		<title>Usuario</title>
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
				<h1>Datos del usuario</h1>
				<div id="oldPriority" data-oldpriority="${oldPriority}" />
				<div id="oldName" data-oldname="${oldName}" />
				
				<c:if test="${flashMessageError != null }">
					<div class="alert alert-danger alert-dismissible" role="alert">
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
					  <span class="sr-only">Error:</span>
					${flashMessageError}
					</div>
				</c:if>
	
			<!-- <div class="alert alert-danger alert-dismissible" role="alert" id="alerta" hidden>
  				<span class="glyphicon glyphicon-exclamation-sign"></span>
				<span id="ewebmaster" class="errores">Error: Correo del administrador en blanco</span>
				<span id="esesion" class="errores">Error: Horario incorrecto</span>
			</div> -->
								
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
				    </ul>
				</div>
			</div>
				<a style="float: right;" href="<c:url value="/main" />">Home</a>
			
				
		<div class="container row col-md-6 col-md-offset-3">
		
			<c:choose>
				<c:when test="${user != null}">
					<form action="/signup" method="post" accept-charset="utf-8" class="form">
						<div class="form-group">
								<input type="hidden" name="userId" value="${user.userId}">
							
								<label for="id">ID del usuario:</label>
								<div class="form-control col-md-4" name="id" id="id">${user.userId}</div>
								<br><br><br><br>
							
								<label for="email">Correo del usuario:</label>
								<div class="form-control col-md-4" name="email" id="email">${user.email}</div>
								<br><br><br><br>
								
								<label for="name">Nombre y apellidos de usuario (opcional)</label>
								<input value="${appUser.name}" type="text" class="form-control col-md-4" name="name" id="name" placeholder="Nombre y apellidos"/>
								<br><br><br><br>
								
								<label for="priority">Rol del usuario</label>
									<br>
						
								<span><select class="selectpicker col-md-4" id="priority" name="priority" onchange="checkPriority()"></span>
							 	  <option value="1">1 - Alumno</option>
								  <option value="2">2 - Profesor</option>
								  <option value="3">3 - Coordinador</option>
								  <option value="4">4 - Director</option>
								</select>
								
								<div id="descriptionDiv" style="display: none;">	    	
							    	<br><br>
							    	
							    	<label  for="description">Descripción</label>
									<textarea class="form-control" rows="3" name="description"
											id="description" placeholder="Mensaje opcional al administrador de la instalación"></textarea>
									
									<br><br><br>
									          	
								</div>
								<br><br>

								<input type="submit" value="Fijar" class="btn btn-rounded btn-border-w" />

								<c:if test="${requestState != null}">
									<div class="alert alert-success alert" role="alert" id="alerta" hidden>
						  				<span class="glyphicon glyphicon-exclamation-sign"></span>
										<span>La petición ha sido enviada al administrador. Espere respuesta</span>
									</div>
								</c:if>
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

var nameDiv = document.getElementById("oldName"), oldName;
oldName = nameDiv.getAttribute("data-oldname");

if (oldName != null){
	$("#name").val(oldName);
}

var priorityDiv = document.getElementById("oldPriority"), oldPriority;
oldPriority = priorityDiv.getAttribute("data-oldpriority");


if (oldPriority != null){
	$("#priority").val(oldPriority);
}

function checkPriority() {
	
	if ($("#priority").val() != "1"){
		$("#descriptionDiv").show();
	} else {
		$("#descriptionDiv").hide();
	}
}
/*
function valida(){
	// dejar return para desactivar validación en el cliente
	//return;
	
	$("#alerta").hide();
	$("span.errores").hide();
		
	if($("#webmaster").val()==''){
		$("#alerta").show();
		$("#ewebmaster").show();
		return false;
	}

    if($("#opening").val()>=$("#closing").val())
    {
    	$("#alerta").show();
		$("#esesion").show();
 	   return false;
    }
}
*/
</script>
