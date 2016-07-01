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
            <a class="navbar-brand" href="#">VNCC Test</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/home">Home</a></li>
                <li><a href="/data">Data</a></li>
                <li><a href="/http">Click/Conv</a></li>
                <li class="active"><a href="/hbase">Hbase</a></li>
                           </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
<div class="container">

    <div class="panel panel-default">


        <div class="panel-heading">
            <label class="from-control">Query By RowKey</label>
        </div>

        <div class="panel-body">
            <div class="form-inline">
                <form class="form-inline" method="GET" action="/vncctest/queryHbase">
                    <label for="queryTable">Table:</label>
                    <input class = "form-control" id="queryTable" name="delTable" type="text">
                    <label for="queryRow">Row:</label>
                    <input class = "form-control" id="queryRow" name="queryRow" type="text" class="text">
                    <button class = "btn btn-default" type="submit" value="Submit">Query</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container">

    <div class="panel panel-default">


        <div class="panel-heading">
            <label class="from-control">Delete Row</label>
        </div>

        <div class="panel-body">
            <div class="form-inline">
                <form method="POST" action="/vncctest/delRow">
                    <label for="delTable">Table:</label>
                    <input class = "form-control" id="delTable" name="delTable" type="text">
                    <label for="delRow">Row/Transaction id:</label>
                    <input class = "form-control" id="delRow" name="delRow" type="text">
                    <button class="btn btn-default" type="submit" value="Submit">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container">

    <div class="panel panel-default">


        <div class="panel-heading">
            <label class="from-control">Add New Row</label>
        </div>

        <div class="panel-body">
            <div class="container">
                <form class="form-inline" method="POST" action="/vncctest/addRow">
                    <label for="addTable">Table:</label>
                    <input id="addTable" name="addTable" type="text" class = "form-control">
                    <label for="addColumnFamily">Column Family:</label>
                    <input id="addColumnFamily" name="addColumnFamily" type="text" class = "form-control">
                    <label for="addColumn">Column</label>
                    <input id="addColumn" name="addColumn" type="text" class = "form-control">
                    <br>
                    <br>
                    <label for="addRow">Row/Transaction id:</label>
                    <input id="addRow" name="addRow" type="text" class = "form-control">
                    <label for="addValue">Value:</label>
                    <input id="addValue" name="addValue" type="text" class = "form-control">
                    <button class="btn btn-default" type="submit" value="Submit">Add</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <label class="from-control">Result</label>
        </div>
        <div class="panel-body">
            <c:if test="${not empty msg}">
                <tr>
                    <td>${msg}</td>
                </tr>
            </c:if>
        </div>
    </div>

</div>


</body>
</html>
