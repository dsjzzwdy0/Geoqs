/**
 * 角色管理的单例
 */
var Menu = {
    id: "menuTable",	//表格id
};
$(function () {
	InitialPage();
	GetTree();
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
            $("#gridTable").setGridHeight($(window).height() - 136);
            $("#itemTree").setTreeHeight($(window).height() - 46);
        }, 200);
        e.stopPropagation();
    });
}
var _parentId = "0";
//加载树
function GetTree() {
    var item = {
        height: $(window).height() - 46,
        url: Geomonitor.ctxPath+"/menu/tree",
        onnodeclick: function (item) {
            _parentId = item.id;
            $('#btn_Search').trigger("click");
        }
    };
    //初始化
    $("#itemTree").treeview(item);
}
/**
 * 初始化表格
 */
function GetGrid() {
    var selectedRowIndex = 0;
    var $gridTable = $('#gridTable');
    $gridTable.jqGrid({
        url: Geomonitor.ctxPath+"/menu/list",
        datatype: "json",
        height: $(window).height() - 136,
        autowidth: true,
        colModel: [
            { label: "主键", name: "id", index: "id", hidden: true },
            { label: "菜单名称", name: "name", index: "name", width: 150, align: "center" },
            { label: "菜单编号", name: "code", index: "code", width: 150, align: "center" },
            { label: "菜单父编号", name: "pcode", index: "pcode", width: 150, align: "center" },
            { label: "请求地址", name: "url", index: "url", width: 200, align: "center" },
            { label: "是否是菜单", name: "isMenuName", index: "isMenuName", width: 60, align: "center" },
            { label: "排序", name: "num", index: "num", width: 60, align: "center" },
            { label: "层级", name: "levels", index: "levels", width: 60, align: "center" },
            { label: "状态", name: "statusName", index: "statusName", align: "center" },
        ],
        gridview: true,
        viewrecords: true,
        rowNum: "1000",
        pager: false,
        rownumbers: true,
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
            menuName: $("#txt_Keyword").val(),
            pid: _parentId
        }
        $gridTable.jqGrid('setGridParam', {
            url: Geomonitor.ctxPath+"/menu/list",
            postData:queryJson
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
 * 点击添加菜单
 */
Menu.addMenu = function () {
    dialogOpen({
        id: "Form",
        title: '添加菜单',
        url: '/menu/menu_add/'+_parentId,
        width: "450px",
        height: "420px",
        callBack: function (iframeId) {
            top.frames[iframeId].AcceptClick();
        }
    });
};

/**
 * 点击修改
 */
Menu.editMenu = function () {
	var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '修改菜单',
            url: '/menu/menu_edit/' + keyValue,
            width: "450px",
            height: "420px",
            callBack: function (iframeId) {
                top.frames[iframeId].AcceptClick();
            }
        });
    }
};

/**
 * 删除
 */
Menu.delMenu = function () {
	var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.RemoveForm({
            url: Geomonitor.ctxPath + "/menu/remove",
            param: { menuId: keyValue },
            success: function (data) {
                $("#gridTable").trigger("reloadGrid");
            }
        })
    } else {
        dialogMsg('请选择需要删除的菜单！', 0);
    }
};
