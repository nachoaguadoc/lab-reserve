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
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
      <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
      <link rel="stylesheet" type="text/css" media="screen" href="http://silviomoreto.github.io/bootstrap-select/stylesheets/bootstrap-select.css">
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
      <script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
      <script src="http://silviomoreto.github.io/bootstrap-select/javascripts/bootstrap-select.js"></script>
		<link rel="stylesheet" type="text/css" href="js/jquery.datetimepicker.css"/ >
		
		<script src="js/jquery.datetimepicker.js"></script>
		
	</head>
	<body class="main2">
	<div class="container">
	<h1>Reserva de recursos</h1>
		<c:if test="${flashMessageError != null }">
	<div class="alert alert-error alert-dismissible" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  <strong>¡Error!</strong> ${flashMessageError}
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
						<table class="table">
							<input type="hidden" name="resourceID" id="resourceID" value="${resource.id}" /></td>
							
							<div class="input-group">
							  <span class="input-group-addon" id="sizing-addon2"></span>
							  
							  <input type="text" name="initDate" id="initDate" class="form-control" placeholder="Select Date" aria-describedby="sizing-addon2">
									<select class="selectpicker" data-style="size: auto" id="initTime" name="initTime">
								 <!-- <option value="00:00">00:00</option>
									  <option value="01:00">01:00</option>
									  <option value="02:00">02:00</option>
									  <option value="03:00">03:00</option>
									  <option value="04:00">04:00</option>
									  <option value="05:00">05:00</option>
									  <option value="06:00">06:00</option> -->
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
									  <!--<option value="22:00">22:00</option>
									  <option value="23:00">23:00</option> -->
		
									</select>                			
		                			<!-- <input id="finalDate" type="text" name="finalDate"> -->
		                			
		                			<select id="finalTime" name="finalTime" class="selectpicker">
									  <!-- <option value="00:00">00:00</option>
									  <option value="01:00">01:00</option>
									  <option value="02:00">02:00</option>
									  <option value="03:00">03:00</option>
									  <option value="04:00">04:00</option>
									  <option value="05:00">05:00</option>
									  <option value="06:00">06:00</option>
									  <option value="07:00">07:00</option> -->
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
								<!--  <option value="23:00">23:00</option> -->
									</select>
							<td colspan="2" align="center"><input class="btn btn-default btn-round btn-border-w" type="submit"
									value="Reservar" /> </td>
							</div>
							
     						</table>
					</form>
				</c:when>
				<c:otherwise>
	
	Please login with your Google account
				</c:otherwise>
			</c:choose>
			
			<!-- Consultar las reservas en un día concreto -->
			<form action="/consult?id=${resource.id}" method="post" accept-charset="utf-8">
				<table class="table">
					<input type="hidden" name="resourceID" id="resourceID" value="${resource.id}" /></td>
					
					<div class="input-group">
					  <span class="input-group-addon" id="sizing-addon2"></span>
					  
					  <input type="text" name="consultDate" id="consultDate" class="form-control" placeholder="Select Date" value="${dateSelected}" aria-describedby="sizing-addon2">

					<td colspan="2" align="center"><input class="btn btn-default btn-round btn-border-w" type="submit"
							value="Consultar" /> </td>
					</div>
					
   						</table>
			</form>
			
			<c:if test="${consult != null}">
				<h2>Consulta para fecha ${dateSelected}</h2>
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
jQuery('#initDate').datetimepicker({ 
			timepicker:false,
			 format: 'd/m/y'
});
jQuery('#consultDate').datetimepicker({ 
	timepicker:false,
	 format: 'd/m/y'
});

jQuery('#finalDate').datetimepicker({ 
	timepicker:false,
	format: 'd-m-y'			 
});

$('.selectpicker').selectpicker();

</script>
