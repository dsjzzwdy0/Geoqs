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
                <h5>菜单管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="col-sm-3">
                                <fns:NameCon id="menuName" name="菜单名称" />
                            </div>
                            <div class="col-sm-3">
                                <fns:NameCon id="level" name="层级" />
                            </div>
                            <div class="col-sm-3">
                                <fns:button name="搜索" icon="fa-search" clickFun="Menu.search()"/>
                            </div>
                        </div>
                        <div class="hidden-xs" id="menuTableToolbar" role="group">
                        <c:if test="${qs:hasPermission('/menu/add')}">
                            <fns:button name="添加" icon="fa-plus" clickFun="Menu.openAddMenu()"/>
                        </c:if>
                        <c:if test="${qs:hasPermission('/menu/edit')}">
                            <fns:button name="修改" icon="fa-edit" clickFun="Menu.openChangeMenu()" space="true"/>
                        </c:if>
                        <c:if test="${qs:hasPermission('/menu/remove')}">
                            <fns:button name="删除" icon="fa-remove" clickFun="Menu.delMenu()" space="true"/>
                        </c:if>
                        </div>
                        <fns:table id="menuTable"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxPath}/content/modular/system/menu/menu.js"></script>
