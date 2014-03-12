<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="Login" method="post">
    <table align="center">
        <tr>
            <th colspan="4">
                <H1 >Tonika's RSS reader</H1>
            </th>
        </tr>
        <tr>
            <th colspan="2">Login</th>
            <th colspan="2">Register<th>
        </tr>
        <tr>
            <td> Login </td>
            <td><input type="text" name="LoginLog" tabindex="1"></td>
            <td>Login</td>
            <td><input type="text" name="LoginReg" tabindex="4"></td>
        </tr>
        <tr>
            <td rowspan="2">Password</td>
            <td rowspan="2"><input type="text" name="PassLog" tabindex="2"></td>
            <td>Password</td>
            <td><input type="text" name="PassReg"tabindex="5"></td>
        </tr>
        <tr>

            <td>Password</td>
            <td><input type="text" name="PassRegA" tabindex="6"></td>
        </tr>
        <tr>
        <tr>
            <th colspan="2"><input type="submit" value="Sign in" name="button"tabindex="3"></th>
            <th colspan="2"> <input type="submit" value="Create new account" name="button" tabindex="7"></th>
        </tr>
        <tr >
            <td colspan="4">
                <input type="text" value="<%if(session.getAttribute("sessionLabel") != null){out.print
     (session.getAttribute("sessionLabel"));} %> "disabled style="width: 100%">
            </td>
        </tr>
    </table>
</form>
</body>
</html>