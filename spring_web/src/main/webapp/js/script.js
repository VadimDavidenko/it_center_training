/**
 * Created by v.davidenko on 02.03.2016.
 */

//////////////////////////////////////////////////////////////////
// Dashboard

function onOrderEditClick() {
    var id = document.getElementById('id').value;
    if (!id.trim() || isNaN(+id)) {
        alert("Please, fill in Id field with valid value!");
        return;
    }
    document.getElementById("msg").innerHTML = '';
    document.location='/order/edit/' + id;
}

function onShowClientsByGtSumClick() {
    var sum = document.getElementById('sum').value;
    if (!sum.trim() || isNaN(+sum)) {
        alert("Please, fill in Sum field with valid value!");
        return;
    }
    document.location='/clients/onSum/?sum=' + sum;
}

function onShowOrdersOnSumClick() {
    var sumFrom = document.getElementById('sumFrom').value;
    if (!sumFrom.trim() || isNaN(+sumFrom)) {
        alert("Please, fill in From field with valid value!");
        return;
    }
    var sumTo = document.getElementById('sumTo').value;
    if (!sumTo.trim() || isNaN(+sumTo)) {
        alert("Please, fill in To field with valid value!");
        return;
    }
    document.location='/orders/onSum/?sumFrom=' + sumFrom + '&sumTo=' + sumTo;
}

//////////////////////////////////////////////////////////////////
// Order form

function submitOrderForm() {
    var form = document.getElementById("orderForm");
    if(checkOrderFields(form)) {
        document.getElementById("msg").innerHTML = '';
        form.submit();
    }
}

function checkOrderFields(form) {
    if(!form.amount.value.trim() || isNaN(+form.amount.value) ||
        !form.addressFrom.value.trim() || !form.addressTo.value.trim()) {
        alert("Please, fill in all fields with valid values!");
        return false;
    }
    var clientId = document.getElementById("clientId");
    if(!clientId.value) {
        alert("Please, select a client!");
        return false;
    }
    return true;
}

//////////////////////////////////////////////////////////////////
// User/Login form

function submitUserForm() {
    var form = document.getElementById("userForm");
    var result = checkUserFields();
    if(!result) {
        document.getElementById("msg").innerHTML = '';
        form.submit();
    } else {
        alert(result);
    }
}

function checkUserFields() {
    var form = document.getElementById("userForm");
    var err = '';
    var login = form.login.value;
    var number = form.idNumber.value;
    var password = form.password.value;
    var confirmPassword = form.confirmPassword.value;

    if(login.length < 4) err += 'Login should be 4 symbols at least!\n';
    if(login.search(/\\s/) != -1) err += 'Login should not contain spaces!\n';

    if(number.length != 10) err += 'ID Number should be 10 digits length!\n';
    if(isNaN(+number)) err += 'ID Number should contain digits only!\n';

    if(password.length < 8) err += 'Password should be 8 symbols at least!\n';
    if(password.search(/\\s/) != -1) err += 'Password should not contain spaces!\n';
    if(password.search(/[A-Z]/) == -1 || password.search(/[a-z]/) == -1 || password.search(/[0-9]/) == -1)
        err += 'Password should contain lower, upper case letters and digits!\n';
    if(confirmPassword != password) err += 'Confirmed password does not match with password!\n';

    return err;
}

function submitLoginForm() {
    var form = document.getElementById("loginForm");
    if(!form.login.value.trim() || !form.password.value.trim()) {
        alert("Please, fill in all fields!");
        return;
    }
    document.getElementById("msg").innerHTML = '';
    form.submit();
}

//////////////////////////////////////////////////////////////////
// Client form

function submitClientForm() {
    var form = document.getElementById("clientForm");
    if(checkClientFields(form)) {
        document.getElementById("msg").innerHTML = '';
        form.submit();
    }
}

function checkClientFields(form) {
    if(!form.name.value.trim() || !form.surname.value.trim() ||
        !form.phone.value.trim()) {
        alert("Please, fill in all fields with valid values!");
        return false;
    }
    return true;
}


