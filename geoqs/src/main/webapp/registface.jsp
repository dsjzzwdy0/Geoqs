<%@ page contentType="text/html;charset=UTF-8" language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.main{
	text-align: center;
	width:1000px;
	margin:0 auto;
}
.label{
	width: 200px;
}

.input_label
{
	text-align: left;
}
</style>
</head>
<body>
<div class="main">
<table style="width:1000px;">
<tr>
	<td class="label">用户ID</td><td class="input_value"><input type="text" name="userid"></td>
</tr>
<tr>
	<td class="label">用户ID</td><td class="input_value"><input type="text" name="name"></td>
</tr>
<tr>
	<td class="label">用户ID</td><td class="input_value"><input type="file" name="userid"></td>
</tr>
<tr>
	<td class="label"></td><td><input type="button" value="提交"></td>
</tr>
</table>
</div>

<script type="text/javascript">

	if (typeof FileReader == 'undefined') {
		document.getElementById("xmTanDiv").InnerHTML = "<h1>当前浏览器不支持FileReader接口</h1>";
		document.getElementById("xdaTanFileImg").setAttribute("disabled", "disabled");
	}
	//选择图片，马上预览
	function xmTanUploadImg(obj) {
		var file = obj.files[0];
		console.log(obj);
		console.log(file);
		console.log("file.size = " + file.size);
		var reader = new FileReader();
		reader.onloadstart = function(e) {
			console.log("开始读取....");
		}
		reader.onprogress = function(e) {
			console.log("正在读取中....");
		}
		reader.onabort = function(e) {
			console.log("中断读取....");
		}
		reader.onerror = function(e) {
			console.log("读取异常....");
		}
		reader.onload = function(e) {
			console.log("成功读取....");
			var img = document.getElementById("avarimgs");
			img.src = e.target.result;
			//或者 img.src = this.result;  //e.target == this
		}
		reader.readAsDataURL(file)
	}
</script>

</body>
</html>