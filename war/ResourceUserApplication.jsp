<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Resources</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main2">
	 <div class="container">
			<div>
				<h1>Resources</h1>
				<div>
					<a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
				</div>
	
		<p>You have a total number of <c:out value="${fn:length(resources)}" />
		Resources available.</p> 
		<div class="container row">
			<c:forEach items="${resources}" var="resource">
				<a><div class="container col-md-3 resource">
					<h4><c:out value="${resource.name}" /></h4>
					<h5><c:out value="${resource.state}" /></h5>
					<p><c:out value="${resource.description}" /></p>
					<span>
						<a class="btn btn-default" href="<c:url value="/reserve?id=${resource.id}" />">Reservar</a>
					</span>
				</div></a>
			</c:forEach>
			</div>
		</div>
	</body>
</html>
