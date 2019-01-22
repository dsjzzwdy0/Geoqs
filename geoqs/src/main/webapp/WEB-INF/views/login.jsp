<%@ page contentType="text/html;charset=UTF-8" language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Guns - 登录</title>
    <link rel="shortcut icon" href="${ctxPath}/content/favicon.ico">
    <link href="${ctxPath}/content/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctxPath}/content/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctxPath}/content/css/animate.css" rel="stylesheet">
    <link href="${ctxPath}/content/css/style.css?v=4.1.0" rel="stylesheet">
    <script src="${ctxPath}/content/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctxPath}/content/js/bootstrap.min.js?v=3.3.6"></script>
</head>

<body class="gray-bg">
 <script type="text/javascript">
if (window.top !== window.self) {
	window.top.location = window.location;
}
</script>
<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div style="padding: 10px 0px;">
        <div>
            <h1 class="logo-name">GS</h1>
        </div>
        <h3>欢迎使用 Guns</h3>
        <br/>
        <h4 style="color: red;">${tips}</h4>
        <form class="m-t" role="form" action="${ctxPath}/login" method="post">
            <div class="form-group">
                <input type="text" name="username" class="form-control" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="密码" required="">
            </div>
            <div class="form-group" style="float: left;">
                <div class="col-sm-8" style="padding-left: 0px; padding-right: 0px;">
                    <input class="form-control" type="text" name="kaptcha" placeholder="验证码" required="">
                </div>
                <div class="col-sm-4" style="padding-left: 0px; padding-right: 0px;">
                    <img src="${ctxPath}/kaptcha" id="kaptcha" width="100%" height="100%"/>
                </div>
            </div>
            <div class="form-group" style="float: left;">
                <div class="checkbox" style="text-align: left">
                    <label>
                        <input type="checkbox" name="remember" style="margin-top: 2px;">记住我
                    </label>
                </div>
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
            </p>
        </form>
    </div>
</div>

<script>
    $(function () {
        $("#kaptcha").on('click', function () {
            $("#kaptcha").attr('src', '${ctxPath}/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
        });
    });
</script>

</body>

</html>
