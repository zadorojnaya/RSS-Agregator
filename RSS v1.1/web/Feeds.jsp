<%@ page import="Feeds.Feed" %>
<%@ page import="Feeds.Menu" %>
<%@ page import="Feeds.MainPageServlet" %>
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
<form action="MainPageServlet" method="get">

<Table>
    <tr>
    <IFrame src="Menu.jsp" height="685" align="left"></IFrame></tr>
    <tr>
        <tr><input type="text" name="Label" value = "<%if(session.getAttribute("URLCon") != null){out.print
        (session.getAttribute("URLCon"));} %> "disabled></tr>
        <tr><input type="text" value = "<%out.print(MainPageServlet.getLastLogin());%>" disabled></tr>
        <tr>Here you can add a new feed:<p>
           URL: <input type="text" name = "URL" ></tr>
        </p>
        Name: <input type="text" name = "name" >
        <tr><input type="submit"></tr>
    <tr>
        <IFrame src="News.html" name="news" height="570" width = "1025" align="bottom"></IFrame></tr>
    </tr>
</Table>
</form>
</body>
</html>
