<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 12.03.14
  Time: 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="styles.css"/>
<html>
<head>
    <title></title>


</head>
<body onLoad="parent.news.document.location = 'news.jsp'" class="menu">
<form action="Login" method="post">
    <input type="submit" value="All Feeds" name="News"  class="allFeedButton" >
    <br>
    <c:if test="${uData.linksList != null }">
        <c:forEach  var="button" items="${uData.linksList}">
            <input class="menuButton" type =submit name = "News" value ="${button.name}">
        </c:forEach>
    </c:if>
    <c:if test="${uData.linksList == null }">
        Add feeds!
    </c:if>
</form>
</body>
</html>
