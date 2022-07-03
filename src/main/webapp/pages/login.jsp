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
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<c:set var="pagePath" value="/pages/login.jsp" scope="session"/>
<%@ include file="/pages/static/header.jsp" %>
<%--<br/>--%>
<fmt:message key="title.enrollment"/>
<c:if test="${not empty enrollment}"> №<c:out value="${enrollment.enrollmentId}"/>
</c:if>:
<c:choose>
    <c:when test="${enrollment.state=='OPENED'}"><font color="#008b8b"><fmt:message key="message.opened"/></font></>
        <fmt:message key="message.start_date"/><font color="#008b8b"><fmt:formatDate
                value="${enrollment.startDate}"
                pattern="dd-MM-yyyy HH:mm"/></font></>
    </c:when>
    <c:otherwise><font color="#b22222"><fmt:message key="message.closed"/></font></h5>
        <c:if test="${not empty enrollment}"><fmt:message key="message.end_date"/><font color="#b22222"><fmt:formatDate
                value="${enrollment.endDate}"
                pattern="dd-MM-yyyy HH:mm"/></font></></c:if>
        <br/>
        <font color="#b22222"><fmt:message key="message.come_back_later"/></font></>
    </c:otherwise>
</c:choose>
<div class="container">
    <form name="loginForm" method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="login"/>
        <fieldset>
            <legend>
                <h4><b></b></h4>
            </legend>
            <div>
                <label class="col-md-6 control-label"><fmt:message key="login.title"/> </label>
                <div class="form-group col-md-4">
                    <input class="form-control" type="text" name="login" value=""
                           required
                           pattern="^[a-zA-Z0-9]+$"
                           minlength="1"
                           maxlength="45"
                           placeholder="<fmt:message key="login.placeholder"/>"/>
                </div>
            </div>
            <div>
                <label class="col-md-6 control-label"><fmt:message key="login.password"/></label>
                <div class="form-group col-md-4">
                    <input class="form-control" type="password" name="password" value="${fn:escapeXml(password)}"
                           required
                           maxlength="45"
                           minlength="8"
                           pattern="((?=.*[a-z])(?=.*\d)(?=.*[A-Z])(?=.*[#$%!\-]).{8,45})"
                           placeholder="<fmt:message key="login.password" />"/>
                </div>
            </div>
            <input type="submit" class="btn btn-dark" value="<fmt:message key="login.submit"/>"/>
        </fieldset>
    </form>
    <strong><p class="text-danger"> ${blockedUserError} </p></strong>
    <strong><p class="text-danger"> ${fn:escapeXml(loginError)} </p></strong>
    <strong><p class="text-danger"> ${wrongAction} </p></strong>
    <strong><p class="text-danger"> ${nullPage} </p></strong>
    <strong><p class="text-danger"> ${recoverSuccess} </p></strong>
    <a href="${pageContext.request.contextPath}/pages/registration.jsp"> <fmt:message
            key="registration.account"/> </a>
    <br>
    <a href="${pageContext.request.contextPath}/pages/recoverPassword.jsp"><fmt:message
            key="password.forgot"/></a>
</div>
<footer class=" fixed-bottom">
    <%@ include file="/pages/static/footer.jsp" %>
</footer>
</body>
</html>
