<%@ page import="notes.domain.Vendor" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 14.02.2016
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form name="reportForm" action="/reportsServlet" method="post">
   <table border="0" cellpadding="4" align="center" style="background-color: #d4ecff">
       <tr><td colspan="3" align="center"><b>REPORTS</b></td></tr>
       <tr><td colspan="3"><hr/></td></tr>
       <tr>
           <td><input type="radio" name="reportMenu" value="byPortion"></td>
           <td>Show all notebooks by portion:</td>
           <td><input type="text" name="portion" size="2" maxlength="2"/></td>
       </tr>
       <tr>
           <td><input type="radio" name="reportMenu" value="gtAmount"></td>
           <td>Show notebooks greater then amount:</td>
           <td><input type="text" name="gtAmount" size="5" maxlength="5"/></td>
       </tr>
       <tr>
           <td><input type="radio" name="reportMenu" value="byCPU"></td>
           <td>Show notebooks by CPU vendor:</td>
           <td>
               <select size="3" name="cpuVendor">
                   <option disabled>Select ..................</option>
                   <%
                       List<Vendor> vendorList = (List<Vendor>)request.getAttribute("vendorList");
                       if(vendorList != null && !vendorList.isEmpty()){
                           for (Vendor vendor : vendorList){
                   %>
                   <option value="<%=String.valueOf(vendor.getId())%>"><%=vendor.getName()%></option>
                   <% } } %>
               </select>
           </td>

       </tr>
       <tr>
           <td><input type="radio" name="reportMenu" value="storeAll" checked></td>
           <td colspan="2">Show all notebooks</td>
       </tr>
       <tr>
           <td><input type="radio" name="reportMenu" value="storePresent"></td>
           <td colspan="2">Show notebooks present on store</td>
       </tr>
       <tr>
           <td><input type="radio" name="reportMenu" value="salesByDays"></td>
           <td colspan="2">Show notebook sales per day</td>
       </tr>
       <tr><td colspan="3"><hr/></td></tr>
       <tr>
           <td colspan="3" align="center">
               <input type="button" value="Select" onclick="submitForm()" style="width: 80px"/>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <a href="../notes/menu.jsp"><input type="button" value="Back" style="width: 80px"/></a>
           </td>
       </tr>
       <tr><td colspan="3" align="center"><span id="msg"><b>${server_msg}</b></span></td></tr>
   </table>
</form>

<script>
    document.reportForm.reportMenu.value = '${reportMenu}';
    document.reportForm.portion.value = '${portion}';
    document.reportForm.gtAmount.value = '${gtAmount}';
    document.reportForm.cpuVendor.value = '${cpuVendor}';

    function submitForm() {
        var form = document.reportForm;
        if(checkFields(form)) {
            form.submit();
        }
    }

    function checkFields(form) {
        var isValid = true;
        switch (form.reportMenu.value) {
            case 'byPortion':
                if (!form.portion.value.trim() || isNaN(+form.portion.value)) isValid = false;
                break;
            case 'gtAmount':
                if (!form.gtAmount.value.trim() || isNaN(+form.gtAmount.value)) isValid = false;
                break;
            case 'byCPU':
                if (!form.cpuVendor.value.trim()) isValid = false;
        }
        if(!isValid) {
            alert("Please, fill in field with valid value!");
            return false;
        }
        return true;
    }

</script>

</body>
</html>
