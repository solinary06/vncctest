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
    <%--<script src='<c:url value="/js/httpRequest.js"></c:url>'></script>--%>
    <script src="/js/httpRequest.js"></script>
    <script src="/webjars/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="/webjars/bootstrap/3.1.0/css/bootstrap.min.css">
</head>

<h2>Http template</h2>

<div class="container">
    <form class="form-httpGet" method="GET" action="/vncctest/httpGet">
        <label for="getUrl">URL:</label>
        <input id="getUrl" name="getUrl" type="text" class="text" size=35>
        <button type="submit" value="Submit">Submit</button>
    </form>
</div>

<div class="container" id="clickConv">
    <span class="container">
             <b>Click </b>
             <label for="clickServiceSelect">Server:</label>
             <select name="clickServiceSelect" id="clickServiceSelect" style="width:180px;margin:-2px;">
                 <option value="http://172.30.10.174:8080">http://172.30.10.174:8080</option>
                 <option value="http://172.30.10.146:8080">http://172.30.10.146:8080</option>
                 <option value="http://10.7.0.8:9076">http://10.7.0.8:9076</option>
             </select>

             <label for="apiSelect">api:</label>
             <select name="apiSelect" id="apiSelect" style="width:70px;margin:-2px;">
                 <option value="click">trace</option>
                 <option value="offerInfo">offerInfo</option>
             </select>

             <label for="offerId">Offer:</label>
             <input id="offerId" name="offerId" type="text" class="text" size=5>
             <label for="affId">Affiliate:</label>
             <input id="affId" name="affId" type="text" class="text" size=5>
             <button id="submitClickButton" value="Submit" onclick="doClick()">Submit</button>
    </span>

    <form class="form-conversion" method="GET" action="/vncctest/httpConv">
        <b>Conversion </b>
        <label for="convServiceSelect">Server:</label>
        <select name="convServiceSelect" id="convServiceSelect" style="width:200px;margin:-2px;">
            <option value="http://172.30.10.174:8080">http://172.30.10.174:8080</option>
            <option value="http://172.30.10.146:8080">http://172.30.10.146:8080</option>
            <option value="http://10.7.0.8:9076">http://10.7.0.8:9076</option>
        </select>

        <label for="transactionId">Transaction id:</label>
        <input id="transactionId" name="transactionId" type="text" class="text" size=65 value=<c:if
                test="${not empty transId}">${transId}</c:if>>
        <button type="submit" value="Submit">Submit</button>
    </form>

    <form class="form-queryMessage" method="GET" action="/vncctest/queryMessage">
        <b>RocketMQ message </b>
        <select name="messageTagSelect" id="messageTagSelect" style="width:50px;margin:-2px;">
            <option value="QA">QA</option>
            <option value="DEV">DEV</option>
            <option value="ALI">ALI</option>
        </select>

        <label for="messageKey">Transaction id:</label>
        <input id="messageKey" name="messageKey" type="text" class="text" size=65 value=<c:if
                test="${not empty transId}">${transId}</c:if>>
        <button type="submit" value="Submit">Submit</button>
    </form>

    <form class="form-queryTransId" method="GET" action="/vncctest/queryTransIdInHbase">
        <b>Hbase infomation </b>
        <label for="hbaseTransId">Transaction id:</label>
        <input id="hbaseTransId" name="hbaseTransId" type="text" class="text" size=65 value=<c:if
                test="${not empty transId}">${transId}</c:if>>
        <button type="submit" value="Submit">Submit</button>
    </form>

     <span class="container">
         <b>Regulation </b>
         <label for="bsServer">Server:</label>
         <input name="bsServer" id="bsServer" type="text" class="text" size=20>
         <label for="regulationTypeSelect">Type:</label>
         <select name="regulationTypeSelect" id="regulationTypeSelect" style="width:200px;margin:-2px;">
            <option value="updatedOfferInfo">updatedOfferInfo</option>
            <option value="updatedPersonalizedOfferInfo">updatedPersonalizedOfferInfo</option>
            <option value="updateAffiliateId">updateAffiliateId</option>
         </select>

         <label for="timeStamp">Timestamp:</label>
         <input id="timeStamp" name="timeStamp" type="text" class="text" size=5>
         <button id="submitRegulationButton" value="Submit" onclick="doRegulation()">Submit</button>
     </span>


</div>

<div class="container">
    <span id="messageArea" style="width:1000px"></span>
    <c:if test="${not empty msg}">
        <tr>
                <%--<script type="text/html" style="display:block">${msg}</script>--%>
                ${msg}
        </tr>
    </c:if>
</div>

</body>
</html>
