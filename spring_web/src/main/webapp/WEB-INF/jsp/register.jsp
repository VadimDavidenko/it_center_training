<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 28.02.2016
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="header.jsp"%>

<form:form modelAttribute="user" autocomplete="false" method="post" action="/register" id="userForm">
    <form:errors path="*"/>
    <form:hidden path="id"/>

    <table align="center" border="0" cellpadding="5" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><b>User registration</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td align="right">ID Number:</td>
            <td><form:input path="idNumber" size="20" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Login name:</td>
            <td><form:input path="login" size="20" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Password:</td>
            <td><form:input type="password" path="password" size="20" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Confirm:</td>
            <td><input type="password" name="confirmPassword" size="20" maxlength="20"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" value="Save" onclick="submitUserForm()" style="width: 70px"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="/dashboard"><input type="button" value="Back" style="width: 70px"/></a>
            </td>
        </tr>
        <tr><td colspan="2" align="center"><span id="msg"><i>${msg}</i></span></td></tr>
    </table>
</form:form>

<%@include file="footer.jsp"%>
