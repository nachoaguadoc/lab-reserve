<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>


<!DOCTYPE html>


<html>
	<head>
		<title>Parámetros de la instalación </title>
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
				<h1>Fijar parámetros</h1>				
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
			
				
		<div class="container row col-md-6 col-md-offset-3">
		
			<c:choose>
				<c:when test="${user != null}">
					<form action="/config" method="post" accept-charset="utf-8" class="form">
						<div class="form-group">
							
								<label for="name">Correo del administrador</label>
								<input type="text" class="form-control col-md-4" name="webmaster" id="webmaster" placeholder="Correo"/>
								<br><br><br><br>
									<label>Máximo tiempo de sesión</label>
									<br>
						
									<span><select class="selectpicker col-md-4" id="sessionTime" name="sessionTime"></span>
								 	  <option value="1">1</option>
									  <option value="2">2</option>
									  <option value="3">3</option>
									  <option value="4">4</option>
									  <option value="5">5</option>
									  <option value="6">6</option>
									  <option value="7">7</option>

		
									</select>     
							    <br><br><br>
								<label>Horario de la instalación</label>
									<br>						    
							 	<span><select class="selectpicker col-md-4" id="opening" name="opening"></span>
									  <option value="01">01:00</option>
									  <option value="02">02:00</option>
									  <option value="03">03:00</option>
									  <option value="04">04:00</option>
									  <option value="05">05:00</option>
									  <option value="06">06:00</option>
									  <option value="07">07:00</option>
									  <option value="08">08:00</option>
									  <option value="09">09:00</option>
									  <option value="10">10:00</option>
									  <option value="11">11:00</option>
									  <option value="12">12:00</option>
									  <option value="13">13:00</option>
									  <option value="14">14:00</option>
									  <option value="15">15:00</option>
									  <option value="16">16:00</option>
									  <option value="17">17:00</option>
									  <option value="18">18:00</option>
									  <option value="19">19:00</option>
									  <option value="20">20:00</option>
									  <option value="21">21:00</option>
									  <option value="22">22:00</option>
									  <option value="23">23:00</option>
									  <option value="24">24:00</option>
									</select>  
									
									<span><select class="selectpicker col-md-4" id="closing" name="closing"></span>
							    
									  <option value="01">01:00</option>
									  <option value="02">02:00</option>
									  <option value="03">03:00</option>
									  <option value="04">04:00</option>
									  <option value="05">05:00</option>
									  <option value="06">06:00</option>
									  <option value="07">07:00</option>
									  <option value="08">08:00</option>
									  <option value="09">09:00</option>
									  <option value="10">10:00</option>
									  <option value="11">11:00</option>
									  <option value="12">12:00</option>
									  <option value="13">13:00</option>
									  <option value="14">14:00</option>
									  <option value="15">15:00</option>
									  <option value="16">16:00</option>
									  <option value="17">17:00</option>
									  <option value="18">18:00</option>
									  <option value="19">19:00</option>
									  <option value="20">20:00</option>
									  <option value="21">21:00</option>
									  <option value="22">22:00</option>
									  <option value="23">23:00</option>
									  <option value="24">24:00</option>
									</select>  
																    <br><br><br>
									                	
								<input type="submit" value="Fijar" class="btn btn-rounded btn-border-w" />
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
