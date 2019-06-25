/**
 * 用户管理的单例
 */
var User = {

};
//加载layui的form模块
layui.use('form', function(){
	     
});
$(function () {
	InitialPage();
	GetGrid();
});
/**
 * 初始化表格
 */
function GetGrid() {
    var selectedRowIndex = 0;
    var $gridTable = $('#gridTable');
    $gridTable.jqGrid({
    	url: Geomonitor.ctxPath+"/mgr/list",
    	//url: Geomonitor.ctxPath+"/mgr/billionlist",//百万数据测试
        datatype: "json",
        height: $(window).height() - 165,
        autowidth: true,
        colModel: [
            { label: "主键", name: "id", index: "id", hidden: true },
            { label: "账号", name: "account", index: "account", width: 100, align: "center" },
            { label: "姓名", name: "realname", index: "realname", width: 150, align: "center" },
            { label: "所属单位", name: "deptname", index: "deptname", width: 180, align: "center" },
            { label: "性别", name: "gender", index: "gender", width:60, align: "center",
            	formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == 0) {
                        return '男';
                    } else if (cellvalue == 1) {
                        return '女';
                    } else {
                        return '--';
                    }
                }
            },
            { label: "邮箱", name: "email", index: "email", width: 160, align: "center" },
            { label: "电话", name: "tel", index: "tel",width: 100,  align: "center" },
            { label: "创建时间", name: "createtime", index: "createtime",width: 80,  align: "center" },
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
            { label: "人员角色", name: "rolename", index: "rolename", width: 250, align: "center" }
        ],
        viewrecords: true,
        rowNum: 20,
        rowList: [15,20,50, 100],
        pager: "#gridPager",
        shrinkToFit: true,
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
            deptId: $("#deptId").val(),
            personType: $("#personType").val()
        }
        $gridTable.jqGrid('setGridParam', {
            url: Geomonitor.ctxPath+"/mgr/list",
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
 * 点击添加管理员
 */
User.openAddMgr = function () {
    dialogOpen({
        id: "Form",
        title: '添加管理员',
        url: '/mgr/user_add',
        width: "800px",
        height: "560px",
        callBack: function (iframeId) {
            top.frames[iframeId].AcceptClick();
        }
    });
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
User.openChangeUser = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '编辑人员',
            url: '/mgr/user_edit/' + keyValue,
            width: "800px",
            height: "450px",
            callBack: function (iframeId) {
                top.frames[iframeId].AcceptClick();
            }
        });
    }
};

/**
 * 点击角色分配
 * @param
 */
User.roleAssign = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (checkedRow(keyValue)) {
        dialogOpen({
            id: "Form",
            title: '角色分配',
            url: '/mgr/role_assign/' + keyValue,
            width: "300px",
            height: "400px",
            callBack: function (iframeId) {
                top.frames[iframeId].AcceptClick();
            }
        });
    }
};

/**
 * 删除用户
 */
User.delMgrUser = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.RemoveForm({
            url: Geomonitor.ctxPath + "/mgr/delete",
            param: { userId: keyValue },
            success: function (data) {
                $("#gridTable").trigger("reloadGrid");
            }
        })
    } else {
        dialogMsg('请选择需要删除的人员！', 0);
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
User.freezeAccount = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.ConfirmAjax({
        	msg:"是否确定冻结当前账户？",
            url: Geomonitor.ctxPath + "/mgr/freeze",
            param: { userId: keyValue },
            success: function (data) {
                $("#gridTable").trigger("reloadGrid");
            }
        })
    } else {
        dialogMsg('请选择需要冻结的人员！', 0);
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
User.unfreeze = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.ConfirmAjax({
        	msg:"是否确定解除冻结当前账户？",
            url: Geomonitor.ctxPath + "/mgr/unfreeze",
            param: { userId: keyValue },
            success: function (data) {
                $("#gridTable").trigger("reloadGrid");
            }
        })
    } else {
        dialogMsg('请选择需要解除冻结的人员！', 0);
    }
}

/**
 * 重置密码
 */
User.resetPwd = function () {
    var keyValue = $("#gridTable").jqGridRowValue("id");
    if (keyValue) {
        $.ConfirmAjax({
        	msg:"是否重置当前用户密码为111111？？",
            url: Geomonitor.ctxPath + "/mgr/reset",
            param: { userId: keyValue },
            success: function (data) {               
            }
        })
    } else {
        dialogMsg('请选择需要重置密码的人员！', 0);
    }
};