<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="avatarImg" required="false"%>
<%@ attribute name="underline" required="false"%>

<div class="form-group">
    <label class="col-sm-3 control-label head-scu-label">${name}</label>
    <div class="col-sm-4">
        <div id="${id}PreId">
            <div><img width="100px" height="100px"
                <c:if test="${avatarImg == null || avatarImg == '' }">
                	src="${ctxPath}/content/img/girl.gif"></div>
                </c:if>
                <c:if test="${avatarImg != null && avatarImg != '' }">
              		src="${ctxPath}/kaptcha/${avatarImg}"></div>
                </c:if>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="head-scu-btn upload-btn" id="${id}BtnId">
            <i class="fa fa-upload"></i>&nbsp;上传
        </div>
    </div>
    <input type="hidden" id="${id}" value="${avatarImg!}"/>
</div>

<c:if test="${underline=='true'}">
	<div class="hr-line-dashed"></div>
</c:if>



