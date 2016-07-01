<%--
  Created by IntelliJ IDEA.
  User: steven.liu
  Date: 2016/6/21
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>hbase query</title>
    <script src="../../js/manageData.js"></script>
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
                <li class="active"><a href="/data">Data</a></li>
                <li><a href="/http">Click/Conv</a></li>
                <li><a href="/hbase">Hbase</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>
<div class="container">

    <div class="panel panel-default">


        <div class="panel-heading">
            <label class="from-control">Create Data</label>

            <div id="dataTypeButton" class="btn-group" data-toggle="buttons"
                 onchange="changeDataType(this.id,'createDataArea','create')">

                <label class="btn btn-default active">
                    <input type="radio" class="toggle" value="offer" name="type" checked> OFFER
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="affiliate" name="type"> AFF
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="app" name="type"> APP
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="personalizedOffer" name="type"> PERSONAL
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="appOffer" name="type"> APPOFFER
                </label>
            </div>

            <label class="from-control">on Environment</label>

            <div id="environmentButton" class="btn-group" data-toggle="buttons">

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
            <button type="button" class="btn btn-default"
                    style="background-color:transparent;border-style: none;color: #55c1ff;margin-left: 30%"
                    onclick="showInfo('create')">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
            </button>
        </div>

        <div class="panel-body">
      <span class="container">
        <span id="createDataArea" class="container form-inline">

          <label>Offer id</label>
          <input name="dataInput_create" class="form-control" style="width:200px;margin:2px 10px 2px 10px">

          <div id="additionalParameter"></div>

        </span>
        <div class="container form-inline">
            <button type="button" class="btn btn-default" onclick="addDataParam('additionalParameter','create')"
                    Style="background-color: Transparent; border-style :none;">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </button>
            <tr>Add additional parameter</tr>
        </div>
        <div class="container form-inline" style="margin-top: 30px;margin-left: 80%">
            <button type="button" class="btn btn-default" onclick="previewData('create')">Preview</button>
            <button type="button" class="btn btn-default" onclick="createData()">Create</button>
        </div>

    </span>
        </div>
    </div>

    <div class="panel panel-default">


        <div class="panel-heading">
            <label class="from-control">Update Data</label>

            <div id="updateDataTypeButton" class="btn-group" data-toggle="buttons"
                 onchange="changeDataType(this.id,'updateDataArea', 'update')">

                <label class="btn btn-default active">
                    <input type="radio" class="toggle" value="offer" name="updateType" checked> OFFER
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="affiliate" name="updateType"> AFF
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="app" name="updateType"> APP
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="personalizedOffer" name="updateType"> PERSONAL
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="appOffer" name="updateType"> APPOFFER
                </label>
            </div>

            <label class="from-control">on Environment</label>

            <div id="updateEnvironmentButton" class="btn-group" data-toggle="buttons">

                <label class="btn btn-default active">
                    <input type="radio" class="toggle" value="qa" name="updateEnvironment" checked> QA
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="dev" name="updateEnvironment"> DEV
                </label>
                <label class="btn btn-default">
                    <input type="radio" class="toggle" value="ali" name="updateEnvironment"> ALI
                </label>
                <%--<label class="btn btn-default">--%>
                    <%--<input type="radio" class="toggle" value="prod" name="updateEnvironment"> PROD--%>
                <%--</label>--%>
            </div>
            <button type="button" class="btn btn-default"
                    style="background-color:transparent;border-style: none;color: #55c1ff;margin-left: 29%"
                    onclick="showInfo('update')">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
            </button>
        </div>

        <div class="panel-body">
      <span class="container">
        <span id="updateDataArea" class="container form-inline">

          <label>Offer id</label>
          <input name="dataInput_update" class="form-control" style="width:200px;margin:2px 10px 2px 10px">

          <div id="updateParameter"></div>

        </span>
        <div class="container form-inline">
            <button type="button" class="btn btn-default" onclick="addDataParam('updateParameter','update')"
                    Style="background-color: Transparent; border-style :none;">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </button>
            <tr>Add additional parameter</tr>
        </div>
        <div class="container form-inline" style="margin-top: 30px;margin-left: 75%">
            <button type="button" class="btn btn-default" style="margin-right: 15px" onclick="loadData()">Load</button>
            <button type="button" class="btn btn-default" onclick="previewData('update')">Preview</button>
            <button type="button" class="btn btn-default" onclick="updateData()">Update</button>
        </div>

    </span>
        </div>
    </div>
</div>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <label class="from-control">Result</label>
        </div>
        <div class="panel-body">
            <span id="messageArea" style="width:1000px"></span>

            <div id="dataArea"></div>
        </div>
    </div>

</div>

<div class="modal fade" id="previewModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Data Preview
                </h4>
            </div>
            <div class="modal-body" id="previewModalBody">
        <pre id="PreviewResult">
        </pre>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">Close
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>


<div class="modal fade" id="informationModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    Information Panel
                </h4>
            </div>
            <div class="modal-body" id="infoModalBody">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">Close
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<style>
    pre {
        outline: 1px solid #ccc;
        padding: 5px;
        margin: 5px;
    }

    .string {
        color: green;
    }

    .number {
        color: darkorange;
    }

    .boolean {
        color: blue;
    }

    .null {
        color: magenta;
    }

    .key {
        color: red;
    }
</style>

</body>
</html>
