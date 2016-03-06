<%@ page import="notes.domain.Notebook" %>
<%@ page import="java.util.List" %>
<%@ page import="notes.domain.Store" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="static notes.util.Utils.DATEFORMAT_COMMON" %>
<%--
  Created by IntelliJ IDEA.
  User: v.davidenko
  Date: 15.02.2016
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form name="listForm" action="/listServlet" method="post">
    <input type="hidden" name="reportMenu">
    <input type="hidden" name="action">
    <input type="hidden" name="page">
    <input type="hidden" name="portion">
    <input type="hidden" name="gtAmount">
    <table align="center" cellpadding="3">

        <tr><td align="center" colspan="3"><b>NOTEBOOK LIST</b></td></tr>
        <tr><td colspan="3">
            <% String option = (String)request.getAttribute("reportMenu"); %>
            <table border="1" cellpadding="3">

            <% if (option.equals("storePresent")) { %>
                <tr style="background-color: #d4ecff">
                    <th align="center" width="40px">Id</th>
                    <th align="center" width="200px">Model</th>
                    <th align="center" width="100px">Manuf. date</th>
                    <th align="center" width="120px">Vendor</th>
                    <th align="center" width="200px">CPU</th>
                    <th align="center" width="120px">Memory</th>
                    <th align="center" width="50px">Store</th>
                    <th align="center" width="50px">Amount</th>
                    <th align="center" width="100px">Price</th>
                </tr>
                <%
                    List<Store> storeList = (List<Store>)request.getAttribute("storeList");
                    if(storeList != null && !storeList.isEmpty()){
                        for (Store store : storeList) {
                            Notebook note = store.getNotebook();
                %>
                <tr>
                    <td align="center"><%= String.valueOf(note.getId()) %></td>
                    <td align="center"><%= note.getModel() %></td>
                    <td align="center"><%= note.getManufactureDateStr() %></td>
                    <td align="center"><%= note.getVendor().getName() %></td>
                    <td align="center"><%= note.getCpu().getModel() + " " + note.getCpu().getFrequency() %></td>
                    <td align="center"><%= note.getMemory().getVendor().getName() + " " + note.getMemory().getSize() %></td>
                    <td align="center"><%= String.valueOf(store.getId()) %></td>
                    <td align="center"><%= String.valueOf(store.getAmount()) %></td>
                    <td align="center"><%= String.valueOf(store.getPrice()) %></td>
                </tr>
            <% } } } %>

            <% if (option.equals("byCPU") || option.equals("storeAll") ||
                    option.equals("byPortion") || option.equals("gtAmount")) { %>
                <tr style="background-color: #d4ecff">
                    <th align="center" width="40px">Id</th>
                    <th align="center" width="200px">Model</th>
                    <th align="center" width="100px">Manuf. date</th>
                    <th align="center" width="120px">Vendor</th>
                    <th align="center" width="200px">CPU</th>
                    <th align="center" width="120px">Memory</th>
                </tr>
                <%
                    List<Notebook> noteList = (List<Notebook>)request.getAttribute("noteList");
                    if(noteList != null && !noteList.isEmpty()){
                        for (Notebook note : noteList) {
                %>
                <tr>
                    <td align="center"><%= String.valueOf(note.getId()) %></td>
                    <td align="center"><%= note.getModel() %></td>
                    <td align="center"><%= note.getManufactureDateStr() %></td>
                    <td align="center"><%= note.getVendor().getName() %></td>
                    <td align="center"><%= note.getCpu().getModel() + " " + note.getCpu().getFrequency() %></td>
                    <td align="center"><%= note.getMemory().getVendor().getName() + " " + note.getMemory().getSize() %></td>
                </tr>
            <% } } } %>

            <% if (option.equals("salesByDays")) { %>
                <tr style="background-color: #d4ecff">
                    <th align="center" width="100px">Sale date</th>
                    <th align="center" width="100px">Notebooks</th>
                </tr>
                <%
                    Map<Date, Integer> salesMap = (Map<Date, Integer>)request.getAttribute("salesMap");
                    if(salesMap != null && !salesMap.isEmpty()){
                        Set<Map.Entry<Date, Integer>> entries = salesMap.entrySet();
                        for(Map.Entry<Date, Integer> entry : entries) {
                %>
                <tr>
                    <td align="center"><%= DATEFORMAT_COMMON.get().format(entry.getKey()) %></td>
                    <td align="center"><%= String.valueOf(entry.getValue()) %></td>
                </tr>
            <% } } } %>
            </table>

        </td></tr>
        <tr style="background-color: #d4ecff">
            <td colspan="3">
                <% if (option.equals("byPortion")) { %>
                    <input type="button" value="Prev" onclick="submitPrevForm()" style="width: 80px"/>
                    &nbsp;&nbsp;&nbsp;
                    <input type="button" value="Next" onclick="submitNextForm()" style="width: 80px"/>
                <% } %>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="Back" onclick="submitBackForm()" style="width: 80px"/>
            </td>
        </tr>

    </table>
</form>

<script>
    document.listForm.reportMenu.value = '${reportMenu}';
    document.listForm.portion.value = '${portion}';
    document.listForm.gtAmount.value = '${gtAmount}';


    function submitPrevForm() {
        document.listForm.page.value = '-1';
        document.listForm.action.value = 'paging';
        document.listForm.submit();
    }
    function submitNextForm() {
        document.listForm.action.value = 'paging';
        document.listForm.page.value = '1';
        document.listForm.submit();
    }
    function submitBackForm() {
        document.listForm.action.value = 'back';
        document.listForm.submit();
    }
</script>

</body>
</html>
