<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="pattern" required="true"%>
<%@ attribute name="isTime" required="true"%> 

<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                type="button">${name}
        </button>
    </div>
    <input type="text" class="form-control layer-date"
           onclick="laydate({istime: ${isTime}, format: '${pattern}'})" id="${id}"/>
</div>