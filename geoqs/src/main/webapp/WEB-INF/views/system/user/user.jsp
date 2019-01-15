<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="row">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>用户管理</h5>
			</div>
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-sm-12">
						<div class="row">
							<div class="col-lg-2 col-sm-3">
								<div class="panel panel-default">
									<div class="panel-heading">组织机构</div>
									<div class="panel-body dept-tree">
										<ul id="deptTree" class="ztree"></ul>
									</div>
								</div>
							</div>
							<div class="col-lg-10 col-sm-9">
								<div class="row">
									<div class="col-lg-10 col-sm-9">
										<div class="row">
											<div class="col-lg-4 col-sm-12">
												<fns:NameCon id="name" name="用户名称" placeholder="帐号/姓名/手机号"/>
											</div>
											<div class="col-lg-4 col-sm-6">
												<fns:TimeCon id="beginTime" name="注册开始日期" isTime="false" pattern="YYYY-MM-DD"/>
											</div>
											<div class="col-lg-4 col-sm-6">
												<fns:TimeCon id="endTime" name="注册结束日期" isTime="false" pattern="YYYY-MM-DD"/>
											</div>
										</div>
									</div>
									<div class="col-lg-2 col-sm-3">
										<div class="row">
											<div class="col-lg-12 col-sm-12">
												<#button name="搜索" icon="fa-search" clickFun="MgrUser.search()"/>
												<#button name="重置" icon="fa-trash" clickFun="MgrUser.resetSearch()" space="true"/>
											</div>
										</div>
									</div>
								</div>
								<div class="hidden-xs" id="managerTableToolbar" role="group">
									@if(shiro.hasPermission("/mgr/add")){
									<#button name="添加" icon="fa-plus" clickFun="MgrUser.openAddMgr()"/>
									@}
									@if(shiro.hasPermission("/mgr/edit")){
									<#button name="修改" icon="fa-edit" clickFun="MgrUser.openChangeUser()" space="true"/>
									@}
									@if(shiro.hasPermission("/mgr/delete")){
									<#button name="删除" icon="fa-remove" clickFun="MgrUser.delMgrUser()" space="true"/>
									@}
									@if(shiro.hasPermission("/mgr/reset")){
									<#button name="重置密码" icon="fa-refresh" clickFun="MgrUser.resetPwd()" space="true"/>
									@}
									@if(shiro.hasPermission("/mgr/freeze")){
									<#button name="冻结" icon="fa-warning" clickFun="MgrUser.freezeAccount()" space="true"/>
									@}
									@if(shiro.hasPermission("/mgr/unfreeze")){
									<#button name="解除冻结" icon="fa-check-circle" clickFun="MgrUser.unfreeze()" space="true"/>
									@}
									@if(shiro.hasPermission("/mgr/setRole")){
									<#button name="角色分配" icon="fa-user-secret" clickFun="MgrUser.roleAssign()" space="true"/>
									@}
								</div>
								<#table id="managerTable"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctxPath}/content/modular/system/user/user.js"></script>
