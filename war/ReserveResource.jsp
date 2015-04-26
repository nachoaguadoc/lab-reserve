<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Recurso</title>
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
	<h1>Reserva de recursos - Recurso ${resource.name}</h1>
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
			    <li role="presentation"><a role="menuitem" tabindex="-1" href="<c:url value="/signup" />"> Perfil </a></li>						
		    </ul>
		</div>
	</div>
	<a style="float: right;" href="<c:url value="/main" />">Home</a>
			
		
		
		<br><br><br>
		
			<!-- Consultar las reservas en un día concreto -->
			<form action="/resource?id=${resource.id}" method="post" accept-charset="utf-8">
					<input type="hidden" name="resourceID" id="resourceID" value="${resource.id}" />
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
						<select class="selectpicker col-md-2" id="initTimeMonth" name="initTimeMonth">
							<c:forEach items="${initTimes}" var="initHour" varStatus="status">
								<option value="${initHour}">${initHour}</option>
							</c:forEach>
						</select>                			
		             			<!-- <input id="finalDate" type="text" name="finalDate"> -->
		             			<select id="finalTimeMonth" name="finalTimeMonth" class="selectpicker col-md-2">
							<c:forEach items="${finalTimes}" var="finalHour" varStatus="status">
								<option value="${finalHour}">${finalHour}</option>
							</c:forEach>
						</select>
					</div>

					
					
					<br><br>
					<div class="wrapper">
					<input class="btn btn-default btn-round btn-border-w" id="buttonConsult" type="submit"
							value="Consultar" />
					</div>
					
					
			</form>
			<br><br>
			<div id="oldType" data-oldtype="${type}" />
			<c:if test="${type == 'day'}">
							
				<h2>Horas libres el ${dateSelected}</h2>
				<table class="table table-bordered">
				<tbody>
				<form action="/reserve?id=${resource.id}" method="POST">
					<input type="hidden" name="resourceID" id="resourceID" value="${resourceID}" />
						<input type="hidden" name="resType" id="resType" value="${type}" />
						<input type="hidden" name="resDate" id="resDate" value="${dateSelected}" />

					<c:set var="counter" value="1"/>
					<c:forEach items="${list}" var="hour" varStatus="i">

							<c:if test="${!consult[hour]}">
								<c:if test="${fn:length(list) > i.count}" >
									<td class="free">${hour} - ${list[i.count]} <input type="checkbox" name="resHours" value="${hour }">
									</td>
								</c:if>								
							</c:if>
							<c:if test="${consult[hour]}">
								<c:if test="${fn:length(list) > i.count}" >
									<td class="busy">${hour} - ${list[i.count]}</td>
								</c:if>
							</c:if>
							<c:if test="${counter % 4 == 0 }">
								</tr>
								<tr>
							</c:if>
							<c:set var="counter" value="${counter + 1}"/>
					</c:forEach>
				
				  </tbody>
			  </table>
			  		<div class="wrapper">				
					<input type="submit" class="btn btn-default btn-round btn-border-w" value="Reservar">
					</div>
			  </form>
			</c:if>
			
			<c:if test="${type == 'month'}">
				<div id="oldMonth" data-oldmonth="${oldMonth}" />
				<div id="oldYear" data-oldyear="${oldYear}" />
				<div id="oldInit" data-oldinit="${oldInit}" />
				<div id="oldFinal" data-oldfinal="${oldFinal}" />
				
				<h2>Horas libres</h2>
				<table class="table table-bordered">
				<thead>
					<tr>
						<td>L</td>
						<td>M</td>
						<td>X</td>
						<td>J</td>
						<td>V</td>
						<td>S</td>
						<td>D</td>
						
					</tr>
				</thead>
				<tbody>
					<form action="/reserve?id=${resource.id}" method="POST">
						<input type="hidden" name="startingHour" id="startingHour" value="${oldInit}" />
						<input type="hidden" name="finishingHour" id="finishingHour" value="${oldFinal}" />
						<input type="hidden" name="resType" id="resType" value="${type}" />
						<input type="hidden" name="resMonth" id="resMonth" value="${oldMonth}" />
						<input type="hidden" name="resYear" id="resYear" value="${oldYear}" />
						<input type="hidden" name="resourceID" id="resourceID" value="${resourceID}" />

					<c:set var="counter" value="1"/>
			         <c:forEach var="i" begin="1" end="${startsWith - 1}">
						<td></td>
			         </c:forEach>
					<c:set var="counter" value="${startsWith}"/>
			         
						<c:forEach items="${month}" var="day" varStatus="i">
						

								<c:if test="${!consult[day]}">
									<c:if test="${fn:length(month) >= i.count}" >
										<td class="free">${day} <input type="checkbox" name="resDays" value="${day }"></td>
									</c:if>								
								</c:if>
								<c:if test="${consult[day]}">
									<c:if test="${fn:length(month) >= i.count}" >
										<td class="busy">${day}</td>
									</c:if>										
								</c:if>
								<c:if test="${counter % 7 == 0 }">
									</tr>
									<tr>
								</c:if>
								<c:set var="counter" value="${counter + 1}"/>
						</c:forEach>
				  </tbody>
			  </table>
			  		<div class="wrapper">				
					<input type="submit" class="btn btn-default btn-round btn-border-w" value="Reservar">
					</div>
			  </form>
			</c:if>
			
			</div>
	</div>
	</body>
</html>
<script>
$(document).ready(function(){
	console.log($("#type").val());
	changeType();
})
var typeDiv = document.getElementById("oldType"), oldType;
oldType = typeDiv.getAttribute("data-oldtype");

if (oldType != null){
	$("#type").val(oldType);
}

function changeType(){
	if ($("#type").val() == "day"){
		$("#daily").show();
		$("#monthly").hide();
	} else if ($("#type").val() =="month"){
		$("#monthly").show();
		$("#daily").hide();
	}
}

var monthDiv = document.getElementById("oldMonth"), oldMonth;
oldMonth = monthDiv.getAttribute("data-oldmonth");
if (oldMonth != null){
	$("#month").val(oldMonth);
}

var yearDiv = document.getElementById("oldYear"), oldYear;
oldYear = yearDiv.getAttribute("data-oldyear");

if (oldYear != null){
	$("#year").val(oldYear);
}

var initDiv = document.getElementById("oldInit"), oldInit;
oldInit = initDiv.getAttribute("data-oldinit");

if (oldInit != null){
	$("#initTimeMonth").val(oldInit);
}

var finalDiv = document.getElementById("oldFinal"), oldFinal;
oldFinal = finalDiv.getAttribute("data-oldfinal");

if (oldFinal != null){
	$("#finalTimeMonth").val(oldFinal).change();
}

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
			autoclose:true,
			startDate: date,
			format: 'd/m/yy'
});
jQuery('#consultDate').datepicker({ 
	timepicker:false,
	autoclose:true,
	startDate: date,
	 format: 'd/m/yy'
});

jQuery('#finalDate').datetimepicker({ 
	timepicker:false,
	format: 'd-m-yy'			 
});


</script>





<!-- <option value="00:00">00:00</option>
  <option value="01:00">01:00</option>
  <option value="02:00">02:00</option>
  <option value="03:00">03:00</option>
  <option value="04:00">04:00</option>
  <option value="05:00">05:00</option>
  <option value="06:00">06:00</option>
  <option value="07:00">07:00</option>
  <option value="08:00">08:00</option>
  <option value="09:00">09:00</option>
  <option value="10:00">10:00</option>
  <option value="11:00">11:00</option>
  <option value="12:00">12:00</option>
  <option value="13:00">13:00</option>
  <option value="14:00">14:00</option>
  <option value="15:00">15:00</option>
  <option value="16:00">16:00</option>
  <option value="17:00">17:00</option>
  <option value="18:00">18:00</option>
  <option value="19:00">19:00</option>
  <option value="20:00">20:00</option>
  <option value="21:00">21:00</option>
  <option value="22:00">22:00</option>
  <option value="23:00">23:00</option> -->
