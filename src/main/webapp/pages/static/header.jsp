<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="messages"/>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>
    <script>$(document).ready(function () {
        $('#myTable').DataTable();
    });</script>
    <title>Header</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <h5 class="navbar-brand"><fmt:message key="title.title"/></h5>
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=show_enrollments">
                    <fmt:message key="enrollments.results"/> </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=show_all_faculties">
                    <fmt:message key="show.faculties"/></a>
            </li>
            <c:if test="${ not empty user}">
                <div>
                    <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark">
                        <fmt:message
                                key="account.title"/></a>
                </div>
            </c:if>
        </ul>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <c:if test="${empty user}">
                <div aria-flowto="right">
                    <span class="badge badge-pill badge-danger"><ctg:role role="${ user.userRole }"/></span>
                </div>
            </c:if>
            <c:if test="${ user.userRole == 'ADMIN' }">
                <div aria-flowto="right">
                    <span class="badge badge-pill badge-warning"><ctg:role role="${ user.userRole }"/></span>
                </div>
            </c:if>
            <c:if test="${ user.userRole == 'ENTRANT' }">
                <div aria-flowto="right">
                    <span class="badge badge-pill badge-success"><ctg:role role="${ user.userRole }"/></span>
                </div>
            </c:if>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&newLanguage=en_En">
                    <i>
                        <span class="badge badge-pill badge-secondary">EN</span>
                    </i>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.session.servletContext.contextPath}/controller?command=change_language&newLanguage=ru_RU">
                    <i>
                        <span class="badge badge-pill badge-secondary">RU</span>
                    </i>
                </a>
            </li>
            <c:choose>
                <c:when test="${empty user}">
                    <li class="nav-item">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/pages/login.jsp"><i
                                class="fa fa-sign-in fa-lg fa-fw"></i><fmt:message
                                key="login.submit"/></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/controller?command=logout"><i
                                class="fa fa-sign-out fa-lg fa-fw"></i><fmt:message
                                key="title.signOut"/></a>
                    </li>
                </c:otherwise>
            </c:choose>
            <c:if test="${empty user}">
                <li class="nav-item">
                    <a class="btn btn-secondary"
                       href="${pageContext.request.contextPath}/pages/registration.jsp"><i
                            class="fa fa-user-plus fa-lg fa-fw"></i><fmt:message
                            key="button.signUp"/></a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
</body>
</html>
