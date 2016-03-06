<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 14.02.2016
  Time: 21:26
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
    <form name="vendorForm" action="/vendorServlet" method="post">
      <input type="hidden" name="action">
      <table border="0" cellpadding="6" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><b>VENDOR</b></td></tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
          <td align="right">Id:</td>
          <td><input type="text" name="id" size="3" readonly style="background-color: lightgrey"></td>
        </tr>
        <tr>
          <td align="right">Vendor name:</td>
          <td><input type="text" name="vendorName" size="20" maxlength="20"/></td>
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
  document.vendorForm.id.value = '${id}';
  document.vendorForm.selectedId.value = '${selectedId}';
  document.vendorForm.vendorName.value = '${vendorName}';

  function submitForm() {
    var form = document.vendorForm;
    if(checkFields(form)) {
      document.vendorForm.action.value = 'save';
      document.getElementById("msg").innerHTML = '';
      form.submit();
    }
  }

  function newEntity() {
    document.vendorForm.id.value = '';
    document.vendorForm.vendorName.value = '';
    document.vendorForm.selectedId.value = '';
    document.getElementById("msg").innerHTML = '';
  }

  function editEntity() {
    var id = document.vendorForm.selectedId.value.trim();
    if(!id || isNaN(+id)) {
      alert("Please, fill in Id with numeric value!");
    } else {
      document.vendorForm.action.value = 'find';
      document.getElementById("msg").innerHTML = '';
      document.vendorForm.submit();
    }
  }

  function checkFields(form) {
    if(!form.vendorName.value.trim()) {
      alert("Please, fill in fields with valid values!");
      return false;
    }
    return true;
  }
</script>

</body>
</html>
