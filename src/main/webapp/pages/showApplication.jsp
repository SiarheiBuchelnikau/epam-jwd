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
    <title><fmt:message key="Application.title"/></title>
</head>
<body>

<c:set var="pagePath" scope="session" value="/controller?command=show_application"/>
<%@ include file="/pages/static/header.jsp" %>
<h5><fmt:message key="title.application"/></h5>
    <div style="margin-left: 30%">
        <p class="personalData"><fmt:message key="applicant_name"/>: <font
                color="#008b8b"><c:out value="${applicant.lastName}"/> <c:out value="${applicant.firstName}"/> <c:out value="${applicant.patronymic}"/></font></p>
        <p class="personalData"><fmt:message key="enrollment"/>: <font
                color="#008b8b">№<c:out value="${applicant.enrollmentId}"/></font>
        </p>
        <p class="personalData"><fmt:message key="faculty"/>: <font color="#008b8b"><c:out value="${applicant.facultyName}"/></font>
        </p>
        <p class="personalData"><fmt:message key="subjects"/>:
            <c:forEach items="${subjects}" var="subject" varStatus="loop"><c:out value="${subject.name}"/> <font
                    color="#008b8b"><c:out value="${subject.grade}"/></font><c:if test="${!loop.last}">, </c:if></c:forEach>
        </p>
        <p class="personalData"><fmt:message key="application_state"/>: <font color="<c:choose>
                                <c:when test="${applicant.applicantState=='NOT_ENROLLED'}">firebrick</c:when>
                                    <c:otherwise>darkcyan</c:otherwise></c:choose>">
            <c:out value="${applicant.applicantState}"/></font></p>
        <c:if test="${applicant.applicantState=='APPLIED' && user.userId==applicant.userId}">
            <form action="Committee" method="post">
                <input type="hidden" name="command" value="cancel_application"/>
                <input type="hidden" name="applicant_id" value="${applicant.id}"/>
                <input class="submitButton" type="submit" value="<fmt:message key="cancel"/>" onclick="clicked(event)"/>
                <script>
                    function clicked(e) {
                        if (!confirm('<fmt:message key="cancel_application_confirmation"/>.'))
                            e.preventDefault();
                    }
                </script>
            </form>
        </c:if>
    </div>
<div>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message
            key="back.toCabinet"/></a>
</div>
<%@ include file="/pages/static/footer.jsp" %>
</body>
</html>
