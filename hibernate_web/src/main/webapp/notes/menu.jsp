<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 14.02.2016
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form name="menuForm" action="/menuServlet" method="post">
    <input type="hidden" name="menuOption">

    <table cellpadding="4" align="center" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center" width="260"><b>NOTEBOOKS MARKET</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr><td colspan="2"><b>Entity management</b></td></tr>
        <tr>
            <td width="10"><input type="radio" name="entityMenu" value="notebook" checked></td>
            <td>Notebook model</td>
        </tr>
        <tr>
            <td width="10"><input type="radio" name="entityMenu" value="vendor"></td>
            <td>Vendor name</td>
        </tr>
        <tr>
            <td width="10"><input type="radio" name="entityMenu" value="cpu"></td>
            <td>CPU type</td>
        </tr>
        <tr>
            <td width="10"><input type="radio" name="entityMenu" value="memory"></td>
            <td>Memory type</td>
        </tr>
        <tr>
            <td colspan="2" align="left">
                <input type="button" value="Select" onclick="submitEntity()" style="width: 80px"/>
            </td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr><td colspan="2"><b>Store operations</b></td></tr>
        <tr>
            <td colspan="2"><input type="button" value="Menu" onclick="submitStore()" style="width: 80px"/></td>
        </tr>
        <tr><td colspan="2"><b>Reports</b></td></tr>
        <tr>
            <td colspan="2"><input type="button" value="Menu" onclick="submitReport()" style="width: 80px"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr><td colspan="2"><a href="../index.jsp">Log off</a></td></tr>
    </table>
</form>

<script>
    function submitEntity() {
        document.menuForm.menuOption.value = 'entity';
        document.menuForm.submit();
    }

    function submitStore() {
        document.menuForm.menuOption.value = 'store';
        document.menuForm.submit();
    }

    function submitReport() {
        document.menuForm.menuOption.value = 'reports';
        document.menuForm.submit();
    }
</script>

</body>
</html>
