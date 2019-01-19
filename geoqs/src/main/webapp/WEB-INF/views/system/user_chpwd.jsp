<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="col-sm-4  col-sm-offset-4">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>修改密码</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-content" style="border:none !important; ">
                            <div class="form-horizontal">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <fns:input id="oldPwd" name="原密码" underline="true" type="password"/>
                                        <fns:input id="newPwd" name="新密码" underline="true" type="password"/>
                                        <fns:input id="rePwd" name="新密码验证" type="password"/>
                                    </div>
                                </div>
                                <div class="row btn-group-m-t">
                                    <div class="col-sm-10">
                                        <fns:button btnCss="info" name="提交" id="ensure" icon="fa-check" clickFun="UserInfoDlg.chPwd()"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxPath}/content/modular/system/user/user_info.js"></script>
