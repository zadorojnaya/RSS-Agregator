<%--
  Created by IntelliJ IDEA.
  User: Naya
  Date: 06.03.14
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="Login" method="post">
<table width="100%" height="100%">
    <tr>
        <td rowspan="5" width="20%">
            <IFrame src="<%out.print(session.getAttribute("Login"));%>" style="height: 100%; width: 100%;"
                    scrolling="yes"></IFrame>
        </td>
        <th colspan="2">Add</th>
        <th colspan="2">Remove</th>
        <td><input type="text" value="<%if(session.getAttribute("URLCon") != null){out.print
                           (session.getAttribute("URLCon"));} %> "disabled style="width: 100%">
                </td>
        </td>
    </tr>
    <tr>
        <td>URL:</td>
        <td><input type="text" style="width: 100%" name="URL"></td>
        <td rowspan="2">Name:</td>
        <td rowspan="2"><input type="text" style="width: 100%" name="removeName"></td>
        <td><input type="submit" value="create logs" style="height: 100%;width: 100%;" name="feedButton"></td>
    </tr>
    <tr>
        <td>Name:</td>
        <td><input type="text" style="width: 100%" name="addName"></td>
        <td><input type="submit" value="Sort by date: new is first" style="height: 100%;width: 100%;" name="feedButton"></td>
    </tr>
    <tr>
        <td colspan="2"align="center">
            <input type="submit" value="add" style="height: 100%;width: 100%;" name="feedButton">
        </td>
        <td colspan="2" align="center">
            <input type="submit" value="remove" style="height: 100%;width: 100%;" name="feedButton">
        </td>
        <td><input type="submit" value="Sort by date: old is first" style="height: 100%;width: 100%;" name="feedButton"></td>
    </tr>
    <tr >
        <td colspan="5" height="80%">
         <IFrame  name ="news" src="<%out.print(session.getAttribute("News"));%>" style="height: 100%; width:100%;"></IFrame>
        </td>
    </tr>

</table>
</form>
</body>
</html>
