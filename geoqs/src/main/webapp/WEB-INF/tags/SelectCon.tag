<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="tagBody" required="false"%>

<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">
            ${name}
        </button>
    </div>
    <select class="form-control" id="${id}">
        ${tagBody}
    </select>
</div>