<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Redis 演示</title>
    <style>
        .btn {
            float: right;
        }
    </style>
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
                    <div class="panel-title">存储一个字符串数据</div>
                </div>
                <div class="panel-body">
                    <form>
                        <div class="form-group">
                            <label for="key1">KEY</label>
                            <input id="key1" type="text" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="value1">VALUE</label>
                            <input id="value1" type="text" class="form-control">
                        </div>
                        <button type="button" class="btn" onclick="test1();">保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title">获取一个字符串数据</div>
                </div>
                <div class="panel-body">
                    <form>
                        <div class="form-group">
                            <label for="key2">KEY</label>
                            <input id="key2" type="text" class="form-control">
                        </div>
                        <button type="button" class="btn" onclick="test2();">获取</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    /**
     * 向 Redis 中存储一个字符串值
     */
    function test1() {
        ajaxRequest_post("/testRedis/save", {
            key: $("#key1").val(), value: $("#value1").val()
        }, function (result) {
            layer.alert(result);
        });
    }

    /**
     * 从 Redis 中获取一个字符串值
     */
    function test2() {
        ajaxRequest_post("/testRedis/query", {key: $("#key2").val()}, function (result) {
            layer.alert(result);
        });
    }
</script>
</body>
</html>