<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="hidden" required="false"%>
<%@ attribute name="tagBody" required="false"%>
<%@ attribute name="hiddenValue" required="false"%>
<%@ attribute name="underline" required="false"%>

<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <select class="form-control" id="${id}" name="${id}">
            <jsp:doBody/>
        </select>
        <c:if test="${hidden != null && hidden != '' }">
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}" />
        </c:if>
    </div>
</div>

<c:if test="${underline == 'true' }">
    <div class="hr-line-dashed"></div>
</c:if>


