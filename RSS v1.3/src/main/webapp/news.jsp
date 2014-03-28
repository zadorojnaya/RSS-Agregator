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
<link rel="stylesheet" type="text/css" href="styles.css"/>

<html>
<head>
    <title></title>
</head>
<body class="index">
<form action="Login">
    <c:if test="${uData.linkIndex == -1}">
        <c:set var="list" value="${uData.allFeeds}"/>
    </c:if>
    <c:if test="${uData.linkIndex >= 0}">
        <c:set value="${uData.feedList}" var="list"/>
    </c:if>
    <%request.setAttribute("page",request.getParameter("c")); %>
    <c:set var="pages" value="${list.size()/5}"/>
    <c:if test="${list.size() == null}">
        <%request.setAttribute("pages",request.getParameter("p"));%>
    </c:if>
<table >
    <c:forEach var="feed" items="${list}" begin="${page*5}" end="${page*5+4}" varStatus="count">
        <tr>
            <td colspan="2" padding="5">
            <a href="${feed.link}" target="_blank" class="links">
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
            <td colspan="2" >
           ${feed.description}
            </td>
        </tr>
        <tr>
            <td colspan="2"><hr></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2" align="center">
    <c:forEach begin="1" end="${pages}" varStatus="s">
        <a href="news.jsp?c=${s.count-1}&p=${pages}" class="pageLinks">
             <c:set value="${pages}" scope="page" var="p"/>
            <c:out value="${s.count}"/>
        </a>
    </c:forEach>
    </td>
</tr>
</table>
</form>
</body>
</html>
