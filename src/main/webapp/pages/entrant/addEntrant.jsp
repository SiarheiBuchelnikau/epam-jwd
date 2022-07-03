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
    <title><fmt:message key="tittle.add_entrant"/></title>
</head>
<body>
<c:set var="pagePath" scope="session" value="/pages/entrant/addEntrant.jsp"/>
<%@ include file="/pages/static/header.jsp" %>

<h5><fmt:message key="apply_for"/> <font color="#008b8b"><c:out value="${faculty.name}"/></font>:</h5>

<div class="container">
    <form class="well form-horizontal" name="addEntrant"
          method="POST" action="${pageContext.request.contextPath}/controller">

        <input type="hidden" name="command" value="add_entrant"/>
        <fieldset>
            <legend>
                <h5><b><p><fmt:message key="registration.legend"/></b></h5>
            </legend>
            <div>
                <label class="col-md-6 control-label"><fmt:message key="faculty.name"/> <span
                        class="text-danger">*</span></label>
                <div class="form-group col-md-4">
                    <c:forEach items="${faculty.requiredSubjects}" var="subject">
                        <input class="register_field" name="grade"
                               pattern="\d+"
                               type="number" min="10" max="100"
                               required
                               placeholder="${subject.name}"/><br>
                    </c:forEach>
                </div>
            </div>
            <br>
            <input type="hidden" name="id" value="${faculty.facultyId}"/>
            <input type="submit" class="btn btn-dark" value="<fmt:message key="addGrades.submit"/> ">

            <strong><p class="text-danger"> ${errorAddFaculty} </p></strong>
            <strong><p class="text-danger"> ${errorValidateFaculty} </p></strong>

        </fieldset>
    </form>
    <br>
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message
                key="back.toCabinet"/></a>
    </div>
</div>
<c:remove var="error" scope="session"/>

<%@ include file="/pages/static/footer.jsp" %>

</body>
</html>
