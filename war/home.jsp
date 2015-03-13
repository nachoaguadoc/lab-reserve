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
		<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		<link href='http://fonts.googleapis.com/css?family=Oswald:700|Plaster' rel='stylesheet' type='text/css'>
		<script src="js/bootstrap.min.js"></script>
		<meta charset="utf-8">
	</head>
	<body class="main">
  			<div class="container">
  			<div class="row vertical-center-row">
        <div class="col-lg-12">
            <div class="row ">
                <div class="title col-md-offset-2 col-md-8"><h1 class="t1">LabReserve </h1><h4 class="t2">Gestor de puestos científicos</h4>
                <div id="login" class="row">
					<a class="btn btn-border-p btn-round" href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
					</a>
				</div>
                </div>
            </div>
        </div>
    </div>
    </div>
</body>
</html>
