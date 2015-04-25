<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Estadísticas</title>
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
	<h1>Estadísticas</h1>
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
				<span id="eDate" class="errores">Error: Día de consulta en blanco</span>
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
			    <li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/signup" />"> Perfil </a></li>						
		    </ul>
		</div>
	</div>
	<a style="float: right;" href="<c:url value="/main" />">Home</a>
			
		
		
		<br><br><br>
		
			<!-- Consultar las reservas en un día concreto -->
			<form action="/stats" method="post" accept-charset="utf-8">
				     Quiero consultar por 
				    <select class="selectpicker col-md-2" id="type" name="type" onchange="changeType()">
							<option value="day">Día</option>
							<option value="month">Mes</option>
					</select>
				    <br><br>
				    
				    <div id="daily">
						<input type="text" name="consultDate" id="consultDate" class="form-control max-width col-md-4" placeholder="Select Date" value="${dateSelected}">
					</div>
					
					<div id="monthly" style="display:none">
						<select class="selectpicker col-md-2" id="month" name="month">
								<option value="1">Enero</option>
								<option value="2">Febrero</option>
								<option value="3">Marzo</option>
								<option value="4">Abril</option>
								<option value="5">Mayo</option>
								<option value="6">Junio</option>
								<option value="7">Julio</option>
								<option value="8">Agosto</option>
								<option value="9">Septiembre</option>
								<option value="10">Octubre</option>
								<option value="11">Noviembre</option>
								<option value="12">Diciembre</option>
						</select>                			
		             	<select id="year" name="year" class="selectpicker col-md-2">
								<option value="15">2015</option>
								<option value="16">2016</option>
								<option value="17">2017</option>
						</select>
					</div>

					
					
					<br><br>
					<div class="wrapper">
					<input class="btn btn-default btn-round btn-border-w" id="buttonConsult" type="submit"
							value="Consultar" />
					</div>
					
					
			</form>
			<br><br>
<!--
Zona donde se muestran las estadísticas
-->
<c:if test="${lista != null}">
<p>Numero de movimientos = ${fn:length(string1)}</p>
</c:if>

	</div>
	</body>
</html>
<script>
$(document).ready(function(){
	console.log($("#type").val());
	changeType();
})

function changeType(){
	if ($("#type").val() == "day"){
		$("#daily").show();
		$("#monthly").hide();
	} else if ($("#type").val() =="month"){
		$("#monthly").show();
		$("#daily").hide();
	}
}

function valida(){
	// dejar return para desactivar validación en el cliente
	//return;
	
	$("#alerta").hide();
	$("span.errores").hide();
	
	if ($("#type").val() == "day"){
		if($("#consultDate").val()==''){
			$("#alerta").show();
			$("#eDate").show();
		}
		
	} else if ($("#type").val() =="month"){
	//por ahora nada
	};
}
</script>
<script>
var date = new Date();
date.setDate(date.getDate());
jQuery('#initDate').datepicker({ 
			timepicker:false,
			autoclose:true,
			startDate: date,
			format: 'd/m/yy'
});
jQuery('#consultDate').datepicker({ 
	timepicker:false,
	autoclose:true,
	startDate: '1/1/15',
	 format: 'd/m/yy'
});

jQuery('#finalDate').datetimepicker({ 
	timepicker:false,
	format: 'd-m-yy'			 
});


</script>