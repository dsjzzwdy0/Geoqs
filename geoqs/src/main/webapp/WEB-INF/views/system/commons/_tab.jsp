<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="nav-header">
				<div class="dropdown profile-element">
					<span>
					<img alt="image" class="img-circle"
						<c:if test="${avatar == null}">
                             src="${ctxPath}/content/img/girl.gif"
						</c:if>
						<c:if test="${avatar != null}">
                             src="${ctxPath}/content/img/${avatar}"
						</c:if>
                             width="64px" height="64px" />
                    </span> 
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
                    <span class="clear"> 
                       	<span class="block m-t-xs"><strong class="font-bold">${shiro.getUser().name}</strong></span> 
                       	<span class="text-muted text-xs block">${shiro.getUser().roleNames[0]}<b class="caret"></b></span>
					</span>
					</a>
					<ul class="dropdown-menu m-t-xs">
						<li><a class="J_menuItem" href="${ctxPath}/mgr/user_info">个人资料</a></li>
						<li><a class="J_menuItem" href="${ctxPath}/mgr/user_chpwd">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="${ctxPath}/logout">安全退出</a></li>
					</ul>
				</div>
				<div class="logo-element">GS</div>
			</li> 
			<c:forEach var="title" items="${titles }">
				<c:if test="${title.children == null}">
					<li>
						<a class="J_menuItem" href="${ctxPath}${title.url}" name="tabMenuItem"> <i class="fa ${title.icon}"></i> 
						<span class="nav-label">${title.name}</span>
						</a>
					</li>
				</c:if>
				<c:if test="${title.children != null }">
					<li>
						<a href="#"> <i class="fa ${title.icon}"></i> 
							<span class="nav-label">${title.name}</span>
							<span class="fa arrow"></span>
						</a>
						<ul class="nav nav-second-level">
						<c:forEach var="subTitle" items="${title.children}">
							<c:if test="${subTitle.children == null }">
								<li>
								<a class="J_menuItem" href="${ctxPath}${subTitle.url}" name="tabMenuItem">${subTitle.name}</a></li> 
							</c:if>
							<c:if test="${subTitle.children != null }">
								<li><a href="#">${subTitle.name} <span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
								<c:forEach var="thirdTitle" items="${subTitle.children}">
									<li><a class="J_menuItem" href="${ctxPath}${thirdTitle.url}" name="tabMenuItem">${thirdTitle.name}</a></li> 
								</c:forEach>
								</ul>
								</li> 
							</c:if> 
						</c:forEach>
						</ul>
					</li> 
				</c:if>
			</c:forEach>
		</ul>
	</div>
</nav>