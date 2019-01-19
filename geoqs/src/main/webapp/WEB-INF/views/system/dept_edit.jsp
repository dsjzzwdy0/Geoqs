<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tigis.geoqs.security.ShiroKit" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="form-horizontal" id="deptInfoForm">

            <input type="hidden" id="id" value="${dept.id}">

            <div class="row">
                <div class="col-sm-6 b-r">
                    <fns:input id="simplename" name="部门名称" underline="true" value="${dept.simplename}"/>

                    <fns:input id="fullname" name="部门全称" underline="true" value="${dept.fullname}"/>

                    <fns:input id="tips" name="备注" underline="true" value="${dept.tips}"/>
                </div>
                <div class="col-sm-6">
                    <fns:input id="num" name="排序" underline="true" value="${dept.num}"/>

                    <fns:input id="pName" name="上级部门" readonly="readonly" hidden="pid"
                            hiddenValue="${dept.pid}" value="${pName}"
                            clickFun="DeptInfoDlg.showDeptSelectTree(); return false;"
                            style="background-color: #ffffff !important;"/>
                </div>
            </div>

            <!-- 父级部门的选择框 -->
            <div id="parentDeptMenu" class="menuContent"
                 style="display: none; position: absolute; z-index: 200;">
                <ul id="parentDeptMenuTree" class="ztree tree-box" style="width: 245px !important;"></ul>
            </div>

            <div class="row btn-group-m-t">
                <div class="col-sm-10">
                    <fns:button btnCss="info" name="提交" id="ensure" icon="fa-check" clickFun="DeptInfoDlg.editSubmit()"/>
                    <fns:button btnCss="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="DeptInfoDlg.close()"/>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="${ctxPath}/content/modular/system/dept/dept_info.js"></script>

