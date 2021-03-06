<%@ page contentType="text/html;charset=UTF-8" language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>工程勘察质量监管平台 - 主页</title>
    <link rel="shortcut icon" href="${ctxPath}/content/favicon.ico">
    <link href="${ctxPath}/content/plugins/bootstrap-4.3.0/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctxPath}/content/fonts/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctxPath}/content/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
       	<%@include file="system/commons/_tab.jsp"%>
        <!--左侧导航结束-->
        
        <!--右侧部分开始-->
        <%@include file="system/commons/_right.jsp"%>
        <!--右侧部分结束-->
        
        <!--右侧边栏开始-->
        <%@include file="system/commons/_theme.jsp"%>
        <!--右侧边栏结束-->
       
    </div>

    <!-- 全局js -->
    <script type="text/javascript" src="${ctxPath}/content/plugins/jquery/jquery.min.js?v=2.1.4"></script>
    <script type="text/javascript" src="${ctxPath}/content/plugins/bootstrap-4.3.0/js/popper.min.js?v=3.3.6"></script>
    <script type="text/javascript" src="${ctxPath}/content/plugins/bootstrap-4.3.0/js/bootstrap.min.js?v=3.3.6"></script>
    <script type="text/javascript" src="${ctxPath}/content/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script type="text/javascript" src="${ctxPath}/content/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="${ctxPath}/content/plugins/layer/layer.js"></script>

    <!-- 自定义js -->
    <script type="text/javascript" src="${ctxPath}/content/scripts/hplus.js?v=4.1.0"></script>
    <script type="text/javascript" src="${ctxPath}/content/scripts/contabs.js"></script>

    <!-- 第三方插件 -->
    <script src="${ctxPath}/content/plugins/pace/pace.min.js"></script>

</body>
</html>
