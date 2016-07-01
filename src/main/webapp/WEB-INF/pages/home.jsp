<%--
  Created by IntelliJ IDEA.
  User: steven.liu
  Date: 2016/6/28
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
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
                <li class="active"><a href="/home">Home</a></li>
                <li><a href="/data">Data</a></li>
                <li><a href="/http">Click/Conv</a></li>
                <li><a href="/hbase">Hbase</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>

<div class="col-xs-12 col-sm-12">
    <div class="jumbotron">
        <h1>Welcome to VNCC Test System</h1>

        <p>This is an system help you test with online/offline visitor node and central controller.</p>
    </div>
</div>
</body>
</html>
