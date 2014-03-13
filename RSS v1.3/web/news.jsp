<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 12.03.14
  Time: 2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="menu.jsp">
    <%if(session.getAttribute("allNews")!= null) {out.print(session.getAttribute("allNews").toString());}
    else out.print("here will be your news!");%>

</form>
</body>
</html>
