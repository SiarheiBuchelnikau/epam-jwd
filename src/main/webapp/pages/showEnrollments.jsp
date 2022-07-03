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
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>
    <script>$(document).ready(function () {
        $('#myTable').DataTable();
    });</script>
    <title><fmt:message key="Enrolments.title"/></title>
</head>
<body>

<c:set var="pagePath" scope="session" value="/controller?command=show_enrollments"/>
<%@ include file="/pages/static/header.jsp" %>
<h5><fmt:message key="title.enrollment"/></h5>
<c:choose>
    <c:when test="${not empty enrollments}">

        <table id="myTable" class="display">
            <thead>
            <tr>
                <th><fmt:message key="enrollment"/></th>
                <th><fmt:message key="start_date"/></th>
                <th><fmt:message key="end_date"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${enrollments}" var="enrollment">
                <tr>
                    <td>
                        <a style="color: black"
                           href="${pageContext.request.contextPath}/controller?command=show_applicants&id=${enrollment.enrollmentId}">
                                <fmt:message key="enrollment"/> <c:out value="${enrollment.enrollmentId}"/></a>
                    </td>
                    <td><c:out value="${enrollment.startDate}"/></td>
                    <td><c:out value="${enrollment.endDate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <br>
        <div class="alert alert-info">
            No enrollment found matching your search criteria
        </div>
    </c:otherwise>
</c:choose>
<br>
<%--<c:if test="${user.userRole=='ADMIN'}">--%>
<%--    <div>--%>
<%--        <a href="${pageContext.request.contextPath}/controller?command=add_faculty" class="btn btn-dark"> <fmt:message--%>
<%--                key="title.addFaculty"/></a>--%>
<%--    </div>--%>
<%--</c:if>--%>
<br>
<div>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message
            key="back.toCabinet"/></a>
</div>
<%--<%@ include file="/pages/static/footer.jsp" %>--%>
</body>
</html>
