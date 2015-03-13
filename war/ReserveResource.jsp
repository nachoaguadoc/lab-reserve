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
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="js/jquery.datetimepicker.css"/ >
		<script src="js/jquery.datetimepicker.js"></script>
		
	</head>
	<body class="main2">
	<div class="container">
	<h1>Reserva de recursos</h1>
		<div>
					<a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
				</div>
		
		<div>
			<c:choose>
				<c:when test="${user != null}">
					<form action="/reserve?id=${resource.id}" method="post" accept-charset="utf-8">
						<table class="table">
							<input type="hidden" name="resourceID" id="resourceID" value="${resource.id}" /></td>
                			<input id="initDate" type="text" name="initDate">
							<select id="initTime" name="initTime">
							  <option value="00:00">00:00</option>
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
							  <option value="23:00">23:00</option>

							</select>                			
                			<!-- <input id="finalDate" type="text" name="finalDate"> -->
                			
                			

							<select id="finalTime" name="finalTime">
							  <option value="00:00">00:00</option>
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
							  <option value="23:00">23:00</option>
                			
	                		<tr>
								<td colspan="2" align="right"><input type="submit"
									value="Reserve" /></td>
							</tr>
						</table>
					</form>
				</c:when>
				<c:otherwise>
	
	Please login with your Google account
				</c:otherwise>
			</c:choose>
			<div>						${message}
			</div>
		</div>
	</div>
	</body>
</html>
<script>
jQuery('#initDate').datetimepicker({ 
			timepicker:false,
			 format: 'd/m/y',
			 mask: true
});

jQuery('#finalDate').datetimepicker({ 
	timepicker:false,
	format: 'd-m-y'			 
});

</script>
