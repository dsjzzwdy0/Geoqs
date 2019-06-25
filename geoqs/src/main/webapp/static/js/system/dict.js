/**
 * 字典管理的单例
 */
var Dict = {
    
};
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
    var $gridTable = $('#gridTable');
    $gridTable.jqGrid({
    	url: Geomonitor.ctxPath+"/dict/list",
        datatype: "json",
        height: $(window).height() - 165,
        autowidth: true,
        colModel: [
            { label: "主键", name: "id", index: "id", hidden: true },
            { label: "字典类别", name: "name", index: "name", width: 150, align: "center" },
            { label: "字典编码", name: "code", index: "code", width: 150, align: "center" },
            { label: "字典值域", name: "detail", index: "detail", width: 400},
            { label: "备注", name: "tips", index: "tips" } 
        ],
        viewrecords: true,
        rowNum: 20,
        rowList: [15,20,50, 100],
        pager: "#gridPager",
        shrinkToFit: true,
        rownumbers: true,
        gridview: true,
        loadError: function (xhr,status,error) {
        	dialogAlert(xhr.responseJSON.message + "!");
        }
    });
    //查询事件
    $("#btn_Search").click(function () {
        var queryJson = {
            condition: $("#txt_Keyword").val()
        }
        $gridTable.jqGrid('setGridParam', {
            url: Geomonitor.ctxPath+"/dict/list",
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
 * 点击添加
 */
Dict.add = function () {
    dialogOpen({
        id: "Form",
        title: '添加字典',
        url: '/dict/dict_add',
        width: "550px",
        height: "400px",
        callBack: function (iframeId) {
            top.frames[iframeId].DictInfoDlg.addSubmit();
        }
    });
};

/**
 * 点击修改
 */
Dict.edit = function () {
	var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '修改字典',
            url: '/dict/dict_edit/' + keyValue,
            width: "550px",
            height: "400px",
            callBack: function (iframeId) {
                top.frames[iframeId].DictInfoDlg.addSubmit();
            }
        });
    }
};

/**
 * 删除
 */
Dict.del = function () {
	var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.RemoveForm({
            url: Geomonitor.ctxPath + "/dict/delete",
            param: { dictId: keyValue },
            success: function (data) {
                $("#gridTable").trigger("reloadGrid");
            }
        })
    } else {
        dialogMsg('请选择需要删除的字典！', 0);
    }
};
