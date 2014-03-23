<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body class="index">
  <link rel="stylesheet" type="text/css" href="styles.css"/>
  <form action="Login" method="post">
      <table align="center" class="fonts">
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
              <td><input type="text" name="LoginLog" tabindex="1" class="indexFields"></td>
              <td>Login</td>
              <td><input type="text" name="LoginReg" tabindex="4" class="indexFields"></td>
          </tr>
          <tr>
              <td rowspan="2">Password</td>
              <td rowspan="2"><input type="text" name="PassLog" tabindex="2" class="indexFields"></td>
              <td>Password</td>
              <td><input type="text" name="PassReg"tabindex="5" class="indexFields"></td>
          </tr>
          <tr>
              <td>Password</td>
              <td><input type="text" name="PassRegA" tabindex="6" class="indexFields"></td>
          </tr>
          <tr>
              <th colspan="2"><input type="submit" value="Sign in" name="button" tabindex="3"class="indexButton"></th>
              <th colspan="2"> <input type="submit" value="Create new account" name="button" tabindex="7" class="indexButton"></th>
          </tr>
          <tr >
              <td colspan="4">
                  <input type="text" value="${uData.message}" disabled class="indexFields">
              </td>
          </tr>
      </table>
  </form>
  </body>
</html>