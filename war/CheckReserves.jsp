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
			<div>
				<h1>Resources</h1>
				<div>
					<a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
				</div>
	
		<p>You have a total number of <c:out value="${fn:length(reserves)}" />
		Resources available.</p> 
		<div class="container row">
		
			<c:forEach items="${reserves}" var="reserve">
				<a><div class="container col-md-2 resource">
					<p>Resource: <c:out value="${reserve.resourceName}" /></p>
					<p>Date: <c:out value="${reserve.date}" /></p>
					<p>Init time: <c:out value="${reserve.initHour}" /></p>
					<p>Final time: <c:out value="${reserve.finalHour}" /></p>
					<span>
						<a class="btn btn-default" href="<c:url value="/update?id=${reserve.id}" />">Modify</a>
					</span>
					<span>
						<a class="btn btn-default" href="<c:url value="/cancel?id=${reserve.id}" />">Cancelar</a>
					</span>
				</div></a>
			</c:forEach>
			</div>
		</div>
	</body>
</html>
