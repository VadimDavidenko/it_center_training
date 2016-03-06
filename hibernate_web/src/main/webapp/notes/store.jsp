<%@ page import="notes.domain.Store" %>
<%@ page import="java.util.List" %>
<%@ page import="notes.domain.Notebook" %>
<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 17.02.2016
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form name="storeForm" action="/storeServlet" method="post">
  <table border="0" cellpadding="3" align="center" style="background-color: #d4ecff">
    <tr><td colspan="8" align="center"><b>STORE</b></td></tr>
    <tr><td colspan="8"><hr/></td></tr>
    <tr>
      <td><input type="radio" name="storeMenu" value="receive" checked></td>
      <td>Receive notebooks to store</td>
      <td align="right">Note id:</td>
      <td>
        <select size="2" name="notebookId">
          <option disabled>Select</option>
          <%
            List<Notebook> noteList = (List<Notebook>)request.getAttribute("notebookList");
            String noteId = (String)request.getAttribute("notebookId");
            if(noteList != null && !noteList.isEmpty()){
              for (Notebook note : noteList){
          %>
          <option <%=(String.valueOf(note.getId()).equals(noteId)) ? "selected" : ""%>
                  value="<%=String.valueOf(note.getId())%>"><%=String.valueOf(note.getId())%></option>
          <% } } %>
        </select>
      </td>
      <td align="right">Amount:</td>
      <td><input type="text" name="amountReceive" size="5" maxlength="5"/></td>
      <td align="right">Price:</td>
      <td><input type="text" name="priceReceive"  size="7" maxlength="7"/></td>
    </tr>
    <tr>
      <td><input type="radio" name="storeMenu" value="remove"></td>
      <td>Remove notebooks from store</td>
      <td align="right">Store id:</td>
      <td>
          <select size="2" name="storeIdRemove">
              <option disabled>Select</option>
              <%
                  String storeId = (String)request.getAttribute("storeIdRemove");
                  List<Store> storeList = (List<Store>)request.getAttribute("storeList");
                  if(storeList != null && !storeList.isEmpty()){
                      for (Store store : storeList){
              %>
              <option <%=(String.valueOf(store.getId()).equals(storeId)) ? "selected" : ""%>
                      value="<%=String.valueOf(store.getId())%>"><%=String.valueOf(store.getId())%></option>
              <% } } %>
          </select>
      </td>
      <td align="right">Amount:</td>
      <td><input type="text" name="amountRemove" size="5" maxlength="5"/></td>
      <td colspan="2"></td>
    </tr>
    <tr>
      <td><input type="radio" name="storeMenu" value="sale"></td>
      <td>Sale store of notebooks</td>
      <td align="right">Store id:</td>
      <td>
        <select size="2" name="storeIdSale">
          <option disabled>Select</option>
          <%
            storeId = (String)request.getAttribute("storeIdSale");
            if(storeList != null && !storeList.isEmpty()){
              for (Store store : storeList){
          %>
          <option <%=(String.valueOf(store.getId()).equals(storeId)) ? "selected" : ""%>
                  value="<%=String.valueOf(store.getId())%>"><%=String.valueOf(store.getId())%></option>
          <% } } %>
        </select>
      </td>
      <td align="right">Amount:</td>
      <td><input type="text" name="amountSale" size="5" maxlength="5"/></td>
      <td colspan="2"></td>
    </tr>
    <tr><td colspan="8"><hr/></td></tr>
    <tr>
      <td colspan="8" align="center">
        <input type="button" value="Select" onclick="submitForm()" style="width: 80px"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="notes/menu.jsp"><input type="button" value="Back" style="width: 80px"/></a>
      </td>
    </tr>
    <tr><td colspan="8" align="center"><span id="msg"><i>${server_msg}</i></span></td></tr>
  </table>
</form>

<script>
  document.storeForm.storeMenu.value = '${storeMenu}';
  document.storeForm.amountReceive.value = '${amountReceive}';
  document.storeForm.amountRemove.value = '${amountRemove}';
  document.storeForm.priceReceive.value = '${priceReceive}';
  document.storeForm.amountSale.value = '${amountSale}';

  function submitForm() {
    var form = document.storeForm;
    if(checkFields(form)) {
      document.getElementById("msg").innerHTML = '';
      form.submit();
    }
  }

  function checkFields(form) {
    var isValid = true;
    switch (form.storeMenu.value) {
      case 'receive':
        if (!form.amountReceive.value.trim() || isNaN(+form.amountReceive.value) ||
                !form.priceReceive.value.trim() || isNaN(+form.priceReceive.value))
          isValid = false;
        break;
      case 'remove':
        if (!form.amountRemove.value.trim() || isNaN(+form.amountRemove.value))
          isValid = false;
        break;
      case 'sale':
        if (!form.amountSale.value.trim() || isNaN(+form.amountSale.value))
          isValid = false;
    }
    if(!isValid) {
      alert("Please, fill in fields with valid values!");
      return false;
    }
    return true;
  }
</script>

</body>
</html>
