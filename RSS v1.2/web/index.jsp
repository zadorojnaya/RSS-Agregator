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
                 <td><input type="text" name="LoginLog"></td>
                 <td>Login</td>
                 <td><input type="text" name="LoginReg"></td>
             </tr>
             <tr>
                 <td rowspan="2">Password</td>
                 <td rowspan="2"><input type="text" name="PassLog"></td>
                 <td>Password</td>
                 <td><input type="text" name="PassReg"></td>
             </tr>
             <tr>

                 <td>Password</td>
                 <td><input type="text" name="PassRegA"></td>
             </tr>
             <tr>
             <tr>
                 <th colspan="2"><input type="submit" value="Sign in" name="button"></th>
                 <th colspan="2"> <input type="submit" value="Create new account" name="button"></th>
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