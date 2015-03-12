<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>


<!DOCTYPE html>


<html>
	<head>
		<title>Resources</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body>
	
		<div class="container">
				<h1>New Resource</h1>
				<div>
					<a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
				</div>
				
		<div class="container row col-md-6 col-md-offset-3">
		
			<c:choose>
				<c:when test="${user != null}">
					<form action="/new" method="post" accept-charset="utf-8">
						<table class="table table-hover">
							<tr>
								<td><label for="name">Name</label></td>
								<td><input type="text" name="name" id="name" size="65" /></td>
							</tr>
							<tr>
								<td valign="description"><label for="description">Description</label></td>
								<td><textarea rows="4" cols="50" name="description"
										id="description"></textarea></td>
							</tr>
							<tr>
								<td valign="state"><label for="state">State</label></td>
								<td><textarea rows="1" cols="50" name="state"
										id="state"></textarea></td>
							</tr>
						
							<tr>
								<td colspan="2" align="right"><input type="submit"
									value="Create" /></td>
							</tr>
						</table>
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
