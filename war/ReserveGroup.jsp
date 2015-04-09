<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Grupo de recursos</title>
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
	<h1>Reserva de grupo de recursos</h1>
	
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
			<span id="efecha" class="errores">Error: Fecha de reserva en blanco</span>
			<span id="esesion" class="errores">Error: Horario de sesión incorrecto</span>
</div>
	
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
			
		
		
		<br><br><br>
			<c:choose>
				<c:when test="${user != null}">
					<form action="/reserveGroup?id=${group.id}" method="post" accept-charset="utf-8" onSubmit="return valida()">
							<input type="hidden" name="groupID" id="groupID" value="${group.id}" /></td>

							<span><input type="text" name="initDate" id="initDate" class="form-control max-width col-md-4" placeholder="Select Date"></span>
							<span><select class="selectpicker col-md-4" id="initTime" name="initTime"></span>
								<c:forEach items="${initTimes}" var="initHour" varStatus="status">
									<option value="${initHour}">${initHour}</option>
								</c:forEach>
							</select>                			
                			<!-- <input id="finalDate" type="text" name="finalDate"> -->
                			<select id="finalTime" name="finalTime" class="selectpicker col-md-4">
								<c:forEach items="${finalTimes}" var="finalHour" varStatus="status">
									<option value="${finalHour}">${finalHour}</option>
								</c:forEach>
							</select>
							<br><br>
							<div class="wrapper col-md-3">
							<input id="buttonReserve" class="btn btn-default btn-round btn-border-w" type="submit"
									value="Reservar" />
								</div>
							
		
					</form>
				</c:when>
				<c:otherwise>
	
	Please login with your Google account
				</c:otherwise>
			</c:choose>
			<c:if test="${error != null}">
			<div class="alert alert-danger" role="alert">
			  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			  <span class="sr-only">Error:</span>
			  ${error}
			</div></c:if>
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
	
	if($("#initDate").val()==''){
		$("#alerta").show();
		$("#efecha").show();	
		return false;
	};

    if($("#initTime").val()>=$("#finalTime").val())
    {
    	$("#alerta").show();
   		$("#esesion").show();
 	   return false;
    };
}
</script>

<script>
var date = new Date();
date.setDate(date.getDate());
jQuery('#initDate').datepicker({ 
			timepicker:false,
			autoclose: true,
			startDate: date,
			format: 'd/m/yy'
});
jQuery('#finalDate').datetimepicker({ 
	timepicker:false,
	format: 'd-m-y'			 
});

</script>
