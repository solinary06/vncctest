<%--
  Created by IntelliJ IDEA.
  User: steven.liu
  Date: 2015/12/10
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>HTTP request</title>
    <link href="/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <%--<script src='<c:url value="/js/httpRequest.js"></c:url>'></script>--%>
    <script src="../../js/httpRequest.js"></script>
    <script src="../../webjars/jquery/2.1.4/jquery.min.js"></script>
    <link rel='stylesheet' href='../../webjars/bootstrap/3.1.0/css/bootstrap.min.css'>
    <script type='text/javascript' src='../../webjars/bootstrap/3.1.0/js/bootstrap.min.js'></script>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">VNCC Test</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/home">Home</a></li>
                <li><a href="/data">Data</a></li>
                <li class="active"><a href="/http">Click/Conv</a></li>
                <li><a href="/hbase">Hbase</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>

<%--<div class="container">--%>
<%--<form class="form-inline" method="GET" action="/vncctest/httpGet">--%>
<%--<label for="getUrl">URL:</label>--%>
<%--<input class="form-control" id="getUrl" name="getUrl" type="text">--%>
<%--<button class="btn btn-default" type="submit" value="Submit">Submit</button>--%>
<%--</form>--%>
<%--</div>--%>

<div class="container" id="clickConv">

    <div class="panel panel-default">

        <div class="panel-heading">
            <label class="from-control">Environment</label>

            <div id="environmentButton" class="btn-group" data-toggle="buttons" onchange="selectEnvironment()">

                <label class="btn btn-default active">
                    <input type="radio" class="toggle" value="qa" name="environment" checked> QA
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="dev" name="environment"> DEV
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="ali" name="environment"> ALI
                </label>
                <%--<label class="btn btn-default">--%>
                    <%--<input type="radio" class="toggle" value="prod" name="environment"> PROD--%>
                <%--</label>--%>
            </div>
        </div>
        <div class="panel-body">

    <span class="container">
        <span class="container form-inline">
            <b>Click </b>
            <label class="form-label">Server:</label>
            <select class="form-control" name="clickServiceSelect" id="clickServiceSelect"
                    style="width:220px;margin:-2px;">
                <option value="http://172.30.10.174:8080">http://172.30.10.174:8080</option>
                <option value="http://172.30.10.146:8080">http://172.30.10.146:8080</option>
                <option value="http://10.7.0.8:9076">http://10.7.0.8:9076</option>
            </select>

            <label for="apiSelect">api:</label>
            <select class="form-control" name="apiSelect" id="apiSelect" style="width:100px;margin:-2px;">
                <option value="click">trace</option>
                <option value="offerInfo">offerInfo</option>
            </select>

            <label for="offerId">Offer:</label>
            <input class="form-control" id="offerId" name="offerId" type="text" size=5>
            <label for="affId">Affiliate:</label>
            <input class="form-control" id="affId" name="affId" type="text" size=5>
            <label for="appId">App:</label>
            <input class="form-control" id="appId" name="appId" type="text" size=5>
            <label for="apiTypeSelect">type:</label>
            <select class="form-control" name="apiTypeSelect" id="apiTypeSelect" style="width:100px;margin:-2px;">
                <option value="affiliate">affiliate</option>
                <option value="sdk">sdk</option>
                <option value="offline">offline</option>
                <option value="realtime">realtime</option>
            </select>
            <button class="btn btn-default" id="submitClickButton" value="Submit" onclick="doClick()">Submit</button>
            <div class="container" id="param_click" style="margin-top: 5px"></div>
            <div class="container" id="header_click"></div>
            <div class="container form-inline" style="margin-top: 5px">
                <button type="button" class="btn btn-default" onclick="addHeader('click')" style="border-style: none">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </button>
                <tr>Add header parameter</tr>
                <button type="button" class="btn btn-default" onclick="addParam('click')" style="border-style: none">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </button>
                <tr>Add url parameter</tr>
            </div>
        </span>

    </span>

    <span class="container">
    <span class="form-inline">
        <b>Conversion </b>
        <label for="convServiceSelect">Server:</label>
        <select class="form-control" name="convServiceSelect" id="convServiceSelect" style="width:220px;margin:-2px;">
            <option value="http://172.30.10.174:8080">http://172.30.10.174:8080</option>
            <option value="http://172.30.10.146:8080">http://172.30.10.146:8080</option>
            <option value="http://10.7.0.8:9076">http://10.7.0.8:9076</option>
        </select>

        <label for="transactionId">Transaction id:</label>
        <input class="form-control" id="transactionId" name="transactionId" type="text" size=65>
        <button class="btn btn-default" value="Submit" onclick="doConversion()">Submit</button>

        <div class="container" id="param_conversion" style="margin-top: 5px"></div>
        <div class="container" id="header_conversion"></div>
        <div class="container form-inline" style="margin-top: 5px">
            <button type="button" class="btn btn-default" onclick="addHeader('conversion')" style="border-style: none">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </button>
            <tr>Add header parameter</tr>
            <button type="button" class="btn btn-default" onclick="addParam('conversion')" style="border-style: none">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </button>
            <tr>Add url parameter</tr>
        </div>
    </span>

    </span>


    <span class="container">
    <span class="form-inline">
        <b>RocketMQ message </b>
        <select class="form-control" name="messageTagSelect" id="messageTagSelect" style="width:100px;margin:-2px;">
            <option value="QA">QA</option>
            <option value="DEV">DEV</option>
            <option value="ALI">ALI</option>
        </select>

        <label for="messageKey">Transaction id:</label>
        <input class="form-control" id="messageKey" name="messageKey" type="text" size=65>
        <button class="btn btn-default" value="Submit" onclick="queryMq()">Submit</button>
    </span>
    </span>

    <span class="container">
    <span class="form-inline">
        <b>Hbase infomation </b>
        <label for="hbaseTransId">Transaction id:</label>
        <input class="form-control" id="hbaseTransId" name="hbaseTransId" type="text" size=65>
        <button class="btn btn-default" value="Submit" onclick="queryHbase()">Submit</button>
    </span>
        </span>

            <%--<span class="container form-inline">--%>
            <%--<b>Regulation </b>--%>
            <%--<label for="bsServer">Server:</label>--%>
            <%--<input class="form-control" name="bsServer" id="bsServer" type="text" size=20>--%>
            <%--<label for="regulationTypeSelect">Type:</label>--%>
            <%--<select class="form-control" name="regulationTypeSelect" id="regulationTypeSelect" style="width:200px;margin:-2px;">--%>
            <%--<option value="updatedOfferInfo">updatedOfferInfo</option>--%>
            <%--<option value="updatedPersonalizedOfferInfo">updatedPersonalizedOfferInfo</option>--%>
            <%--<option value="updateAffiliateId">updateAffiliateId</option>--%>
            <%--</select>--%>

            <%--<label for="timeStamp">Timestamp:</label>--%>
            <%--<input class="form-control" id="timeStamp" name="timeStamp" type="text" size=5>--%>
            <%--<button class="btn btn-default" id="submitRegulationButton" value="Submit" onclick="doRegulation()">Submit</button>--%>
            <%--</span>--%>

        </div>
    </div>
</div>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <label class="from-control">Result</label>
        </div>
        <div class="panel-body">
            <div id="messageArea" style="width:1000px;overflow:auto"></div>
        </div>
    </div>

</div>

</body>
</html>
