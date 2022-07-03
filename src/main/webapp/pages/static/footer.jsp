<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <style>
        footer {
            position: fixed;
            left: 0;
            bottom: 0;
            width: 100%;
            color: white;
            text-align: center;
        }
    </style>

    <title>Title</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>

<!-- Footer -->
<body>

<footer class=" bg-dark text-white mt-4">

    <!-- Footer Links -->
    <div class="container-fluid text-center text-md-left">

        <!-- Grid row -->
        <div class="row">
            <div class="col">
                <h5 class="text-uppercase"><fmt:message key="title.contactUs"/></h5>
                <spanp>Tel: 375-29-781-31-81</spanp>
                <br>
                <spanp>Email: waylay@list.ru</spanp>
            </div>

            <div class="col">
                <br>
                <ul class="list-unstyled">
                    <li>
                        <a class="bg-dark text-white" href="${pageContext.request.contextPath}/pages/contact.jsp">
                            <fmt:message key="title.support"/></a>
                    </li>
                </ul>
            </div>
            <div class="col">
                <br>
                <ul class="list-unstyled">
                    <li>
                        <a class="bg-dark text-white" href="${pageContext.request.contextPath}/pages/termsOfUse.jsp">
                            <fmt:message
                                    key="title.termsOfUse"/></a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer-copyright text-center py-3">© 2022 Copyright:
            <a>SiarheiBuchelnikau</a>
        </div>
    </div>
</footer>
</body>
</html>