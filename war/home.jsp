<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>LabReserve</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<link href='http://fonts.googleapis.com/css?family=Oswald:700|Plaster' rel='stylesheet' type='text/css'>
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main">
  			<div class="container">
  			<div class="row vertical-center-row">
        <div class="col-lg-12">
            <div class="row ">
                <div class="col-xs-4 col-xs-offset-4 title"><h1 class="t1">LabReserve </h1><h4 class="t2">Gestor de puestos científicos</h4>
                <div id="login" class="row">
					<button type="button" class="btn btn-default"><a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
					</button>
				</div>
                </div>
            </div>
        </div>
    </div>
    </div>
</body>
</html>
