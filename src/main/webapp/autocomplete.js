function init() {
    completeField = document.getElementById("searchId");
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
    isIE = false;
}

function showAutoCompletion() {
    var url = "AutoComplete?searchId=" + escape(searchId.value);
    var req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = function() {
        clearTable();
        if (req.readyState == 4 && req.status == 200) {
            parseMessages(req.responseXML);
        }
    };

    req.send();
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function parseMessages(responseXML) { // no matches returned
    if (responseXML == null) {
        return false;
    } else {
        var products = responseXML.getElementsByTagName("products")[0];
        if (products.childNodes.length > 0) {
            completeTable.setAttribute("bordercolor", "black");
            completeTable.setAttribute("border", "1");
            for (loop = 0; loop < products.childNodes.length; loop++) {
                var product = products.childNodes[loop];
                var productName = product.getElementsByTagName("name")[0];
                var productId = product.getElementsByTagName("id")[0];
                appendProduct(productName.childNodes[0].nodeValue, productId.childNodes[0].nodeValue);
            }
        }
    }
}

function callback() {
    clearTable();
    if (req.readyState == 4 && req.status == 200) {
        parseMessages(req.responseXML);
    }
}

function clearTable() {
    if (completeTable.getElementsByTagName("tr").length > 0) {
        completeTable.style.display = 'none';
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable.removeChild(completeTable.childNodes[loop]); }
    }
}


function appendProduct(productName,productId) {
    var row;
    var cell;
    var linkElement;
    if (isIE) {
        completeTable.style.display = 'block';
        row = completeTable.insertRow(completeTable.rows.length);
        cell = row.insertCell(0);
    } else {
        completeTable.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTable.appendChild(row);
    }
    cell.className = "popupCell";
    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "ProductList?id=" + productId);
    linkElement.appendChild(document.createTextNode(productName));
    cell.appendChild(linkElement);
}
