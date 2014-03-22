<%@ page import="ua.tonya.rss.data.Feeds" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 12.03.14
  Time: 2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="Login">
<table>
    <c:forEach var="feed" items="${feeds}"  varStatus="count">
        <tr>
            <td colspan="2">
                <c:if test="${uData.connection}">
            <a href="${feed.link}"></c:if>
                <b><H2>
                    ${feed.title}
                </b></H2>
            </a>
            </td>
        </tr>
        <tr>
            <td>
            <i>
                ${feed.author}
            </i>
            </td>
            <td>
                ${feed.publishDate}
                <br>
            </td>
        </tr>
        <tr><td></td></tr>
        <tr>
            <td colspan="2">
           ${feed.description}
            </td>
        </tr>
        <tr><td colspan="2"><hr></td></tr>
    </c:forEach>
</table>
</form>
</body>
</html>
