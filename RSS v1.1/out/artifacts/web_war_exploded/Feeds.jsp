<%@ page import="Feeds.Feed" %>
<%@ page import="Feeds.CreatePages" %>
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
        <tr><br>Here you can add a new channel:
          <br> URL: <input type="text" name = "URL" ></tr>
        </p>
        Name: <input type="text" name = "name" >
        <tr><input type="submit" value = "add"name = "button"></tr>


        <tr>
          <br>  You can delete channel<br>name:<br><input type = "text" name = "delName"></tr>

        <tr><input type = "submit" value = "delete" name="button"></tr>

    <tr>
        <IFrame src="News.jsp" name="news" height="570" width = "1025" align="bottom"></IFrame></tr>
    </tr>
</Table>
</form>
</body>
</html>
