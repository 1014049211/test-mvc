<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<html>
<head>
    <title>RequestMapping 演示</title>
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
            <div class="card">
                <div class="card-header">测试 @RequestMapping</div>
                <div class="card-body">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="url">请求路径</label>
                            <input id="url" type="text" class="form-control">
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

    /**
     * 测试 @RequestMapping
     */
    function testRequestMapping() {
        let url = $("#url").val() || "";
        if (url === "") {
            layer.msg("请输入请求地址!");
            return;
        }

        ajaxRequest_all(url, true, $("#requestType").val(), $("#contentType").val()
            , null, null, null, null, function (result) {
                // 展示结果
                layer.alert(typeof result == "string" ? result : "结果已打印到控制台, 请按 F12 键查看");
                console.log(result);
            });
    }

</script>
</body>
</html>
