<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag import="com.tigis.geoqs.util.ToolUtil" %>
<%@ attribute name="id" required="false"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="value" required="false"%>
<%@ attribute name="type" required="false"%>
<%@ attribute name="hidden" required="false"%>
<%@ attribute name="hiddenValue" required="false"%>
<%@ attribute name="readonly" required="false"%>
<%@ attribute name="clickFun" required="false"%>
<%@ attribute name="style" required="false"%>
<%@ attribute name="selectId" required="false"%>
<%@ attribute name="selectFlag" required="false"%>
<%@ attribute name="selectStyle" required="false"%>
<%@ attribute name="selectTreeId" required="false"%>
<%@ attribute name="underline" required="false"%>
<%@ attribute name="disabled" required="false"%>


<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <input class="form-control" id="${id}" name="${id}"
               <c:if test="${value != null && value != ''}">
                    value="${ToolUtil.dateType(value)}"
               </c:if>
               <c:if test="${type != null && type != ''}">
                    type="${type}"
               </c:if>
               <c:if test="${type == null || type == '' }">
                    type="text"
               </c:if>
               <c:if test="${readonly != null && readonly != ''}">
                    readonly="${readonly}"
               </c:if>
               <c:if test="${clickFun != null && clickFun != ''}">
                    onclick="${clickFun}"
               </c:if>
               <c:if test="${style != null && style != ''}">
                    style="${style}"
               </c:if>
               <c:if test="${disabled != null && disabled != ''}">
                    disabled="${disabled}"
               </c:if>
        >
        <c:if test="${hidden != null && hidden != ''}">
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue}">
        </c:if>

        <c:if test="${selectFlag != null && selectFlag != ''}">
            <div id="${selectId}" style="display: none; position: absolute; z-index: 200;">
                <ul id="${selectTreeId}" class="ztree tree-box" style="${selectStyle}"></ul>
            </div>
        </c:if>
    </div>
</div>
<c:if test="${underline != null && underline == 'true'} ">
    <div class="hr-line-dashed"></div>
</c:if>


