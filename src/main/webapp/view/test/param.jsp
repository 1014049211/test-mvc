<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>传参取值演示</title>
    <style>
        .btn {
            float: right;
        }
    </style>
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
                    <form>
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
                    <form>
                        <div class="form-group">
                            <input id="requestParamUrl" type="text" class="form-control" disabled
                                   title="路径请求演示 @RequestParam" value="/testParam/testRequestParam?name=小明&age=18">
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
                    <!--
                        Tips form 提交中文会乱码, 解决办法是添加 org.springframework.web.filter.CharacterEncodingFilter
                    -->
                    <form action="<%=wabApp%>/testParam/testRequestParam" method="post">
                        <div class="form-group">
                            <label for="name">名字</label>
                            <input id="name" name="name" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="age">年龄</label>
                            <input id="age" name="age" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="gender">性别</label>
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
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">使用 @RequestBody 接收复杂类型参数</div>
                </div>
                <div class="panel-body">
                    <span>{name: "小明", gender: "男", age: 18, date: "{{nowTime}}"}</span>
                    <button type="button" class="btn" onclick="testRequestBody();">发送</button>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">使用表单提交复杂对象</div>
                </div>
                <div class="panel-body">
                    <form action="<%=wabApp%>/testParam/testComplexity" method="post">
                        <div class="form-group-sm">
                            <label for="name1">名字</label>
                            <input id="name1" name="name" type="text" class="form-control">
                            <label for="age1">年龄</label>
                            <input id="age1" name="age" type="text" class="form-control">
                            <!--
                            Tips 没有 @RequestBody 注解的复杂对象在接收数据时, 只能做基本的类型转换
                                无法将 String 转为 Enum 的
                            <label for="gender1">性别</label>
                            <select id="gender1" name="gender" class="form-control selectpicker">
                                <option value="">请选择</option>
                                <option>男</option>
                                <option>女</option>
                                <option>人妖</option>
                            </select>
                            Tips 属性为对象时, 传值需要添加属性名, 例如 contact.phone 代表给 contact 属性的 phone 属性传参
                            -->
                            <label for="phone">电话</label>
                            <input id="phone" name="contact.phone" type="text" class="form-control">
                            <label for="email">邮箱</label>
                            <input id="email" name="contact.email" type="text" class="form-control">
                        </div>
                        <button type="submit" class="btn">发送</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    var app = new Vue({
        el: "#app",
        data: {
            nowTime: dataFormat()
        }
    });

    setInterval(function () {
        app.$data.nowTime = dataFormat();
    }, 1000);

    function dataFormat() {
        let now = new Date();
        return now.getFullYear()
            + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1)
            + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate()
            + " " + (now.getHours() < 10 ? "0" : "") + now.getHours()
            + ":" + (now.getMinutes() < 10 ? "0" : "") + now.getMinutes()
            + ":" + (now.getSeconds() < 10 ? "0" : "") + now.getSeconds();
    }

    /**
     * REST 风格的参数传递
     */
    function urlTest(inputId) {
        ajaxRequest_post($("#" + inputId).val(), null, function (result) {
            if (result.code == '1') {
                layer.alert(result.data);
            } else {
                layer.alert(result.message);
            }
            console.log(result);
        });
    }

    /**
     * 测试 @RequestBody
     */
    function testRequestBody() {
        ajaxRequest_all("/testParam/testRequestBody", null, null, "application/json;charset=UTF-8",
            {name: "小明", sex: "男", age: "18", date: dataFormat()}, null, null, null, function (result) {
                layer.alert(result.code == '1' ? "成功" : "失败");
                console.log(result);
            });
    }
</script>
</body>
</html>