<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 03.03.14
  Time: 02:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feeds</title>
</head>
<body>
<form action="MainPageServlet" method="post">

<input type="text" value = "<%out.print(Login.DataBase.getLastLogin());%>" disabled>


</form>
</body>
</html>
