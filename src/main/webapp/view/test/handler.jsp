<%--suppress ELValidationInJSP --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<html>
<head>
    <title>演示 handler</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div style="visibility: hidden;height: 10px;">这是一个没有灵魂的占位 DIV</div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">
                        <h1>接收到了以下数据</h1>
                    </div>
                </div>
                <div class="panel-body">
                    ${test}
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
