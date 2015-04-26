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
		<script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['bar']}]}"></script>
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
		  <a href="<c:url value="/main" />"><button class="btn btn-default btn-round btn-border-w pull-right" data-toggle="tooltip" data-placement="bottom" title="Home" >
		    <span class="glyphicon glyphicon-chevron-left">
		    </span>
		    </ul>
		</div>
	</div>
		
		
		<br>
		
			<!-- Consultar las reservas en un día concreto -->
			<form action="/stats" method="post" accept-charset="utf-8">
				     Quiero consultar:
				    <select class="selectpicker col-md-2" id="type" name="type" onchange="changeType()">
							<option value="day">Eventos por día</option>
							<option value="month">Eventos por Mes</option>
							<option value="year">Eventos por Año</option>
							<option value="resources">Eventos por recursos</option>
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
								<option value="2015">2015</option>
								<option value="2016">2016</option>
								<option value="2017">2017</option>
						</select>
					</div>
										
					<div id= "oresources" style="display:none">					
						<select id="recurso" name="recurso" class="form-control max-width col-md-4">
								<c:forEach items="${resources}" var="recurso" varStatus="status">
									<option value="${recurso.id}">${recurso.name}</option>
								</c:forEach>
							</select>
					</div>
					<div id= "oyear" style="display:none">					
						<select id="syear" name="syear" class="selectpicker col-md-2">
								<option value="2015">2015</option>
								<option value="2016">2016</option>
								<option value="2017">2017</option>
						</select>
					</div>							
					<br><br>
					<div class="wrapper col-md-4">
					<input class="btn btn-default btn-round btn-border-w" id="buttonConsult" type="submit"
							value="Consultar" />
					</div>
					
					
			</form>
			<br><br>
<!--
Zona donde se muestran las estadísticas del dia
-->
<c:if test="${listadia != null}">
<p>Numero de eventos en el dia ${dia} = ${fn:length(listadia)}</p>
<div class="container row col-md-7">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Tipo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listadia}" var="mov">
						<tr>
							<th>${mov.resourceid}</th>
							<th>${mov.resourcename}</th>
							<th>
							<c:if test="${mov.tipo == 1}">Reserva de recurso</c:if>
							<c:if test="${mov.tipo == 2}">Cancelación de reserva</c:if>
							<c:if test="${mov.tipo == 3}">Registro de usuario</c:if>
							</th>
						</tr>
					</c:forEach>
				</tbody>				
			</table>
		</div>
		<div class="col-md-5">
	<button class="btn btn-default btn-round btn-border-w" onclick="drawdia(${tipo1},${tipo2},${tipo3})">Mostrar gráfica</button>
		<div id="grafica"></div>
	</div>
</c:if>
<!--
Zona donde se muestran las estadísticas del mes
-->
<c:if test="${listames != null}">
<p>Numero de eventos en el mes ${fecha} = ${fn:length(listames)}</p>
<div class="container row col-md-7">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Fecha</th>
						<th>Tipo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listames}" var="mov">
						<tr>
							<th>${mov.resourceid}</th>
							<th>${mov.resourcename}</th>
							<th>${mov.date}</th>
							<th>
							<c:if test="${mov.tipo == 1}">Reserva de recurso</c:if>
							<c:if test="${mov.tipo == 2}">Cancelación de reserva</c:if>
							<c:if test="${mov.tipo == 3}">Registro de usuario</c:if>
							</th>
						</tr>
					</c:forEach>
				</tbody>				
			</table>
		</div>
		<div class="col-md-5">
	<button class="btn btn-default btn-round btn-border-w" onclick="drawdia(${tipo1},${tipo2},${tipo3})">Mostrar gráfica</button>
		<div id="grafica"></div>
	</div>
</c:if>
<!--
Zona donde se muestran las estadísticas del año
-->
<c:if test="${listayear != null}">
<p>Numero de eventos en el año ${year} = ${fn:length(listayear)}</p>
<div class="container row col-md-7">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Fecha</th>
						<th>Tipo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listayear}" var="mov">
						<tr>
							<th>${mov.resourceid}</th>
							<th>${mov.resourcename}</th>
							<th>${mov.date}</th>
							<th>
							<c:if test="${mov.tipo == 1}">Reserva de recurso</c:if>
							<c:if test="${mov.tipo == 2}">Cancelación de reserva</c:if>
							<c:if test="${mov.tipo == 3}">Registro de usuario</c:if>
							</th>
						</tr>
					</c:forEach>
				</tbody>				
			</table>
		</div>
		<div class="col-md-5">
	<button class="btn btn-default btn-round btn-border-w" onclick="drawdia(${tipo1},${tipo2},${tipo3})">Mostrar gráfica</button>
		<div id="grafica"></div>
	</div>
</c:if>
<!--
Zona donde se muestran las estadísticas por recurso
-->
<c:if test="${listarecurso != null}">
<p>Numero de eventos en el recurso ${nombrerecurso} = ${fn:length(listarecurso)}</p>

<div class="container row col-md-7">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Fecha</th>
						<th>Tipo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listarecurso}" var="mov">
						<tr>
							<th>${mov.date}</th>
							<th>
							<c:if test="${mov.tipo == 1}">Reserva de recurso</c:if>
							<c:if test="${mov.tipo == 2}">Cancelación de reserva</c:if>
							</th>
						</tr>
					</c:forEach>
				</tbody>				
			</table>
		</div>
<div class="col-md-5">
	<button class="btn btn-default btn-round btn-border-w" onclick="drawrecurso(${tipo1},${tipo2})">Mostrar gráfica</button>
		<div id="grafica2"></div>
	</div>

</c:if>

	
</div>
	
	
	</body>
</html>
<script>
function drawdia(a, b, c) {
    var data = new google.visualization.arrayToDataTable([
      ['Evento', 'nº eventos'],
      ["Reservas", a],
      ["Cancelaciones", b],
      ["Nuevos usuarios", c],
    ]);

    var options = {
      legend: { position: 'none' },
      axes: {
        x: {
          0: { side: 'top', label: 'Tipo de evento'} // Top x-axis.
        }
      },
      bar: { groupWidth: "80%" }
    };

    var chart = new google.charts.Bar(document.getElementById('grafica'));
    // Convert the Classic options to Material options.
    chart.draw(data, google.charts.Bar.convertOptions(options));
  };
  
  function drawrecurso(a, b) {
	    var data = new google.visualization.arrayToDataTable([
	      ['Evento', 'nº eventos'],
	      ["Reservas", a],
	      ["Cancelaciones", b],
	    ]);

	    var options = {
	      legend: { position: 'none' },
	      axes: {
	        x: {
	          0: { side: 'top', label: 'Tipo de evento'} // Top x-axis.
	        }
	      },
	      bar: { groupWidth: "80%" }
	    };

	    var chart = new google.charts.Bar(document.getElementById('grafica2'));
	    // Convert the Classic options to Material options.
	    chart.draw(data, google.charts.Bar.convertOptions(options));
	  };
	  
</script>
<script>
$(document).ready(function(){
	console.log($("#type").val());
	changeType();
})

function changeType(){
	if ($("#type").val() == "day"){
		$("#daily").show();
		$("#monthly").hide();
		$("#oyear").hide();
		$("#oresources").hide();
	} else if ($("#type").val() =="month"){
		$("#monthly").show();
		$("#daily").hide();
		$("#oyear").hide();
		$("#oresources").hide();
	}  else if ($("#type").val() =="year"){
		$("#monthly").hide();
		$("#daily").hide();
		$("#oresources").hide();
		$("#oyear").show();
	}else if ($("#type").val() =="resources"){
		$("#monthly").hide();
		$("#daily").hide();
		$("#oyear").hide();
		$("#oresources").show();
	}
}

function valida(){
	// dejar return para desactivar validación en el cliente
	return;
	
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