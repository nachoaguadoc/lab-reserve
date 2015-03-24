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
jQuery('#initDate').datetimepicker({ 
			timepicker:false,
			 format: 'd/m/y'
});

jQuery('#finalDate').datetimepicker({ 
	timepicker:false,
	format: 'd-m-y'			 
});

$('.selectpicker').selectpicker();

</script>
