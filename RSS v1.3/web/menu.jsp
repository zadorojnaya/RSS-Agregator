<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 12.03.14
  Time: 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<core:forEach var="object" items="${requestScope.objectList}">
    <a href="/link?id=${object.id}">name</a>
</core:forEach>
</body>
</html>
