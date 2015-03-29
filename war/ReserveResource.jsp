<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Resources</title>
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
				
					<form action="/reserve?id=${resource.id}" method="post" accept-charset="utf-8">
							<input type="hidden" name="resourceID" id="resourceID" value="${resource.id}" />
							
							  
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
						<br><br><br>
				</c:when>
				<c:otherwise>
	
	Please login with your Google account
				</c:otherwise>
			</c:choose>
			<br><br>
			<!-- Consultar las reservas en un día concreto -->
			<form action="/consult?id=${resource.id}" method="post" accept-charset="utf-8">
					<input type="hidden" name="resourceID" id="resourceID" value="${resource.id}" />
					

					  
					  <input type="text" name="consultDate" id="consultDate" class="form-control max-width ol-md-4" placeholder="Select Date" value="${dateSelected}">
					<br><br><br>
					<div class="wrapper">
					<input class="btn btn-default btn-round btn-border-w" id="buttonConsult" type="submit"
							value="Consultar" />
							</div>
					
			</form>
			<br><br>
			
			<c:if test="${consult != null}">
				<h2>Horas libres el ${dateSelected}</h2>
				<table class="table table-bordered">
				<tbody>
					<c:set var="counter" value="1"/>
					<c:forEach items="${list}" var="hour" varStatus="status">

							<c:if test="${!consult[hour]}">
								
								<td class="free">${hour}</td>								
							</c:if>
							<c:if test="${consult[hour]}">
								<td class="busy">${hour }</td>								
							</c:if>
							<c:if test="${counter % 4 == 0 }">
								</tr>
								<tr>
							</c:if>
							<c:set var="counter" value="${counter + 1}"/>
					</c:forEach>
<!-- 				    <tr>
				      <td class="free">01:00</td>
				      <td class="busy">02:00</td>
				      <td class="disabled">03:00</td>
				      <td>04:00</td>
				    </tr>
				    <tr>
				      <td>05:00</td>
				      <td>06:00</td>
				      <td>07:00</td>
				      <td>08:00</td>
				    </tr>
				    <tr>
				      <td>09:00</td>
				      <td>10:00</td>
				      <td>11:00</td>
				      <td>12:00</td>
				    </tr>      <tr>
				      <td>13:00</td>
				      <td>14:00</td>
				      <td>15:00</td>
				      <td>16:00</td>
				    </tr>      <tr>
				      <td>17:00</td>
				      <td>18:00</td>
				      <td>19:00</td>
				      <td>20:00</td>
				    </tr>      <tr>
				      <td>21:00</td>
				      <td>22:00</td>
				      <td>23:00</td>
				      <td>00:00</td>
				    </tr> -->
				  </tbody>
			  </table>
			</c:if>
			
			</div>
	</div>
	</body>
</html>
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
