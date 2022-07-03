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

<c:set var="pagePath" scope="session" value="/controller?command=show_applicants"/>
<%@ include file="/pages/static/header.jsp" %>
<h5><fmt:message key="title.applicants"/></h5>
<c:choose>
    <c:when test="${not empty applicants}">

        <table id="myTable" class="display">
            <thead>
            <tr>
                <th><fmt:message key="applicant"/></th>
                <th><fmt:message key="faculty"/></th>
                <th><fmt:message key="total_rating"/></th>
                <th><fmt:message key="status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${applicants}" var="applicant">
                <tr>
                    <td>
                        <a style="color: black"
                           href="${pageContext.request.contextPath}/controller?command=show_application&id=${applicant.id}"><c:out
                                value="${applicant.lastName}"/> <c:out value="${applicant.firstName}"/> <c:out
                                value="${applicant.patronymic}"/></a>
                    </td>
                    <td><c:out value="${applicant.facultyName}"/></td>
                    <td><c:out value="${applicant.totalRating}"/></td>
                    <td><font
                    <c:choose>
                            <c:when test="${applicant.applicantState=='NOT_ENROLLED'}">color="#b22222"</c:when>
                            <c:otherwise>color="#008b8b"</c:otherwise>
                    </c:choose>>
                            <c:out value="${applicant.applicantState}"/><font/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p style="text-align: center"><font color="#b22222"><fmt:message key="no_enrollments"/></font>
        </p>
    </c:otherwise>
</c:choose>
<div>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message
            key="back.toCabinet"/></a>
</div>
<%--<%@ include file="/pages/static/footer.jsp" %>--%>
</body>
</html>
