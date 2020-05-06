<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>ActiveMQ 演示</title>
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
                    <div class="panel-title">发送一个消息</div>
                </div>
                <div class="panel-body">
                    <form>
                        <div class="form-group">
                            <label for="type">消息类型</label>
                            <select id="type" class="selectpicker form-control">
                                <option value="queue">队列</option>
                                <option value="topic">主题</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="message">内容</label>
                            <input id="message" type="text" class="form-control">
                        </div>
                        <button type="button" class="btn" onclick="send();">发送</button>
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
    function send() {
        ajaxRequest_post("/testActivemq/send", {
            type: $("#type").val(), message: $("#message").val()
        }, function (result) {
            layer.alert(result);
        });
    }
</script>
</body>
</html>