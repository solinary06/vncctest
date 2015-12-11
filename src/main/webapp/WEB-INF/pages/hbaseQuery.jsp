<%--
  Created by IntelliJ IDEA.
  User: steven.liu
  Date: 2015/10/28
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>hbase query</title>
</head>
<body>
<h2>Hbase template</h2>

<h3>=============Query rowKey================</h3>
<div class="container">
    <form class="form-transId" method="GET" action="/vncctest/queryHbase">
        <label for="queryTable">Table:</label>
        <input id="queryTable" name="delTable" type="text" class="text">
        <i>*Default table is 'clicklog'</i>
        <br>
        <label for="queryRow">Row:</label>
        <input id="queryRow" name="queryRow" type="text" class="text">
        <br>
        <button type="submit" value="Submit">Query</button>
    </form>
</div>
<h3>=============Delete row================</h3>

<div class="container">
    <form class="form-transId" method="POST" action="/vncctest/delRow">
        <label for="delTable">Table:</label>
        <input id="delTable" name="delTable" type="text" class="text">
        <br>
        <label for="delRow">Row/Transaction id:</label>
        <input id="delRow" name="delRow" type="text" class="text">
        <br>
        <button type="submit" value="Submit">Delete</button>
    </form>
</div>
<h3>=============Add row================</h3>

<div class="container">
    <form class="form-transId" method="POST" action="/vncctest/addRow">
        <label for="addTable">Table:</label>
        <input id="addTable" name="addTable" type="text" class="text">
        <br>
        <label for="addColumnFamily">Column Family:</label>
        <input id="addColumnFamily" name="addColumnFamily" type="text" class="text">
        <br>
        <label for="addColumn">Column</label>
        <input id="addColumn" name="addColumn" type="text" class="text">
        <br>
        <label for="addRow">Row/Transaction id:</label>
        <input id="addRow" name="addRow" type="text" class="text">
        <br>
        <label for="addValue">Value:</label>
        <input id="addValue" name="addValue" type="text" class="text">
        <br>
        <button type="submit" value="Submit">Add</button>
    </form>
</div>
<div class="container">
    <c:if test="${not empty msg}">
        <tr>
            <td>${msg}</td>
        </tr>
    </c:if>
</div>


</body>
</html>
