<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 04.03.2016
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>TAXI service</title>
</head>
<body>

<script type="text/javascript" src="/js/script.js"></script>

<div style="background-color: #d4ecff" align="center">
  <h1>Welcome to TAXI service!</h1>
</div>
<br/>

<form:form modelAttribute="user" autocomplete="false" method="post" action="/login" id="loginForm">
    <form:errors path="*"/>

    <table align="center" border="0" cellpadding="5" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><span id="msg" style="color: red"><b>${msg}</b></span></td></tr>
        <tr>
            <td align="right">Login name:</td>
            <td><form:input path="login" size="20" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Password:</td>
            <td><form:input type="password" path="password" size="20" maxlength="20"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td colspan="2" align="center">
              <input type="button" value="Login" onclick="submitLoginForm()" style="width: 100px"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>

