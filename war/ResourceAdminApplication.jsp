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
	</head>
	<body>
	
		<div style="width: 100%;">
			<div class="line"></div>
			<div class="topLine">
				<div style="float: left;" class="headline">Resources</div>
				<div style="float: right;">
					<a
						href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
				</div>
			</div>
		</div>
	
		<div style="clear: both;" />
		You have a total number of <c:out value="${fn:length(resources)}" />
		Resources.
		<a href="new"> New resource </a>
		<table>
			<tr>
				<th>Name</th>
				<th>State</th>
				<th>Description</th>
				
			</tr>
	
			<c:forEach items="${resources}" var="resource">
				<tr>
					<td><c:out value="${resource.name}" /></td>
					<td><c:out value="${resource.state}" /></td>
					<td><c:out value="${resource.description}" /></td>
					
					<td>
						<a class="remove" href="<c:url value="/remove?id=${resource.id}" />">Borrar</a>
					</td>
										
					<td>
						<a class="remove" href="<c:url value="/modify?id=${resource.id}" />">Modificar</a>
					</td>
					
					<!--	<td><a class="remove"
						href="<c:url value="/reserve?id=${resource.id}" />">Reservar</a></td> -->
				</tr>
			</c:forEach>
		</table>
	
	
		<hr />
	
	</body>
</html>
