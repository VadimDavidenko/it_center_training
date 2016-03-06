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

<table align="center" border="0" cellpadding="5" style="background-color: #d4ecff" width="500">
    <tr>
        <td><a href="/user/add">User registration</a></td>
    </tr>
    <tr><td><hr/></td></tr>
    <tr>
        <td><a href="/client/add">Client registration</a></td>
    </tr>
    <tr>
        <td><a href="/clients/ByPortion">Show clients by portion</a></td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="onShowClientsByGtSumClick()">Show clients ordered greater of sum:</a>
            &nbsp;&nbsp;&nbsp;&nbsp;<input id="sum" size="5"/>
        </td>
    </tr>
    <tr>
        <td><a href="/clients/ByLastMonth">Show clients ordered in last month</a></td>
    </tr>
    <tr><td><hr/></td></tr>
    <tr>
        <td><a href="/order/add">Add new order</a></td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="onOrderEditClick()">Modify order</a>
            &nbsp;&nbsp;&nbsp;&nbsp;id:&nbsp;<input id="id" size="5"/>
            &nbsp;&nbsp;&nbsp;&nbsp;<span id="msg" style="color: red">${msg}</span>
        </td>
    </tr>
    <tr>
        <td><a href="/orders/ByPortion">Show orders by portion</a></td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="onShowOrdersOnSumClick()">Show orders on sum</a>
            &nbsp;&nbsp;&nbsp;&nbsp;from:&nbsp;<input id="sumFrom" size="5"/>
            &nbsp;&nbsp;to:&nbsp;<input id="sumTo" size="5"/>
        </td>
    </tr>
    <tr><td><hr/></td></tr>
    <tr><td><a href="/">Logout</a></td></tr>
</table>

<%@include file="footer.jsp"%>

