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

<form:form modelAttribute="order" autocomplete="false" method="post" action="/order/add/save" id="orderForm">
    <form:errors path="*"/>
    <form:hidden path="id"/>

    <table align="center" border="0" cellpadding="5" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><b>Add new order</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td align="right">Client:</td>
            <td>
                <form:select path="client.id" size="4" cssStyle="width: 210px" id="clientId">
                    <form:option value="0" label="Select client" disabled="true"/>
                    <form:options items="${clients}" itemValue="id" itemLabel="fullName"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td align="right">Amount:</td>
            <td><form:input path="amount" size="10" maxlength="10"/></td>
        </tr>
        <tr>
            <td align="right">From:</td>
            <td><form:input path="addressFrom" size="30" maxlength="30"/></td>
        </tr>
        <tr>
            <td align="right">To:</td>
            <td><form:input path="addressTo" size="30" maxlength="30"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" value="Add" onclick="submitOrderForm()" style="width: 70px"/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="/dashboard"><input type="button" value="Back" style="width: 70px"/></a>
            </td>
        </tr>
        <tr><td colspan="2" align="center"><span id="msg"><i>${msg}</i></span></td></tr>
    </table>
</form:form>

<%@include file="footer.jsp"%>