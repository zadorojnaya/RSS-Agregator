<%@ page import="Feeds.Feed" %>
<%@ page import="Feeds.Menu" %>
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

<Table>
    <tr>
    <IFrame src="menu.html" height="700" align="left"></IFrame></tr>
    <tr>
        <tr><input type="text" name="Label" value = "<%if(session.getAttribute("URLCon") != null){out.print
        (session.getAttribute("URLCon"));} %> "disabled></tr>
        <tr><input type="text" value = "<%out.print(Login.DataBase.getLastLogin());%>" disabled></tr>
        <tr>Here you can add a new feed<input type="text" name = "URL"></tr>
        <tr><input type="submit"></tr>
    <tr>
        <IFrame src="News.html"height="400" width = "1000" align="bottom"></IFrame></tr>
    </tr>
</Table>
</form>
</body>
</html>
