<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="placeholder" required="false"%> 

<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                type="button">${name}
        </button>
    </div>
    <input type="text" class="form-control" id="${id}" placeholder="${placeholder}" />
</div>