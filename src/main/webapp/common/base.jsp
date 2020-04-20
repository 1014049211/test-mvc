<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // 项目根路径
    final String wabApp = request.getContextPath();
%>
<!-- CSS -->
<link rel="stylesheet" href="<%=wabApp%>/static/plugin/bootstrap/basic/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=wabApp%>/static/plugin/bootstrap/select/css/bootstrap-select.min.css">

<!-- JavaScript -->
<!-- Tips bootstrap、layer 和 jquery_ajax.js 都需要 jQuery, 所以要放在 jQuery 后面 -->
<script src="<%=wabApp%>/static/plugin/jquery/jquery.3.5.0.min.js"></script>
<script src="<%=wabApp%>/static/plugin/bootstrap/basic/js/bootstrap.min.js"></script>
<script src="<%=wabApp%>/static/plugin/bootstrap/select/js/bootstrap-select.min.js"></script>
<script src="<%=wabApp%>/static/plugin/layer/layer.js"></script>
<script src="<%=wabApp%>/static/js/jquery_ajax.js"></script>

<script>
    // 项目根路径
    // Tips 使用 const 定义 JS 变量相当于 Java 中的 final 修饰
    const wabApp = "<%=wabApp%>";
</script>
