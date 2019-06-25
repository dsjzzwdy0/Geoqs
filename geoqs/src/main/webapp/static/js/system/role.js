/**
 * 角色管理的单例
 */
var Role = {
    id: "roleTable"	//表格id
};
//jquery初始化
$(function () {
	InitialPage();
	GetGrid();
});
//初始化页面
function InitialPage() {
	//layout布局
    $('#layout').layout({
        applyDemoStyles: true,
        onresize: function () {
            $(window).resize()
        }
    });
    //resize重设(表格、树形)宽高
    $(window).resize(function (e) {
        window.setTimeout(function () {
            $('#gridTable').setGridWidth(($('.gridPanel').width()));
            $("#gridTable").setGridHeight($(window).height() - 165);
        }, 200);
        e.stopPropagation();
    });
}
/**
 * 初始化表格
 */
function GetGrid() {
    var selectedRowIndex = 0;
    var $gridTable = $('#gridTable');
    $gridTable.jqGrid({
    	url: Geomonitor.ctxPath+"/role/list",
        datatype: "json",
        height: $(window).height() - 165,
        autowidth: true,
        colModel: [
            { label: "主键", name: "id", index: "id", hidden: true },
            { label: "角色名称", name: "name", index: "name", width: 200, align: "center" ,sortable:false},
            { label: "编号", name: "code", index: "code", width: 150, align: "center",sortable:false },
            { label: "角色类型", name: "type", index: "type", width: 150, align: "center",sortable:false,
            	formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == 0) {
                        return '系统角色';
                    } else if (cellvalue == 1) {
                        return '用户注册角色';
                    } else {
                        return '--';
                    }
                }
            },
            { label: "别名", name: "tips", index: "tips", width: 150, align: "center",sortable:false },
            { label: "排序", name: "num", index: "num", width: 80, align: "center",sortable:false },
            { label: "角色描述", name: "bz", index: "bz", width: 250, align: "center",sortable:false} 
        ],
        viewrecords: true,
        rowNum: 20,
        rowList: [15,20,50, 100],
        pager: "#gridPager",
        shrinkToFit: false,
        rownumbers: true,
        gridview: true,
        onSelectRow: function () {
            selectedRowIndex = $("#" + this.id).getGridParam('selrow');
        },
        gridComplete: function () {
            $("#" + this.id).setSelection(selectedRowIndex, false);
        },
        loadError: function (xhr,status,error) {
        	dialogAlert(xhr.responseJSON.message + "!");
        }
    });
    //查询事件
    $("#btn_Search").click(function () {
        var queryJson = {
        	roleName: $("#txt_Keyword").val()
        }
        $gridTable.jqGrid('setGridParam', {
            url:Geomonitor.ctxPath+ "/role/list",
            postData: queryJson
        }).trigger('reloadGrid');
    });
    //查询回车
    $('#txt_Keyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            $('#btn_Search').trigger("click");
        }
    });
}

/**
 * 点击添加管理员
 */
Role.openAddRole = function () {
	dialogOpen({
        id: "Form",
        title: '添加角色',
        url: '/role/role_add',
        width: "450px",
        height: "450px",
        callBack: function (iframeId) {
            top.frames[iframeId].AcceptClick();
        }
    });
};

/**
 * 点击修改按钮时
 */
Role.openChangeRole = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '修改角色',
            url: '/role/role_edit/'+keyValue,
            width: "450px",
            height: "450px",
            callBack: function (iframeId) {
                top.frames[iframeId].AcceptClick();
            }
        });
    }
};

/**
 * 删除角色
 */
Role.delRole = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.RemoveForm({
            url: Geomonitor.ctxPath + "/role/remove",
            param: { roleId: keyValue },
            success: function (data) {
                $("#gridTable").trigger("reloadGrid");
            }
        })
    } else {
        dialogMsg('请选择需要删除的角色！', 0);
    }
};

/**
 * 权限配置
 */
Role.assign = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '权限配置',
            url: '/role/role_assign/'+keyValue,
            width: "450px",
            height: "420px",
            callBack: function (iframeId) {
                top.frames[iframeId].AcceptClick();
            }
        });
    }
};
