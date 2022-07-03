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
    <title><fmt:message key="subjects.title"/></title>
</head>
<body>

<c:set var="pagePath" scope="session" value="/controller?command=show_all_subjects"/>
<%@ include file="/pages/static/header.jsp" %>
<h5><fmt:message key="subjects"/></h5>
<c:choose>
    <c:when test="${not empty subjectList}">

        <table id="myTable" class="display">
            <thead>
            <tr>
                <th><fmt:message key="Id"/></th>
                <th><fmt:message key="subjects"/></th>
                <td><b> </b></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${subjectList}" var="subject">
                <tr>
                    <td><c:out value="${subject.subjectId}"/></td>
                    <td><c:out value="${subject.name}"/></td>
                    <td><font
                    <c:if test="${ user.userRole == 'ADMIN' }">
                        <td align="center">
                            <form method="POST" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="delete_subject"/>
                                <input type="hidden" name="id" value="${subject.subjectId}"/>
                                <input type="submit" class=""
                                       value="<fmt:message key="title.deleteSubject"/>" onclick="clicked(event)"/>
                                <script>
                                    function clicked(e) {
                                        if (!confirm('<fmt:message key="title.remove_subject_confirmation"/>.'))
                                            e.preventDefault();
                                    }
                                </script>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p style="text-align: center"><font color="#b22222"><fmt:message key="no_subjects"/></font>
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
