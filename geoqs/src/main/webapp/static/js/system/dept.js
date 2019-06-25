/**
 * 单位管理的单例
 */
var Dept = {
    
};
layui.config({
	base: 'static/layui-extend/',
})
//加载layui的form模块
layui.use('form', function(){
	     
});
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
    	url: Geomonitor.ctxPath+"/dept/list",
        datatype: "json",
        height: $(window).height() - 165,
        autowidth: true,
        colModel: [
            { label: "主键", name: "id", index: "id", hidden: true },
            { label: "账号", name: "account", index: "account", width: 100, align: "center" ,sortable:false},
            { label: "单位名称", name: "fullname", index: "fullname", width: 250, align: "center",sortable:false },
            { label: "法人", name: "faren", index: "faren", width: 100, align: "center",sortable:false },
            { label: "联系人", name: "contact", index: "contact", width: 100, align: "center",sortable:false},
            { label: "联系电话", name: "contacttel", index: "contacttel", width: 120, align: "center",sortable:false },
            { label: "注册状态", name: "regstatus", index: "regstatus", width: 100,align: "center",sortable:false,
            	cellattr: function (rowId, cellvalue, rowObject, cm, rdata) {
                    if (rdata.regstatus == 0) {
                        return " style='background-color:#ec971f;color:#fff;'";
                    } else if (rdata.regstatus == 1) {
                        return " style='background-color:#5FB878;color:#fff;'";
                    } else if (rdata.regstatus == 2) {
                        return " style='background-color:#d9534f;color:#fff;'";
                    } else {
                        return '';
                    }
                },
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == 0) {
                        return '注册申请';
                    } else if (cellvalue == 1) {
                        return '审核通过';
                    } else {
                        return '审核不通过';
                    }
                }
            },
            { label: "使用状态", name: "status", index: "status", width: 100,align: "center",sortable:false,
            	cellattr: function (rowId, cellvalue, rowObject, cm, rdata) {
                    if (rdata.status == 1) {
                        return " style='background-color:#1E9FFF;color:#fff;'";
                    } else if (rdata.status == 2) {
                        return " style='background-color:#c2c2c2;color:#fff;'";
                    } else if (rdata.status == 3) {
                        return " style='background-color:#FF5722;color:#fff;'";
                    } else {
                        return '';
                    }
                },
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == 1) {
                        return '正常';
                    } else if (cellvalue == 2) {
                        return '已冻结';
                    }else if (cellvalue == 3){
                    	return '已删除';
                    } 
                    else {
                        return '';
                    }
                }	
            },
            { label: "单位类型", name: "typename", index: "typename", width: 100, align: "center",sortable:false }
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
            condition: $("#txt_Keyword").val(),
            unittype: $("#unittype").val()
        }
        $gridTable.jqGrid('setGridParam', {
            url:Geomonitor.ctxPath+ "/dept/list",
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
 * 查看详情
 */
Dept.detail = function () {
	var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '单位详情',
            url: '/dept/dept_detail/' + keyValue,
            width: "450px",
            height: "420px",
            callBack: function (iframeId) {
                top.frames[iframeId].AcceptClick();
            }
        });
    }
};
