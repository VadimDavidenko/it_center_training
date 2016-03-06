<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 28.02.2016
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp"%>

<table align="center" cellpadding="3">
    <tr><td align="center"><b>CLIENTS LIST</b></td></tr>
    <tr><td>
        <table border="1" cellpadding="3">
            <tr style="background-color: #d4ecff">
                <th align="center" width="40px">Id</th>
                <th align="center" width="150px">Name</th>
                <th align="center" width="150px">Surname</th>
                <th align="center" width="120px">Phone</th>
                <th align="center" width="200px">Address</th>
                <th align="center" width="110px">Last ordered</th>
                <th align="center" width="110px">Sum of orders</th>
            </tr>
            <c:forEach var="c" items="${clientList}">
                <tr>
                    <td align="center">${c.id}</td>
                    <td align="center">${c.name}</td>
                    <td align="center">${c.surname}</td>
                    <td align="center">${c.phone}</td>
                    <td align="center">${c.address}</td>
                    <td align="center">${c.lastOrderDateStr}</td>
                    <td align="center">${c.amount}</td>
                </tr>
            </c:forEach>
        </table>
    </td></tr>

    <tr style="background-color: #d4ecff">
        <td>
            <input type="button" id="prev" value="Prev" onclick="document.location='/clients/reportByPortion/-1'" style="width: 80px"/>
            &nbsp;&nbsp;&nbsp;
            <input type="button" id="next" value="Next" onclick="document.location='/clients/reportByPortion/1'" style="width: 80px"/>

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="Back" onclick="document.location='/clients/reportBack'" style="width: 80px"/>
        </td>
    </tr>
</table>

<script>
    document.getElementById("prev").hidden = !${paging};
    document.getElementById("next").hidden = !${paging};
</script>

<%@include file="footer.jsp"%>
