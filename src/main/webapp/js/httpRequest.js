/**
 * Created by steven.liu on 2015/12/14.
 */
function doClick(){
    var server = document.getElementById("clickServiceSelect").value;
    var api =  document.getElementById("apiSelect").value;
    var offer = document.getElementById("offerId").value;
    var aff= document.getElementById("affId").value;

    $.ajax({
        type: "post",
        data: {
            "clickServiceSelect": server,
            "apiSelect": api,
            "offerId": offer,
            "affId": aff
        },
        url: "vncctest/httpClick",
        dataType: "text",
        success: function (message) {
           document.getElementById('messageArea').innerHTML=message;
        }
    });

}

function doRegulation(){
    var server = document.getElementById("bsServer").value;
    var type =  document.getElementById("regulationTypeSelect").value;
    var time = document.getElementById("timeStamp").value;

    $.ajax({
        type: "post",
        data: {
            "server": server,
            "type": type,
            "time": time
        },
        url: "vncctest/doRegulation",
        dataType: "text",
        success: function (message) {
            document.getElementById('messageArea').innerHTML=message;
        }
    });
}
