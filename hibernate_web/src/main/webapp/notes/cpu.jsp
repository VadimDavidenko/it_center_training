<%@ page import="notes.domain.Vendor" %>
<%@ page import="java.util.List" %>
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

<table align="center">
  <tr><td>
    <form name="cpuForm" action="/cpuServlet" method="post">
      <input type="hidden" name="action">
      <table border="0" cellpadding="6" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><b>CPU</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
          <td align="right">Id:</td>
          <td><input type="text" name="id" size="3" readonly style="background-color: lightgrey"></td>
        </tr>
        <tr>
          <td align="right">Model name:</td>
          <td><input type="text" name="model" size="20" maxlength="20"/></td>
        </tr>
        <tr>
          <td align="right">Vendor name:</td>
            <td>
                <select size="3" name="vendorId">
                    <option disabled>Select ..................</option>
                    <%
                        String vendorId = (String)request.getAttribute("vendorId");
                        List<Vendor> vendorList = (List<Vendor>)request.getAttribute("vendorList");
                        if(vendorList != null && !vendorList.isEmpty()){
                            for (Vendor vendor : vendorList){
                    %>
                    <option <%=(String.valueOf(vendor.getId()).equals(vendorId)) ? "selected" : ""%>
                            value="<%=String.valueOf(vendor.getId())%>"><%=vendor.getName()%></option>
                    <% } } %>
                </select>
            </td>
        </tr>
        <tr>
          <td align="right">Frequency:</td>
          <td><input type="text" name="frequency" size="10" maxlength="10"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
          <td align="center">
            <input type="button" value="New" onclick="newEntity()" style="width: 70px"/>
          </td>
          <td align="center">
            <input type="button" value="Edit" onclick="editEntity()" style="width: 70px"/>
            &nbsp;id:&nbsp;<input type="text" name="selectedId" size="3" maxlength="3"/>
          </td>
        </tr>
        <tr>
          <td align="center">
            <input type="button" value="Save" onclick="submitForm()" style="width: 70px"/>
          </td>
          <td align="left">
            <a href="notes/menu.jsp"><input type="button" value="Back" style="width: 70px"/></a>
          </td>
        </tr>
      </table>
    </form>
  </td></tr>
  <tr><td colspan="2" align="center">
      <span id="msg"><i>${server_msg}</i></span>
  </td></tr>
</table>

<script>
  document.cpuForm.id.value = '${id}';
  document.cpuForm.selectedId.value = '${selectedId}';
  document.cpuForm.model.value = '${model}';
  document.cpuForm.frequency.value = '${frequency}';

  function submitForm() {
    var form = document.cpuForm;
    if(checkFields(form)) {
        document.cpuForm.action.value = 'save';
        document.getElementById("msg").innerHTML = '';
        form.submit();
    }
  }

  function newEntity() {
    document.cpuForm.id.value = '';
    document.cpuForm.selectedId.value = '';
    document.cpuForm.model.value = '';
    document.cpuForm.frequency.value = '';
    document.getElementById("msg").innerHTML = '';
  }

  function editEntity() {
    var id = document.cpuForm.selectedId.value.trim();
    if(!id || isNaN(+id)) {
      alert("Please, fill in Id with numeric value!");
    } else {
        document.cpuForm.action.value = 'find';
        document.getElementById("msg").innerHTML = '';
        document.cpuForm.submit();
    }
  }

  function checkFields(form) {
    if(!form.model.value.trim() || !form.vendorId.value.trim() || !form.frequency.value.trim()) {
      alert("Please, fill in all fields with valid values!");
      return false;
    }
    return true;
  }
</script>

</body>
</html>
