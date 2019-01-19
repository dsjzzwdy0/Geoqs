<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="container-fluid" style="padding: 0 !important;">
	<div class="row">
		<div class="col-sm-12">
		<c:forEach items="${noticeList}" var="notice">
			<div class="alert alert-success alert-dismissable">
				<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
					${notice.content}
			</div>
		</c:forEach>
		</div>
	</div>
</div>
