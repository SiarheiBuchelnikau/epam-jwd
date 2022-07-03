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
    <title><fmt:message key="password.recover"/></title>
</head>
<body>
<c:set var="pagePath" value="/pages/recoverPassword.jsp" scope="session"/>
<%@ include file="/pages/static/header.jsp" %>
<div class="container">
    <form name="passwordRecover" method="post"
          action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="recover_password">
        <fieldset>
            <legend>
                <h2><fmt:message key="password.recoverMessage"/></h2><br>
            </legend>
            <div class="form-group col-md-4">
                <small><fmt:message key="password.recoverInstruction"/> </small>
            </div>
            <div>
                <label class="col-md-6 control-label"><fmt:message key="login.title"/> </label>
                <div class="form-group col-md-4">
                    <input class="form-control" type="text" name="login" value="${fn:escapeXml(login)}"
                           required
                           pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$"
                           title="Login should only contain letters and numbs. First symbol must be letter e.g. f123"
                           minlength="1"
                           maxlength="20"
                           placeholder="<fmt:message key="login.placeholder"/>"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4"><br>

                    <input type="submit" class="btn btn-dark" value="<fmt:message key="password.recoverButton"/>"/>
                </div>
            </div>

        </fieldset>
    </form>
    <strong><p class="text-danger"> ${recoverNoLogin} </p></strong>
    <strong><p class="text-danger"> ${recoverError} </p></strong>
    <div>
        <a href="${pageContext.request.contextPath}/pages/login.jsp"> <fmt:message
                key="back.toLogin"/> </a>
    </div>
</div>
<%@ include file="/pages/static/footer.jsp" %>
</body>
</html>
