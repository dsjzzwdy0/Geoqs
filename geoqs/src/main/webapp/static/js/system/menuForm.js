//加载layui的form模块，否则select元素不会渲染
layui.use('form', function(){
	 var form = layui.form;
	//监听提交
	  form.on('submit(hiddenbtn)', function(data){
	    return false;
	 });
});
//保存表单
function AcceptClick() {
	$("#hiddenbtn").trigger("click");
    if ($('input.layui-form-danger').length>0) {
        return false;
    }
    var postData = $("#form1").GetWebControls();
    var url=Geomonitor.ctxPath+"/menu/add";
    if(postData.id){
    	url=Geomonitor.ctxPath+"/menu/edit";//有id则为修改
    }
    $.SaveForm({
        url: url,
        param: postData,
        loading: "正在保存数据...",
        success: function (data) {
            $.currentIframe().$("#gridTable").trigger("reloadGrid");
        }
    })
}
$(function() {
	//上级
    $("#pcode").ComboBoxTree({
        url: Geomonitor.ctxPath+"/menu/selectMenuTreeList",
        height: "195px",
        allowSearch: false
    });
    $("#pcode").ComboBoxTreeSetValue($("#pcodevalue").val());
});
