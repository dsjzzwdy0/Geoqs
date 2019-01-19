<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="ibox float-e-margins">
	<div class="ibox-content">
		<div class="form-horizontal" id="roleInfoForm">
		
			<input type="hidden" id="id" value="">

			<div class="row">
				<div class="col-sm-6 b-r">
					<fns:input id="name" name="角色名称" underline="true"/>
					<fns:input id="pName" name="上级名称" underline="true" hidden="pid" readonly="readonly"
							clickFun="RolInfoDlg.showPNameSelectTree(); return false;"
							style="background-color: #ffffff !important;"/>
					<fns:input id="deptName" name="部门名称" hidden="deptid" readonly="readonly"
							clickFun="RolInfoDlg.showDeptSelectTree(); return false;"
							style="background-color: #ffffff !important;"/>
				</div>
				<div class="col-sm-6">
					<fns:input id="tips" name="别名" underline="true"/>
					<fns:input id="num" name="排序"/>
				</div>
			</div>

			<!-- 这是部门下拉框 -->
			<div id="deptContent" class="menuContent"
				style="display: none; position: absolute; z-index: 200;">
				<ul id="deptTree" class="ztree tree-box" style="width: 250px !important;"></ul>
			</div>
			
			<!-- 这是父级菜单下拉框 -->
			<div id="pNameContent" class="menuContent"
				style="display: none; position: absolute; z-index: 200;">
				<ul id="pNameTree" class="ztree tree-box" style="width: 250px !important;"></ul>
			</div>

			<div class="row btn-group-m-t">
				<div class="col-sm-10">
					<fns:button btnCss="info" name="提交" id="ensure" icon="fa-check" clickFun="RolInfoDlg.addSubmit()"/>
					<fns:button btnCss="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="RolInfoDlg.close()"/>
				</div>
			</div>
		</div>

	</div>
</div>
<script src="${ctxPath}/content/modular/system/role/role_info.js"></script>
