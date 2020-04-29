<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/base.jsp" %>
<html>
<head>
    <style>
        /*
        TIps CSS 层级选择: 两个选择期间用空格隔开
         此处选择的是 class 包含 btn-primary 的元素中的 a 标签元素
        */
        .btn-primary a {
            color: #ffffff;
        }

    </style>
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div style="visibility: hidden;height: 10px;">这是一个没有灵魂的占位 DIV</div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <button type="button" class="btn btn-primary btn-lg btn-block">
                <!--
                Tips 页面的请求路径中, 是否以 "/" 开头, 所代表的请求地址是不同的
                 假设项目的地址和端口号是 localhost:8080, 项目名为 mvc
                 请求路径是 "/test" 时, 最终的请求是 localhost:8080/test
                 请求路径是 "test" 时, 最终的请求是 localhost:8080/mvc/test
                -->
                <a href="<%=wabApp%>/testRequestMapping/init">@RequestMapping</a>
            </button>
        </div>
        <div class="col-md-3">
            <button type="button" class="btn btn-primary btn-lg btn-block">
                <a href="<%=wabApp%>/testParam/init">传参与取值</a>
            </button>
        </div>
        <div class="col-md-3">
            <button type="button" class="btn btn-primary btn-lg btn-block">
                <a href="<%=wabApp%>/testRedis/init">Redis</a>
            </button>
        </div>
        <div class="col-md-3">
            <button type="button" class="btn btn-primary btn-lg btn-block"
                    onclick="">
                预留
            </button>
        </div>
    </div>
</div>
</body>
</html>
