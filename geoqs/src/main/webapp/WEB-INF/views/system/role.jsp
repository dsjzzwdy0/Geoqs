<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tigis.geoqs.security.ShiroKit" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="qs" uri="/WEB-INF/taglib/function.tld"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>角色管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="col-sm-3">
                                <fns:NameCon id="roleName" name="角色名称" />
                            </div>
                            <div class="col-sm-3">
                                <fns:button name="搜索" icon="fa-search" clickFun="Role.search()"/>
                            </div>
                        </div>
                        <div class="hidden-xs" id="roleTableToolbar" role="group">
                        <c:if test="${qs:hasPermission('/role/add')}">
							<fns:button name="添加" icon="fa-plus" clickFun="Role.openAddRole()" />
                        </c:if>
                       	<c:if test="${qs:hasPermission('/role/edit')}">
                            <fns:button name="修改" icon="fa-edit" clickFun="Role.openChangeRole()" space="true"/>
                        </c:if>
                        <c:if test="${qs:hasPermission('/role/remove')}">
                            <fns:button name="删除" icon="fa-remove" clickFun="Role.delRole()" space="true"/>
                        </c:if>
                        <c:if test="${qs:hasPermission('/role/setAuthority')}">
                             <fns:button name="权限配置" icon="fa-user-secret" clickFun="Role.assign()" space="true"/>
                        </c:if>
                        </div>
                        <fns:table id="roleTable"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxPath}/content/modular/system/role/role.js"></script>
