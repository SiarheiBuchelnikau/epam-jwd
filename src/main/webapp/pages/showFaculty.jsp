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
    <title><fmt:message key="faculty.title"/></title>
</head>
<body>
<c:set var="pagePath" scope="session" value="/controller?command=show_faculty"/>
<%@ include file="/pages/static/header.jsp" %>
    <h5><fmt:message key="faculty"/>: <font color="#008b8b"><c:out value="${faculty.name}"/></font></h5>

    <ol>
        <ul><fmt:message key="faculty.capacity"/>: <font color="#008b8b"><c:out value="${faculty.capacity}"/></font></ul>
        <ul><fmt:message key="required_subjects"/>: <c:forEach items="${faculty.requiredSubjects}" var="subject" varStatus="loop">
            <font
                    color="#008b8b"><c:out value="${subject.name}"/></font><c:if test="${!loop.last}">, </c:if></c:forEach></ul>
    </ol>
    <c:if test="${user.userRole=='ENTRANT'  && enrollment.state=='OPENED'}">
        <form class="btn btn-dark" action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="add_entrant"/>
            <input type="hidden" name="id" value="${faculty.facultyId}"/>
            <input  type="submit" value="<fmt:message key="apply"/>"/>
        </form>
    </c:if>
<br>
    <c:if test="${user==null && enrollment.state=='OPENED'}">

        <div class="btn btn-dark">
            <a href="${pageContext.request.contextPath}/pages/registration.jsp"> <fmt:message
                    key="registration.account"/> </a>
        </div>
    </c:if>

    <c:if test="${user.userRole=='ADMIN'}">
       <form method="POST" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="delete_faculty"/>
                            <input type="hidden" name="id" value="${faculty.facultyId}"/>

                            <input type="submit" class="btn btn-dark"
                                   value="<fmt:message key="title.deleteFaculty"/>"onclick="clicked(event)"/>
                            <script>
                                function clicked(e) {
                                    if (!confirm('${faculty.name} <fmt:message key="title.remove_faculty_confirmation"/>.'))
                                        e.preventDefault();
                                }
                            </script>
        </form>
    </c:if>
</div>
<br>
<br>
<div>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message key="back.toCabinet"/></a>
</div>
<br>
<br>
<br>
<br>
<br>
<%@ include file="/pages/static/footer.jsp" %>
</body>
</html>
