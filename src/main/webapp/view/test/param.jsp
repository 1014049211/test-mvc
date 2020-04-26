<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<html>
<head>
    <title>传参取值演示</title>
</head>
<body>
<div id="app" class="container">
    <div class="row">
        <div class="col-md-12">
            <div style="visibility: hidden;height: 10px;">这是一个没有灵魂的占位 DIV</div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">REST 风格</div>
                </div>
                <div class="panel-body">
                    <form class="form">
                        <div class="form-group">
                            <input id="restUrl" type="text" class="form-control" disabled
                                   title="REST 风格路径演示" value="/testParam/testRest/小明/男/18">
                        </div>
                        <button type="button" class="btn" onclick="urlTest('restUrl');">测试一下</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">使用 @RequestParam 取值: 路径请求</div>
                </div>
                <div class="panel-body">
                    <form class="form">
                        <div class="form-group">
                            <input id="requestParamUrl" type="text" class="form-control" disabled
                                   title="路径请求演示 @RequestParam" value="/testParam/testRequestParam1?name=小明&age=18">
                        </div>
                        <button type="button" class="btn" onclick="urlTest('requestParamUrl');">测试一下</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">使用 @RequestParam 取值: 表单请求</div>
                </div>
                <div class="panel-body">
                    <form class="form" action="<%=wabApp%>/testParam/testRequestParam" method="post">
                        <div class="form-group">
                            <label for="name">名字</label>
                            <input id="name" name="name" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="age">年龄</label>
                            <input id="age" name="age" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="gender"></label>
                            <select id="gender" name="gender" class="selectpicker form-control">
                                <option value="">请选择</option>
                                <option>男</option>
                                <option>女</option>
                                <option>人妖</option>
                            </select>
                        </div>
                        <button type="submit" class="btn">测试一下</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    /**
     * REST 风格的参数传递
     */
    function urlTest(inputId) {
        ajaxRequest_post($("#" + inputId).val(), null, function (result) {
            if (result.code == '0') {
                layer.alert(result.data);
            } else {
                layer.alert(result.message);
            }
            console.log(result);
        });
    }

    function formTest(formId) {

    }

</script>
</body>
</html>
