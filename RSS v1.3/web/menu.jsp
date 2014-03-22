<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 12.03.14
  Time: 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <style>  input.flat
     {border:solid 1px black;}
     A {text-decoration: none;}
       body {margin:0;}</style>
</head>
<body onLoad="parent.news.document.location = 'news.jsp'">
<form action="Login" method="post">
    <c:if test="${list != null }">
        <c:forEach  var="button" items="${list}">
            <input class="flat" style=" width:100%;" type =submit name = "News" value ="${button.name}">
        </c:forEach>
    </c:if>
    <c:if test="${list == null }">
        Add feeds!
    </c:if>
</form>
</body>
</html>
