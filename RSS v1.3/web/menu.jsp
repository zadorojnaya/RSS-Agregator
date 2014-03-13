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
    <style>  input.flat
     {border:solid 1px black;}
     A {text-decoration: none;}
       body {margin:0;}</style>
</head>
<body onLoad="parent.news.document.location = 'news.jsp'">
<form action="Login" method="post">
<%if(session.getAttribute("list")!= null) {out.print(session.getAttribute("list").toString());}
else out.print("Add URL");%>
</form>
</body>
</html>
