/**
 * 人员注册使用
 */
layui.config({
	base: 'static/layui-extend/',
})
//加载layui的模块
layui.use(['form','multiSelect','upload'], function(){
  var upload = layui.upload
    , form = layui.form;
	//事件监听
	  form.on('submit(btn_Regedit)', function(data){
		  AcceptClick();
	 });

	  form.on('checkbox(agree_checkbox)', function(data){
		  if(data.elem.checked){
			  $("#btn_Regedit").removeClass("layui-btn-disabled").addClass("layui-btn-normal");
			  $("#btn_Regedit").removeAttr('disabled');
		  }else{
			  $("#btn_Regedit").removeClass("layui-btn-normal").addClass("layui-btn-disabled");
			  $("#btn_Regedit").attr('disabled','disabled');
		  }
	});       
  //普通图片上传
  var uploadInst = upload.render({
    elem: '#uploadFarenCard',
    url: '/upload/',
    acceptMime: 'image/*',
    drag:false,
    before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
        $('#demo1').attr('src', result); //图片链接（base64）
      });
    },
    done: function(res){
      //如果上传失败
      if(res.code > 0){
        return dialogAlert('上传失败');
      }
      //上传成功
    },
    error: function(){
      //演示失败状态，并实现重传
      var demoText = $('#demoText');
      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
      demoText.find('.demo-reload').on('click', function(){
        uploadInst.upload();
      });
    }
  });
})
//保存表单
function AcceptClick() {
    if ($('input.layui-form-danger').length>0) {
        return false;
    }
    var user={
    		account:$("#account").val(),
    		password:$("#password").val(),
    		rePassword:$("#rePassword").val()
    }
    if(user.password != user.rePassword){
		dialogAlert("两次密码输入不一致。");
		return false;
	}
    var roleCodes = [];
	$('select[multiple] option:selected').each(function() {
		roleCodes.push($(this).val());
	})
	if(roleCodes.length==0){
		dialogAlert("人员类型必须填写。");
		return false;
	}
    var person = $("#form1").GetWebControls();
    var url=contentPath+"/doRegisterPerson";
    $.SaveForm({
        url: url,
        param: JSON.stringify({
        	person:person,
        	user:user,
        	rolecodes:roleCodes.join(',')
        }),
        contentType : 'application/json;charset=utf-8',
        loading: "正在保存数据...",
        success: function (data) {
        	dialogAlert("个人账号【"+user.account+"】注册成功，请等待审核通过。",1,function(){top.location.href=contentPath+"/login"});  
        }
    })
}
