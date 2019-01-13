<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit"/><!-- 让360浏览器默认选择webkit内核 -->

    <!-- 全局css -->
    <link rel="shortcut icon" href="${ctxPath}/content/favicon.ico">
    <link href="${ctxPath}/content/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctxPath}/content/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/validate/bootstrapValidator.min.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="${ctxPath}/content/css/_fstyle.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/ztree/zTreeStyle.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/plugins/bootstrap-treetable/bootstrap-treetable.css" rel="stylesheet"/>
    <!-- <link href="${ctxPath}/content/css/plugins/ztree/demo.css" rel="stylesheet"> -->

    <!-- 全局js -->
    <script src="${ctxPath}/content/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctxPath}/content/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${ctxPath}/content/js/plugins/ztree/jquery.ztree.all.min.js"></script>
    <script src="${ctxPath}/content/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctxPath}/content/js/plugins/validate/bootstrapValidator.min.js"></script>
    <script src="${ctxPath}/content/js/plugins/validate/zh_CN.js"></script>
    <script src="${ctxPath}/content/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="${ctxPath}/content/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${ctxPath}/content/js/plugins/bootstrap-treetable/bootstrap-treetable.js"></script>
    <script src="${ctxPath}/content/js/plugins/layer/layer.js"></script>
    <script src="${ctxPath}/content/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="${ctxPath}/content/js/plugins/iCheck/icheck.min.js"></script>
    <script src="${ctxPath}/content/js/plugins/laydate/laydate.js"></script>
    <script src="${ctxPath}/content/js/plugins/webuploader/webuploader.min.js"></script>
    <script src="${ctxPath}/content/js/common/ajax-object.js"></script>
    <script src="${ctxPath}/content/js/common/bootstrap-table-object.js"></script>
    <script src="${ctxPath}/content/js/common/tree-table-object.js"></script>
    <script src="${ctxPath}/content/js/common/web-upload-object.js"></script>
    <script src="${ctxPath}/content/js/common/ztree-object.js"></script>
    <script src="${ctxPath}/content/js/common/Feng.js"></script>

    <style type="text/css">
        table{  
            width:100px;  
            table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */  
        }
        td{  
            width:100%;  
            word-break:keep-all;/* 不换行 */  
            white-space:nowrap;/* 不换行 */  
            overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */  
            text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用*/  
        }
    </style>

    <script type="text/javascript">
        Feng.addCtx("${ctxPath}");
        Feng.sessionTimeoutRegistry();
    </script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
	<tiles:insertAttribute name="body" />
</div>
<script src="${ctxPath}/content/js/content.js?v=1.0.0"></script>
</body>
</html>