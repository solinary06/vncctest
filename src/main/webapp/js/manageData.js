/**
 * Created by steven.liu on 2016/6/22.
 */
var baseOfferJson = "{" +
    "    \"offerId\": 100011," +
    "    \"allianceId\": 2," +
    "    \"affAllianceIdLimit\": [2]," +
    "    \"advertiserId\": 100133," +
    "    \"adManagerId\": 90010633," +
    "    \"status\": \"active\"," +
    "    \"offerUrl\": \"http://www.baidu.com?clickid={transaction_id}\"," +
    "    \"revenue\": 3," +
    "    \"payout\": 1," +
    "    \"isDynamicRevenue\": 0," +
    "    \"payoutPercentage\": 10," +
    "    \"tuningPercentage\": 0," +
    "    \"redirectOfferUrl\": \"\"," +
    "    \"hasCaps\": 0," +
    "    \"dailyConvCap\": -1," +
    "    \"monthlyConvCap\": -1," +
    "    \"dailyPayoutCap\": 0," +
    "    \"monthlyPayoutCap\": 0," +
    "    \"dailyRevenueCap\": 50," +
    "    \"monthlyRevenueCap\": 0," +
    "    \"capRedirectOfferId\": 0," +
    "    \"isImportOffer\": 0," +
    "    \"regionLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"affLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"appLimit\": {" +
    "        \"allow\": []" +
    "    }," +
    "    \"osLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"carrierLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"ispLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"platformLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"deviceOsLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"deviceModelLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"ipLimit\": {" +
    "        \"deny\": []" +
    "    }," +
    "    \"expiration_date\": \"2050-10-21\"," +
    "    \"currency\": \"USD\"," +
    "    \"pixel\": []" +
    "}";

var baseAffiliateJson = "{" +
    "    \"id\": 900011," +
    "    \"status\": \"active\"," +
    "    \"affManagerId\": 90010966," +
    "    \"pixel\": []," +
    "    \"allianceId\": 2," +
    "    \"apiMap\": {" +
    "        \"affiliate\": {" +
    "            \"payout\": 100," +
    "            \"access\": \"true\"" +
    "        }," +
    "        \"sdk\": {" +
    "            \"payout\": 100," +
    "            \"access\": \"false\"" +
    "        }," +
    "        \"realtime_api\": {" +
    "            \"payout\": 100," +
    "            \"access\": \"false\"" +
    "        }," +
    "        \"offline_api\": {" +
    "            \"payout\": 100," +
    "            \"access\": \"true\"" +
    "        }" +
    "    }" +
    "}";

var baseAppJson = "{" +
    "    \"appId\": 1011," +
    "    \"publisherId\": 900011," +
    "    \"status\": \"active\"" +
    "}";

var basePersonalizedOffer = "{" +
    "    \"offerId\": 100011," +
    "    \"affId\": 900011," +
    "    \"revenue\": -1," +
    "    \"payout\": -1," +
    "    \"dailyConvCap\": -1," +
    "    \"monthlyConvCap\": -1," +
    "    \"dailyPayoutCap\": -1," +
    "    \"monthlyPayoutCap\": -1," +
    "    \"dailyRevenueCap\": -1," +
    "    \"monthlyRevenueCap\": -1," +
    "    \"capRedirectOfferId\": 0," +
    "    \"pixel\": []" +
    "}";

var baseAppOffer = "{" +
    "    \"offerId\": 100011," +
    "    \"appId\": 1011," +
    "    \"revenue\": -1," +
    "    \"payout\": -1," +
    "    \"dailyConvCap\": -1," +
    "    \"monthlyConvCap\": -1," +
    "    \"dailyPayoutCap\": -1," +
    "    \"monthlyPayoutCap\": -1," +
    "    \"dailyRevenueCap\": -1," +
    "    \"monthlyRevenueCap\": -1" +
    "}";

var tempData = "";

function changeDataType(button, area, tag) {
    var dataType = $("#" + button + " input:radio:checked").val();
    document.getElementById(area).innerHTML = "";

    document.getElementById("dataArea").innerHTML = "";
    document.getElementById("messageArea").innerHTML = "";
    tempData = "";

    switch (dataType) {
        case "offer":
            addInputField(["Offer id"], area, tag);
            break;
        case "affiliate":
            addInputField(["Affiliate id"], area, tag);
            break;
        case "app":
            addInputField(["App id", "Publisher id"], area, tag);
            break;
        case "personalizedOffer":
            addInputField(["Offer id", "Affiliate id"], area, tag);
            break
        case "appOffer":
            addInputField(["Offer id", "App id"], area, tag);
            break;
    }
}


function addInputField(fields, area, tag) {
    for (var i = 0; i < fields.length; i++) {

        var paramLabel = document.createElement("label");
        paramLabel.innerHTML = fields[i];

        var paramInput = document.createElement("input");
        paramInput.setAttribute("name", "dataInput_" + tag);
        paramInput.setAttribute("class", "form-control");
        paramInput.setAttribute("style", "width:200px;margin:2px 10px 2px 10px");

        document.getElementById(area).appendChild(paramLabel);
        document.getElementById(area).appendChild(paramInput);
    }

    var additionalParameter = document.createElement("div");
    if(tag == "create"){
        additionalParameter.setAttribute("id", "additionalParameter");
    }else if(tag == "update"){
        additionalParameter.setAttribute("id", "updateParameter");
    }
    document.getElementById(area).appendChild(additionalParameter);

}

function addDataParam(area, tag) {
    var params = document.createElement("div");
    params.setAttribute("class", "form-inline");
    params.setAttribute("name", "data_param");

    var paramLabel = document.createElement("label");
    paramLabel.innerHTML = " Parameter:";

    var paramInput = document.createElement("input");
    paramInput.setAttribute("name", "data_param_key_" + tag);
    paramInput.setAttribute("class", "form-control");
    paramInput.setAttribute("style", "width:200px;margin:2px 10px 2px 10px");

    var valueLabel = document.createElement("label");
    valueLabel.innerHTML = " Value:";

    var valueInput = document.createElement("input");
    valueInput.setAttribute("name", "data_param_value_" + tag);
    valueInput.setAttribute("class", "form-control");
    valueInput.setAttribute("style", "width:400px;margin:2px 10px 2px 10px");

    var deleteButton = document.createElement("span");
    deleteButton.setAttribute("style", "width:22px;border-width:0px;font-family:webdings;margin-left:15px");
    deleteButton.innerHTML = "r";
    deleteButton.setAttribute("onclick", "clean(this)");

    //var deleteButton = document.createElement("button");
    //deleteButton.setAttribute("type","button");
    //deleteButton.setAttribute("style","margin-left:10px");
    //deleteButton.innerHTML="&times;";
    //deleteButton.setAttribute("onclick","clean(this)");

    params.appendChild(paramLabel);
    params.appendChild(paramInput);
    params.appendChild(valueLabel);
    params.appendChild(valueInput);
    params.appendChild(deleteButton);
    document.getElementById(area).appendChild(params);
}

function createData() {
    var json = JSON.stringify(generateDataJson("create"));
    var dataType = $("#dataTypeButton input:radio:checked").val();
    var environment = $("#environmentButton input:radio:checked").val();

    $.ajax({
        type: "post",
        data: {
            "data": json,
            "type": dataType,
            "environment": environment
        },
        url: "/createData",
        dataType: "text",
        success: function (message) {
            document.getElementById('dataArea').innerHTML = "";
            document.getElementById('messageArea').innerHTML = message;
        }
    });

}

function updateData() {
    var json = JSON.stringify(generateDataJson("update"));
    var dataType = $("#updateDataTypeButton input:radio:checked").val();
    var environment = $("#updateEnvironmentButton input:radio:checked").val();

    $.ajax({
        type: "post",
        data: {
            "data": json,
            "type": dataType,
            "environment": environment
        },
        url: "/createData",
        dataType: "text",
        success: function (message) {
            document.getElementById('dataArea').innerHTML = "";
            document.getElementById('messageArea').innerHTML = message;
        }
    });

}

function loadData() {
    var dataType = $("#updateDataTypeButton input:radio:checked").val();
    var environment = $("#updateEnvironmentButton input:radio:checked").val();

    var dataIds = document.getElementsByName("dataInput_update");
    var id = "";
    for (var i = 0; i < dataIds.length; i++) {
        id += dataIds[i].value + "_";
    }

    id = id.substring(0, id.length - 1);


    $.ajax({
        type: "post",
        data: {
            "id": id,
            "type": dataType,
            "environment": environment
        },
        url: "/loadData",
        dataType: "text",
        success: function (message) {

            var preArea = document.createElement("pre");
            preArea.innerHTML = syntaxHighlight(JSON.parse(message));
            document.getElementById('dataArea').innerHTML = "";
            document.getElementById('dataArea').appendChild(preArea);
            tempData = message;
        }
    });

}

function previewData(tag) {
    var obj = generateDataJson(tag);
    document.getElementById('PreviewResult').innerHTML = syntaxHighlight(obj);
    $('#previewModal').modal('show');
}

function generateDataJson(tag) {
    var dataIds = document.getElementsByName("dataInput_" + tag);
    var dataType = $("#dataTypeButton input:radio:checked").val();
    var obj = "";
    switch (dataType) {
        case "offer":
            obj = JSON.parse(tag == 'update' && tempData != "" ? tempData : baseOfferJson);
            obj['offerId'] = parseInt(dataIds[0].value);
            break;
        case "affiliate":
            obj = JSON.parse(tag == 'update' && tempData != "" ? tempData : baseAffiliateJson);
            obj['id'] = parseInt(dataIds[0].value);
            break;
        case "app":
            obj = JSON.parse(tag == 'update' && tempData != "" ? tempData : baseAppJson);
            obj['appId'] = parseInt(dataIds[0].value);
            obj['publisherId'] = parseInt(dataIds[1].value);
            break;
        case "personalizedOffer":
            obj = JSON.parse(tag == 'update' && tempData != "" ? tempData : basePersonalizedOffer);
            obj['offerId'] = parseInt(dataIds[0].value);
            obj['affId'] = parseInt(dataIds[1].value);
            break
        case "appOffer":
            obj = JSON.parse(tag == 'update' && tempData != "" ? tempData : baseAppOffer);
            obj['offerId'] = parseInt(dataIds[0].value);
            obj['appId'] = parseInt(dataIds[1].value);
            break;
    }

    var paramKeys = document.getElementsByName("data_param_key_" + tag);
    var paramValues = document.getElementsByName("data_param_value_" + tag);

    var json = "";
    if (paramKeys.length > 0) {

        var paramString = "{";
        for (var i = 0; i < paramKeys.length; i++) {
            var key = paramKeys[i].value;
            var value = paramValues[i].value;

            paramString += "\"" + key + "\":" + value + ",";
        }

        paramString = paramString.substring(0, paramString.length - 1) + "}";
        var paramJson = JSON.parse(paramString);

        json = $.extend({}, obj, paramJson);
    } else {
        json = obj;
    }

    return json;
}

function showInfo(tag) {
    document.getElementById('infoModalBody').innerHTML = "";
    if (tag == 'create') {
        document.getElementById('infoModalBody').innerHTML = "Create particular type of data info. The created data is generated using default data plus parameters added by user. You could see the generated data info using 'Preview' button before create the data in VNCC. ";
    } else {
        document.getElementById('infoModalBody').innerHTML = "Update particular type of data info. You could load the data info from current VNCC and then add parameters need to be updated. The updated data is generated using original data loading from system plus parameters added by user. You could see the generated data info using 'Preview' button before create the data in VNCC. If there is no data loaded, the 'Update' button will create a new Data using default data ";
    }

    $("#informationModal").modal();
}

function clean(ob) {
    ob.parentNode.parentNode.removeChild(ob.parentNode);
}

function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}