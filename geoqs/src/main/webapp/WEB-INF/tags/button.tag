<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="icon" required="false"%>
<%@ attribute name="btnType" required="false"%>
<%@ attribute name="space" required="false"%>
<%@ attribute name="clickFun" required="false"%>
<%@ attribute name="btnCss" required="false"%>

<%
String spaceCss = "";
if(space != null && space != "")
{
	spaceCss = "button-margin";
}
%>


<button type="button" class="btn btn-${btnType} ${spaceCss}" onclick="${clickFun}" id="${id}">
    <i class="fa ${icon}"></i>&nbsp;${name}
</button>

