<%@ page import="notes.domain.Vendor" %>
<%@ page import="java.util.List" %>
<%@ page import="notes.domain.CPU" %>
<%@ page import="notes.domain.Memory" %>
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

    <form name="notebookForm" action="/noteServlet" method="post">
      <input type="hidden" name="action">
      <table border="0" cellpadding="6" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><b>NOTEBOOK</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
          <td align="right">Id:</td>
          <td><input type="text" name="id" size="3" readonly style="background-color: lightgrey"></td>
        </tr>
        <tr>
          <td align="right">Model name:</td>
          <td><input type="text" name="model" size="25" maxlength="25"/></td>
        </tr>
        <tr>
          <td align="right">Manuf. date:</td>
          <td>
            <input type="text" name="date" hidden/>
            <input type="text" name="dd" size="2" maxlength="2"/>.
            <input type="text" name="mm" size="2" maxlength="2"/>.
            <input type="text" name="yyyy" size="4" maxlength="4"/>
          </td>
        </tr>

        <tr>
          <td align="right">Vendor name:</td>
          <td>
            <select size="3" name="vendorId">
            <option disabled>Select ........................</option>
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
          <td align="right">CPU name:</td>
          <td>
            <select size="3" name="cpuId">
              <option disabled>Select ........................</option>
              <%
                String cpuId = (String)request.getAttribute("cpuId");
                List<CPU> cpuList = (List<CPU>)request.getAttribute("cpuList");
                if(cpuList != null && !cpuList.isEmpty()){
                  for (CPU cpu : cpuList){
              %>
              <option <%=(String.valueOf(cpu.getId()).equals(cpuId)) ? "selected" : ""%>
                      value="<%=String.valueOf(cpu.getId())%>"><%=cpu.getModel()%></option>
              <% } } %>
            </select>
          </td>
        </tr>

        <tr>
          <td align="right">Memory type:</td>
          <td>
            <select size="3" name="memoryId">
              <option disabled>Select ........................</option>
              <%
                String memoryId = (String)request.getAttribute("memoryId");
                List<Memory> memoryList = (List<Memory>)request.getAttribute("memoryList");
                if(memoryList != null && !memoryList.isEmpty()){
                  for (Memory memory : memoryList){
              %>
              <option <%=(String.valueOf(memory.getId()).equals(memoryId)) ? "selected" : ""%>
                      value="<%=String.valueOf(memory.getId())%>">
                <%=memory.getVendor().getName() + " " + memory.getSize()%></option>
              <% } } %>
            </select>
          </td>
        </tr>

        <tr><td colspan="2"><hr/></td></tr>
        <tr>
          <td align="center">
            <input type="button" value="New" onclick="newEntity()" style="width: 70px"/>
          </td>
          <td>
            <input type="button" value="Edit" onclick="editEntity()" style="width: 70px"/>
            &nbsp;id:&nbsp;<input type="text" name="selectedId" size="3" maxlength="3"/>
          </td>
        </tr>
        <tr>
          <td align="center">
            <input type="button" value="Save" onclick="submitForm()" style="width: 70px"/>
          </td>
          <td>
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
  document.notebookForm.id.value = '${id}';
  document.notebookForm.selectedId.value = '${selectedId}';
  document.notebookForm.model.value = '${model}';
  if ('${date}'.length == 10) {
    document.notebookForm.dd.value = '${date}'.substring(0, 2);
    document.notebookForm.mm.value = '${date}'.substring(3, 5);
    document.notebookForm.yyyy.value = '${date}'.substring(6);
  }

  function submitForm() {
    var form = document.notebookForm;
    if(checkFields(form)) {
      form.date.value = readDate(form.dd, form.mm, form.yyyy);
      document.notebookForm.action.value = 'save';
      document.getElementById("msg").innerHTML = '';
      form.submit();
    }
  }

  function newEntity() {
    document.notebookForm.id.value = '';
    document.notebookForm.selectedId.value = '';
    document.notebookForm.model.value = '';
    document.notebookForm.dd.value = '';
    document.notebookForm.mm.value = '';
    document.notebookForm.yyyy.value = '';
    document.getElementById("msg").innerHTML = '';
  }

  function editEntity() {
    var id = document.notebookForm.selectedId.value.trim();
    if(!id || isNaN(+id)) {
      alert("Please, fill in Id with numeric value!");
    } else {
      document.notebookForm.action.value = 'find';
      document.getElementById("msg").innerHTML = '';
      document.notebookForm.submit();
    }
  }

  function checkFields(form) {
    if(!form.model.value.trim() || !form.vendorId.value.trim() ||
            !form.cpuId.value.trim() || !form.memoryId.value.trim() ||
            !form.dd.value.trim() || !form.mm.value.trim() ||
            !form.yyyy.value.trim()) {
      alert("Please, fill in all fields with valid values!");
      return false;
    }
    return true;
  }

  function readDate(dd, mm, yyyy) {
    var d = (dd.value.length == 1) ? '0' + dd.value : dd.value;
    var m = (mm.value.length == 1) ? '0' + mm.value : mm.value;
    var y = yyyy.value;
    return d + '.' + m + '.' + y;
  }
</script>

</body>
</html>
