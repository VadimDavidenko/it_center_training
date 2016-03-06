/**
 * Created by Вадим on 23.01.2016.
 */

var PhonesTable = {
    phones: [],
    createTableHTML: function() {
        var table = document.createElement('table');
        document.body.appendChild(table);
        table.id = 'telephones';
        table.border = '0';
        table.cellPadding = '6';
        table.align = 'center';
        table.bgColor = '#d4ecff';

        addTableHeader();
        addPhonesData();
    }
};

PhonesTable.phones = [
    { phoneName: 'iPhone', number: '+380 5554433', balance: '1000.00' },
    { phoneName: 'Samsung', number: '+380 1112233', balance: '500.00' },
    { phoneName: 'Lenovo', number: '+380 9998877', balance: '300.00'},
    { phoneName: 'Sony', number: '+380 2225511', balance: '600.00'}
];
PhonesTable.createTableHTML();

function addTableHeader() {
    var doc = document;
    var table = doc.getElementById('telephones');
    var tr = doc.createElement('tr');
    table.appendChild(tr);

    var colNames = ['Telephones', 'Number', 'Balance', 'Actions'];
    for (var i = 0; i < colNames.length; i++) {
        var th = doc.createElement('th');
        tr.appendChild(th);
        th.innerHTML = colNames[i];
        if (i == 3) {
            th.colSpan = '2';
            th.align = 'center';
            //th.style = 'align: center';
        }
    }
    tr = doc.createElement('tr');
    table.appendChild(tr);
    var td = doc.createElement('td');
    tr.appendChild(td);
    td.colSpan = '5';
    var hr = doc.createElement('hr');
    td.appendChild(hr);
}

function addPhonesData() {
    var doc = document;
    var table = doc.getElementById('telephones');

    for (var i = 0; i <PhonesTable.phones.length; i++) {
        var tr = doc.createElement('tr');
        table.appendChild(tr);

        var td = doc.createElement('td');
        tr.appendChild(td);
        td.innerHTML = PhonesTable.phones[i].phoneName;

        td = doc.createElement('td');
        tr.appendChild(td);
        var input = doc.createElement('input');
        td.appendChild(input);
        input.type = 'text';
        input.size = '15';
        input.disabled = true;
        input.value = PhonesTable.phones[i].number;
        input.onblur = function() {
            if(!this.isDisabled) this.disabled = true;
        };

        td = doc.createElement('td');
        tr.appendChild(td);
        input = doc.createElement('input');
        td.appendChild(input);
        input.type = 'text';
        input.size = '10';
        input.disabled = true;
        input.value = PhonesTable.phones[i].balance;
        input.onblur = function() {
            if(!this.isDisabled) this.disabled = true;
        };

        td = doc.createElement('td');
        tr.appendChild(td);
        input = doc.createElement('input');
        td.appendChild(input);
        input.type = 'button';
        input.value = 'Modify';
        input.onclick = function() { modifyPhone(this); };

        td = doc.createElement('td');
        tr.appendChild(td);
        input = doc.createElement('input');
        td.appendChild(input);
        input.type = 'button';
        input.value = 'Delete';
        input.onclick = function() { deletePhone(this); };
    }
}

function modifyPhone(elem) {
    var tr = elem.parentNode.parentNode;
    var numberField = tr.childNodes[1].firstElementChild;
    numberField.disabled = false;
    var balanceField = tr.childNodes[2].firstElementChild;
    balanceField.disabled = false;
}


function deletePhone(elem) {
    if(!confirm('Are you sure to delete this phone?')) return;
    var index = elem.parentNode.parentNode.rowIndex;
    document.getElementById('telephones').deleteRow(index);
}
