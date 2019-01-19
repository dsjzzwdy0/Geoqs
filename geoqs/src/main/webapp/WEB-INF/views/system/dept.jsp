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
                <h5>部门管理</h5>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="col-sm-3">
                                <fns:NameCon id="condition" name="名称" />
                            </div>
                            <div class="col-sm-3">
                                <fns:button name="搜索" icon="fa-search" clickFun="Dept.search()"/>
                            </div>
                        </div>
                        <div class="hidden-xs" id="DeptTableToolbar" role="group">
                        <c:if test="${qs:hasPermission('/dept/add')}">
                                <fns:button name="添加" icon="fa-plus" clickFun="Dept.openAddDept()"/>
                        </c:if>
                        <c:if test="${qs:hasPermission('/dept/update')}">
                                <fns:button name="修改" icon="fa-plus" clickFun="Dept.openDeptDetail()" space="true"/>
                        </c:if>
                        <c:if test="${qs:hasPermission('/dept/delete')}">
                                <fns:button name="删除" icon="fa-plus" clickFun="Dept.delete()" space="true"/>
                        </c:if>
                        </div>
                        <fns:table id="DeptTable"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxPath}/content/modular/system/dept/dept.js"></script>

