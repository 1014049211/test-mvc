<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<html>
<head>
    <title>@RequestMapping 演示</title>
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
                    <div class="panel-title">测试 @RequestMapping</div>
                </div>
                <div class="panel-body">
                    <form>
                        <div class="form-group">
                            <label for="editUrl">填写路径</label>
                            <label><input type="radio" name="urlType" value="editUrl" v-model="selectedRadio"></label>
                            <input id="editUrl" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="selectUrl">选择路径</label>
                            <label><input type="radio" name="urlType" value="selectUrl" v-model="selectedRadio"></label>
                            <select id="selectUrl" type="text" class="selectpicker form-control" disabled>
                                <optgroup label="测试 path">
                                    <option>/testRequestMapping/testPath1</option>
                                    <option>/testRequestMapping/testPath2</option>
                                    <option>/testRequestMapping/testPath3</option>
                                    <option>/testRequestMapping/testPath4/小明/18</option>
                                </optgroup>
                                <optgroup label="测试 method">
                                    <option>/testRequestMapping/testPath1</option>
                                </optgroup>
                                <optgroup label="测试 params">
                                    <option>/testRequestMapping/testParams</option>
                                    <option>/testRequestMapping/testParams?name=小明</option>
                                    <option>/testRequestMapping/testParams?name=小红</option>
                                </optgroup>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="requestType">请求方式</label>
                            <select id="requestType" class="selectpicker form-control">
                                <option selected>POST</option>
                                <option>GET</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="contentType">数据类型</label>
                            <select id="contentType" class="selectpicker form-control">
                                <option selected>application/x-www-form-urlencoded;charset=UTF-8</option>
                                <option>application/json;charset=UTF-8</option>
                            </select>
                        </div>
                        <button type="button" class="btn" onclick="testRequestMapping();">发送请求</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    // 构建 vue 数据模型
    var app = new Vue({
        el: "#app",
        data: {
            // 选中的 radio 值
            selectedRadio: "editUrl"
        }
    });

    // 监听 radio 值, 切换输入地址和选择地址的可编辑状态
    app.$watch("selectedRadio", function (oldValue, newValue) {
        $("#" + oldValue).removeAttr("disabled");
        $("#" + newValue).attr("disabled", "disabled");
        $('.selectpicker').selectpicker('refresh');
    });

    /**
     * 测试 @RequestMapping
     */
    function testRequestMapping() {
        // 根据选中的 radio 的值来确定获取哪个 url
        let url = $("#" + $("input:radio[name='urlType']:checked").val()).val() || "";
        if (url === "") {
            layer.msg("请输入请求地址!");
            return;
        }

        ajaxRequest_all(url, true, $("#requestType").val(), $("#contentType").val()
            , {name: "小明", age: 18, gender: "男", birthday: "2020-04-01"}, null, null, null, function (result) {
                // 展示结果
                layer.alert(typeof result == "string" ? result : "结果已打印到控制台, 请按 F12 键查看");
                console.log(result);
            });
    }

</script>
</body>
</html>
