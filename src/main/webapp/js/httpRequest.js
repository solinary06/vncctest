/**
 * Created by steven.liu on 2015/12/14.
 */
function doClick() {
    var server = document.getElementById("clickServiceSelect").value;
    var api = document.getElementById("apiSelect").value;
    var offer = document.getElementById("offerId").value;
    var aff = document.getElementById("affId").value;
    var app = document.getElementById("appId").value;
    var type = document.getElementById("apiTypeSelect").value;
    var headerJson = "";
    var params = "";

    var headerKeys = document.getElementsByName("header_key_click");
    var headerValues = document.getElementsByName("header_value_click");
    if (headerKeys.length > 0) {

        headerJson += "{";

        for (var i = 0; i < headerKeys.length; i++) {
            var key = headerKeys[i].value;
            var value = headerValues[i].value;

            headerJson += "\"" + key + "\":\"" + value + "\","
        }

        headerJson = headerJson.substring(0, headerJson.length - 1) + "}";
    }

    var urlParam = document.getElementsByName("url_param_click");
    if (urlParam.length > 0) {

        for (var i = 0; i < urlParam.length; i++) {
            var param = urlParam[i].value;

            params += "&" + param;
        }
    }

    $.ajax({
        type: "post",
        data: {
            "clickServiceSelect": server,
            "apiSelect": api,
            "offerId": offer,
            "affId": aff,
            "appId": app,
            "type": type,
            "header": headerJson,
            "params": params
        },
        url: "/httpClick",
        dataType: "json",
        success: function (response) {

            if (response.result == "success") {
                if (api == "click") {
                    var pattern = /[0-9a-zA-Z]{9}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{47}/
                    var transactionId = pattern.exec(response.data);
                    document.getElementById("transactionId").value = transactionId;
                    document.getElementById("messageKey").value = transactionId;
                    document.getElementById("hbaseTransId").value = transactionId;
                }
            }
            document.getElementById('messageArea').innerHTML = "";
            var td = document.createElement('td');
            td.setAttribute("style", "word-break:break-all");
            td.innerHTML = response.data;
            document.getElementById('messageArea').appendChild(td);
        }
    });

}

function doConversion() {
    var server = document.getElementById("convServiceSelect").value;
    var transactionId = document.getElementById("transactionId").value;
    var headerJson = "";
    var params = "";

    var headerKeys = document.getElementsByName("header_key_conversion");
    var headerValues = document.getElementsByName("header_value_conversion");
    if (headerKeys.length > 0) {

        headerJson += "{";

        for (var i = 0; i < headerKeys.length; i++) {
            var key = headerKeys[i].value;
            var value = headerValues[i].value;

            headerJson += "\"" + key + "\":\"" + value + "\","
        }

        headerJson = headerJson.substring(0, headerJson.length - 1) + "}";
    }

    var urlParam = document.getElementsByName("url_param_conversion");
    if (urlParam.length > 0) {

        for (var i = 0; i < urlParam.length; i++) {
            var param = urlParam[i].value;

            params += "&" + param;
        }
    }

    $.ajax({
        type: "post",
        data: {
            "convServiceSelect": server,
            "transactionId": transactionId,
            "header": headerJson,
            "params": params
        },
        url: "/httpConv",
        dataType: "text",
        success: function (message) {
            document.getElementById('messageArea').innerHTML = "";
            var td = document.createElement('td');
            td.setAttribute("style", "word-break:break-all");
            td.innerHTML = message;
            document.getElementById('messageArea').appendChild(td);
        }
    });

}

function queryMq() {
    var transactionId = document.getElementById("messageKey").value;
    var tag = $("#environmentButton input:radio:checked").val();
    $.ajax({
        type: "post",
        data: {
            "messageKey": transactionId,
            "messageTagSelect": tag
        },
        url: "/queryMessage",
        dataType: "text",
        success: function (message) {
            document.getElementById('messageArea').innerHTML = "";
            var td = document.createElement('td');
            td.setAttribute("style", "word-break:break-all");
            td.innerHTML = message;
            document.getElementById('messageArea').appendChild(td);
        }
    });

}

function queryHbase() {
    var transactionId = document.getElementById("hbaseTransId").value;
    $.ajax({
        type: "post",
        data: {
            "hbaseTransId": transactionId
        },
        url: "/queryTransIdInHbase",
        dataType: "text",
        success: function (message) {
            document.getElementById('messageArea').innerHTML = "";
            var td = document.createElement('td');
            td.setAttribute("style","word-break:break-all")
            td.innerHTML = message;
            document.getElementById('messageArea').appendChild(td);
        }
    });
}

function selectEnvironment() {
    var value = $("#environmentButton input:radio:checked").val();
    switch (value) {
        case "qa":
            document.getElementById('clickServiceSelect').value = "http://172.30.10.174:8080";
            document.getElementById('convServiceSelect').value = "http://172.30.10.174:8080";
            document.getElementById('messageTagSelect').value = "QA";
            break;
        case "dev":
            document.getElementById('clickServiceSelect').value = "http://172.30.10.146:8080";
            document.getElementById('convServiceSelect').value = "http://172.30.10.146:8080";
            document.getElementById('messageTagSelect').value = "DEV";
            break;
        case "ali":
            document.getElementById('clickServiceSelect').value = "http://10.7.0.8:9076";
            document.getElementById('convServiceSelect').value = "http://10.7.0.8:9076";
            document.getElementById('messageTagSelect').value = "ALI";
            break;
        case "prod":
            document.getElementById('clickServiceSelect').value = "http://172.30.10.174:8080";
            document.getElementById('convServiceSelect').value = "http://172.30.10.174:8080";
            document.getElementById('messageTagSelect').value = "QA";
            break;

    }

}

function addHeader(tag) {
    var header = document.createElement("div");
    header.setAttribute("class", "form-inline");
    header.setAttribute("name", "header");

    var paramLabel = document.createElement("label");
    paramLabel.innerHTML = "Header Parameter:";

    var paramInput = document.createElement("input");
    paramInput.setAttribute("name", "header_key_" + tag);
    paramInput.setAttribute("class", "form-control");
    paramInput.setAttribute("style", "width:200px;margin:2px 10px 2px 10px");

    var valueLabel = document.createElement("label");
    valueLabel.innerHTML = "Value:";

    var valueInput = document.createElement("input");
    valueInput.setAttribute("name", "header_value_" + tag);
    valueInput.setAttribute("class", "form-control");
    valueInput.setAttribute("style", "width:200px;margin:2px 10px 2px 10px");

    var deleteButton = document.createElement("span");
    deleteButton.setAttribute("style", "width:22px;border-width:0px;font-family:webdings;");
    deleteButton.innerHTML = "r";
    deleteButton.setAttribute("onclick", "clean(this)");

    header.appendChild(paramLabel);
    header.appendChild(paramInput);
    header.appendChild(valueLabel);
    header.appendChild(valueInput);
    header.appendChild(deleteButton);
    document.getElementById("header_" + tag).appendChild(header);

}

function addParam(tag) {
    var header = document.createElement("div");
    header.setAttribute("class", "form-inline");
    header.setAttribute("name", "url_param");

    var paramLabel = document.createElement("label");
    paramLabel.innerHTML = "Url Parameter:";

    var paramInput = document.createElement("input");
    paramInput.setAttribute("name", "url_param_" + tag);
    paramInput.setAttribute("class", "form-control");
    paramInput.setAttribute("style", "width:490px;margin:2px 10px 2px 10px");

    var deleteButton = document.createElement("span");
    deleteButton.setAttribute("style", "width:22px;border-width:0px;font-family:webdings;");
    deleteButton.innerHTML = "r";
    deleteButton.setAttribute("onclick", "clean(this)");

    header.appendChild(paramLabel);
    header.appendChild(paramInput);
    header.appendChild(deleteButton);
    document.getElementById("param_" + tag).appendChild(header);
}

function clean(ob) {
    ob.parentNode.parentNode.removeChild(ob.parentNode);
}

function doRegulation() {
    var server = document.getElementById("bsServer").value;
    var type = document.getElementById("regulationTypeSelect").value;
    var time = document.getElementById("timeStamp").value;

    $.ajax({
        type: "post",
        data: {
            "server": server,
            "type": type,
            "time": time
        },
        url: "/doRegulation",
        dataType: "text",
        success: function (message) {
            document.getElementById('messageArea').innerHTML = message;
        }
    });
}

