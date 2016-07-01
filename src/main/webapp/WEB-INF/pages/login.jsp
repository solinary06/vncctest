<%--
  Created by IntelliJ IDEA.
  User: steven.liu
  Date: 2016/4/15
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<div class="container">
  <form class="form-httpGet" method="POST" action="/vncctest/doLogin">
    <label for="userName">User Name:</label>
    <input id="userName" name="userName" type="text" class="text" size=35>
    <label for="password">User Name:</label>
    <input id="password" name="password" type="password" class="text" size=35>
    <button type="submit" value="Submit">Login</button>
  </form>
</div>
<h2>Register user</h2>
<div class="container">
  <form class="form-httpGet" method="POST" action="/vncctest/doRegister">
    <label for="registerUserName">User Name:</label>
    <input id="registerUserName" name="registerUserName" type="text" class="text" size=35>
    <label for="registerPassword">User Name:</label>
    <input id="registerPassword" name="registerPassword" type="password" class="text" size=35>
    <button type="submit" value="Submit">Login</button>
  </form>
</div>

<div class="container">
  <span id="messageArea" style="width:1000px"></span>
  <c:if test="${not empty msg}">
    <tr>
        ${msg}
    </tr>
  </c:if>
</div>
</body>
</html>
