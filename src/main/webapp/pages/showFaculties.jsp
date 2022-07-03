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
    <title><fmt:message key="faculty.title"/></title>
</head>
<body>

<c:set var="pagePath" scope="session" value="/controller?command=show_all_faculties"/>
<%@ include file="/pages/static/header.jsp" %>
<h5><fmt:message key="title.faculties"/></h5>
<c:choose>
    <c:when test="${not empty listOfFaculty}">

        <table id="myTable" class="display">
            <thead>
            <tr>
                <th><fmt:message key="title.faculty_id"/></th>
                <th><fmt:message key="title.faculty_name"/></th>
                <th><fmt:message key="title.faculty_capacity"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listOfFaculty}" var="faculty">
                <tr>
                    <td><c:out value="${faculty.facultyId}"/></td>
                    <td>
                        <a style="color: black"
                           href="${pageContext.request.contextPath}/controller?command=show_faculty&id=${faculty.facultyId}"><c:out
                                value="${faculty.name}"/></a>
                    </td>
                    <td><c:out value="${faculty.capacity}"/></td>
<%--                    <c:if test="${user.userRole=='ADMIN'}">--%>
<%--                        <td>--%>
<%--                            <form method="POST" action="${pageContext.request.contextPath}/controller">--%>
<%--                                <input type="hidden" name="command" value="delete_faculty"/>--%>
<%--                                <input type="hidden" name="id" value="${faculty.facultyId}"/>--%>
<%--                                <input type="hidden" name="start" value="${param.start}"/>--%>
<%-- Not sure                               <input type="submit" class="btn btn-dark"--%>
<%--                                       value="<fmt:message key="title.deleteFaculty"/>" onclick="clicked(event)"/>--%>
<%--                                <script>--%>
<%--                                    function clicked(e) {--%>
<%--                                        if (!confirm('<fmt:message key="title.remove_faculty_confirmation"/>.'))--%>
<%--                                            e.preventDefault();--%>
<%--                                    }--%>
<%--                                </script>--%>
<%--                            </form>--%>
<%--                        </td>--%>
<%--                    </c:if>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <br>
        <div class="alert alert-info">
            No faculty found matching your search criteria
        </div>
    </c:otherwise>
</c:choose>
<br>
<c:if test="${user.userRole=='ADMIN'}">
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=add_faculty" class="btn btn-dark"> <fmt:message
                key="title.addFaculty"/></a>
    </div>
</c:if>
<br>
<div>
    <a href="${pageContext.request.contextPath}/controller?command=go_to_cabinet" class="btn btn-dark"> <fmt:message
            key="back.toCabinet"/></a>
</div>
<%--<%@ include file="/pages/static/footer.jsp" %>--%>
</body>
</html>
