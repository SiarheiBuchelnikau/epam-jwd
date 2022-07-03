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
    <title><fmt:message key="faculty.tittle"/></title>
</head>
<body>
<c:set var="pagePath" scope="session" value="/pages/admin/addFaculty.jsp"/>
<%@ include file="/pages/static/header.jsp" %>
<div class="container">
    <form class="well form-horizontal" name="addFaculty"
          method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="add_faculty"/>
        <fieldset>
            <legend>
                <h5><b><p><fmt:message key="registration.legend"/></b></h5>
            </legend>
            <div>
                <label class="col-md-6 control-label"><fmt:message key="faculty.name"/> <span
                        class="text-danger">*</span></label>
                <div class="form-group col-md-6">
                    <input class="form-control" type="text" name="name"
                           required
                           pattern="[A-Z][a-z]{1,20}(\s?[A-Za-z][a-z]{1,20})*"
                           minlength="1"
                           maxlength="20"
                           placeholder="<fmt:message key="faculty.name"/>"/>
                </div>
            </div>
            <strong><p class="text-danger"> ${map.incorrectName} </p></strong>
            <div>
                <label class="col-md-6 control-label"><fmt:message key="capacity"/><span
                        class="text-danger">*</span></label>
                <div class="form-group col-md-4">
                    <input class="form-control" type="number" name="capacity"
                           pattern="\d+"
                           min="2" max="5"
                           required
                           placeholder="<fmt:message key="capacity" />"/>
                </div>
            </div>
            <strong><p class="text-danger"> ${map.incorrectCapacity} </p></strong>
            <div class="form-check form-check-inline">
                    <c:forEach items="${subjectList}" var="subject">
                        <input input class="form-check-input" type="checkbox" name="subjectIdList" value="${subject.subjectId}"><c:out value="  ${subject.name} "  />
                    </c:forEach>
            </div>
                    <input type="submit" class="btn btn-dark" value="<fmt:message key="addFaculty.submit"/> ">

                    <strong><p class="text-danger"> ${errorAddFaculty} </p></strong>
                    <strong><p class="text-danger"> ${errorValidateFaculty} </p></strong>

        </fieldset>
    </form>
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message
                key="back.toCabinet"/></a>
    </div>
</div>
<c:remove var="error" scope="session"/>

<%--<%@ include file="/pages/static/footer.jsp" %>--%>

</body>
</html>
