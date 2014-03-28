<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 12.03.14
  Time: 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="styles.css"/>
<html>
<head>
    <title></title>
</head>
<body class="feeds">
<form action="Login" method="post">
    <c:if test="${uData.sort}">
         <c:set var="sort" value="Sort by date: old is first"/>
    </c:if>
    <c:if test="${!uData.sort}">
        <c:set var="sort" value="Sort by date: new is first"/>
    </c:if>
    <table width="100%" height="100%" class="fonts">
        <tr>
            <td rowspan="5" width="20%">
                <IFrame src="menu.jsp"  class="frames"
                        scrolling="yes" name="frameMenu"></IFrame>
            </td>
            <th colspan="2">Add</th>
            <th colspan="2">Remove</th>
            <td><input type="text" value="${uData.message} "disabled class="feedsFields">
            </td>
            </td>
        </tr>
        <tr>
            <td>URL:</td>
            <td><input type="text" class="feedsFields" name="URL"></td>
            <td rowspan="2">Name:</td>
            <td rowspan="2">
                <select name="removeName" class="feedsFields">
                    <option selected disabled> choose item to remove</option>
                    <c:forEach var="r" items="${uData.linksList}">
                        <option value="${r.name}">${r.name}</option>
                    </c:forEach>
                    </select>

            <td><input type="submit" value="create logs"class="feedsButton" name="feedButton"></td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type="text" class="feedsFields" name="addName"></td>
            <td><input type="submit" value="${sort}" class="feedsButton" name="feedButton"></td>
        </tr>
        <tr>
            <td colspan="2"align="center">
                <input type="submit" value="add" class="feedsButton" name="feedButton">
            </td>
            <td colspan="2" align="center">
                <input type="submit" value="remove" class="feedsButton" name="feedButton">
            </td>
            <td><input type = "submit" value="Log off" name="feedButton" class="feedsButton"></td>
        </tr>
        <tr >
            <td colspan="5" height="80%">
                <IFrame  name ="news" src="news.jsp" class="frames"></IFrame>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
