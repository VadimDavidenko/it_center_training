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

<form:form modelAttribute="client" autocomplete="false" method="post" action="/registerClient" id="clientForm">
    <form:errors path="*"/>
    <form:hidden path="id"/>

    <table align="center" border="0" cellpadding="5" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><b>Client registration</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td align="right">First name:</td>
            <td><form:input path="name" size="20" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Last name:</td>
            <td><form:input path="surname" size="20" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Phone #:</td>
            <td><form:input path="phone" size="15" maxlength="15"/></td>
        </tr>
        <tr>
            <td align="right">Address:</td>
            <td><form:input path="address" size="20" maxlength="50"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" value="Save" onclick="submitClientForm()" style="width: 70px"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="/dashboard"><input type="button" value="Back" style="width: 70px"/></a>
            </td>
        </tr>
        <tr><td colspan="2" align="center"><span id="msg"><i>${msg}</i></span></td></tr>
    </table>
</form:form>

<%@include file="footer.jsp"%>
