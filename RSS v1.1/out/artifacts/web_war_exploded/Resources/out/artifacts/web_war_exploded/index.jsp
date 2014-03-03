<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <h1>Great to see you here!</h1>
  <h2>You must login to use our source</h2>
  <form action="Main" method="post">
         Login:
        <input type="text" name="Login">
      Password:
      <input type="text" name="Password">
      <input type="submit" value="Log In" name="button">
      <h2>If you're here at first you should to register</h2>
      Login:
      <input type="text" name="LoginReg">
      Password:
      <input type="text" name="PasswordReg">
      Password again:
      <input type="text" name="PasswordReg2">
      <input type="submit" value="Log" name="button">

      <input type="text" name="Label" value = "<%if(session.getAttribute("sessionLabel") != null){out.print
     (session.getAttribute("sessionLabel"));} %> "></Label>
      <input type="hidden" name="buffer" value="<%if(session.getAttribute("buffer") != null){out.print
     (session.getAttribute("buffer"));}%>"disabled>
      </form>
  </body>
</html>