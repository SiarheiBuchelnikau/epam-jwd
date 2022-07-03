<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
Hello (forward)= ${user}
<hr/>
Hi (redirect/forward) = ${user_name}
<hr/>
${filter_atr}
<hr/>
<form action="controller">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="logOut"/>
</form>
</body>
</html>
