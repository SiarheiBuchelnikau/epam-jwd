<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <title><fmt:message key="account.title"/></title>
</head>
<body>
<c:set var="pagePath" value="/pages/entrant/entrantCabinet.jsp" scope="session"/>
<%@ include file="/pages/static/header.jsp" %>

<h5><a class="badge badge-info" href="${pageContext.request.contextPath}/controller?command=show_all_faculties">
    <fmt:message key="title.showFaculties"/></a></h5>

<h5><a class="badge badge-info" href="${pageContext.request.contextPath}/controller?command=go_to_cabinet">
    <fmt:message key="profile.title"/> </a></h5>

<strong><p class="text-danger"> ${blockedUserError} </p></strong>

<footer class=" fixed-bottom">
    <%@ include file="/pages/static/footer.jsp" %>
</footer>
</body>
</html>
