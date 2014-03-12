<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Great to see you here!</h1>


<form action="Main" method="post">
    <Table>
        <tr>
            <td><h2>You must login to use our source</h2></td>
            <td> Login:
                <input type="text" name="Login"></td>
            <td>
                Password:
                <input type="text" name="Password">
            </td>
            <td>
                <input type="submit" value="Log In" name="button">
            </td>
        </tr>

        <tr><td><h2>If you're here at first you should to register</h2></td>
            <td> Login:
                <input type="text" name="LoginReg"></td>
            <td>Password:
                <input type="text" name="PasswordReg"></td>
            <td>Password again:
                <input type="text" name="PasswordReg2"></td>
            <td><input type="submit" value="Log" name="button"></td>
        </tr>
    </Table>
    <input type="text" name="Label" value = "<%if(session.getAttribute("sessionLabel") != null){out.print
     (session.getAttribute("sessionLabel"));} %> "disabled></Label>
    <input type="hidden" name="buffer" value="<%if(session.getAttribute("buffer") != null){out.print
     (session.getAttribute("buffer"));}%>"disabled>
</form>

</body>
</html>