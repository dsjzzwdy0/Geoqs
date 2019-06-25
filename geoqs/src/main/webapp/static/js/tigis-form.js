$.SaveForm = function (options) {
    var defaults = {
        url: "",
        param: [],
        type: "post",
        dataType: "json",
        loading: "正在处理数据...",
        contentType : 'application/x-www-form-urlencoded',
        success: null,
        close: true
    };
    var options = $.extend(defaults, options);
    Loading(true, options.loading);
    if ($('[name=__RequestVerificationToken]').length > 0) {
        options.param["__RequestVerificationToken"] = $('[name=__RequestVerificationToken]').val();
    }
    window.setTimeout(function () {
        $.ajax({
            url: options.url,
            data: options.param,
            type: options.type,
            dataType: options.dataType,
            contentType:options.contentType,
            success: function (data) {
                if (data.code != 200) {
                    dialogAlert(data.message, -1);
                } else {
                    Loading(false);
                    dialogMsg(data.message, 1);
                    options.success(data);
                    if (options.close == true) {
                        dialogClose();
                    }
                }
            },
            error: function (data,XMLHttpRequest, textStatus, errorThrown) {
                Loading(false);
                if(data.responseJSON && data.responseJSON.message){
                	dialogMsg(data.responseJSON.message+"!", 2);
                }else{
                	dialogMsg("出现异常!", 2);
                }
            },
            beforeSend: function () {
                Loading(true, options.loading);
            },
            complete: function () {
                Loading(false);
            }
        });
    }, 500);
}
$.SetForm = function (options) {
    var defaults = {
        url: "",
        param: [],
        type: "get",
        dataType: "json",
        success: null,
        async: false
    };
    var options = $.extend(defaults, options);
    $.ajax({
        url: options.url,
        data: options.param,
        type: options.type,
        dataType: options.dataType,
        async: options.async,
        success: function (data) {
            if (data != null) {
            	if(data.code== 200){
            		options.success(data.result);
            	}else if(data.code!=null && data.code!=200){
            		dialogAlert(data.message, -1);
            	}else{
            		options.success(data);
            	}
            } else {
            	dialogAlert("请求错误。", -1);
            }
        },
        error: function (data,XMLHttpRequest, textStatus, errorThrown) {
        	dialogMsg(data.responseJSON.message+"!", 2);
        }, beforeSend: function () {
            Loading(true);
        },
        complete: function () {
            Loading(false);
        }
    });
}
$.fn.GetWebControls = function () {
    var reVal = "";
    $(this).find('input,select,textarea,.ui-select').each(function (r) {
        var id = $(this).attr('id');
        var autoget=$(this).attr('lay-autoget');//标记是否自动获取，默认获取
        if(id==undefined || id=="" || autoget=='false'){
        	return true;//相当于continue
        }
        var type = $(this).attr('type');
        var $obj = $(this);
        switch (type) {
            case "checkbox":
                if ($obj.is(":checked")) {
                    reVal += '"' + id + '"' + ':' + '"1",'
                } else {
                    reVal += '"' + id + '"' + ':' + '"0",'
                }
                break;
            case "select":
                var value = $obj.attr('data-value');
                if (value == "") {
                    value = "&nbsp;";
                }
                reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
                break;
            case "selectTree":
                var value = $obj.attr('data-value');
                if (value == "") {
                    value = "&nbsp;";
                }
                reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
                break;
            default:
                var value = $obj.val();
                if (value == "") {
                    if (!$obj.hasClass("input-wdatepicker")) {
                        //非日期控件才设置其值为&nbsp;，否则后台将&nbsp;转为datetime时会报错 xx tigis 2016-11-24
                        value = "&nbsp;";
                    }
                }
                reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
                break;
        }
    });
    reVal = reVal.substr(0, reVal.length - 1);
    reVal = reVal.replace(/&nbsp;/g, '');
    reVal = reVal.replace(/\\/g, '\\\\');
    reVal = reVal.replace(/\n/g, '\\n');
    var postdata = jQuery.parseJSON('{' + reVal + '}');
    //阻止伪造请求
    //if ($('[name=__RequestVerificationToken]').length > 0) {
    //    postdata["__RequestVerificationToken"] = $('[name=__RequestVerificationToken]').val();
    //}
    return postdata;
};
$.fn.SetWebControls = function (data) {
    var $id = $(this)
    for (var key in data) {
        var id = $id.find('#' + key);
        if (id.attr('id')) {
            var type = id.attr('type');
            if (id.hasClass("input-datepicker")) {
                type = "datepicker";
            }
            var value = $.trim(data[key]).replace(/&nbsp;/g, '');
            switch (type) {
                case "checkbox":
                    if (value == 1) {
                        id.attr("checked", 'checked');
                    } else {
                        id.removeAttr("checked");
                    }
                    break;
                case "select":
                    id.ComboBoxSetValue(value);
                    break;
                case "selectTree":
                    id.ComboBoxTreeSetValue(value);
                    break;
                case "datepicker":
                    id.val(formatDate(value, 'yyyy-MM-dd'));
                    break;
                default:
                    id.val(value);
                    break;
            }
        }
    }
}
$.ConfirmAjax = function (options) {
    var defaults = {
        msg: "提示信息",
        loading: "正在处理数据...",
        url: "",
        param: [],
        type: "post",
        dataType: "json",
        success: null
    };
    var options = $.extend(defaults, options);
    dialogConfirm(options.msg, function (r) {
        if (r) {
            Loading(true, options.loading);
            window.setTimeout(function () {
                var postdata = options.param;
                if ($('[name=__RequestVerificationToken]').length > 0) {
                    postdata["__RequestVerificationToken"] = $('[name=__RequestVerificationToken]').val();
                }
                $.ajax({
                    url: options.url,
                    data: postdata,
                    type: options.type,
                    dataType: options.dataType,
                    success: function (data) {
                        Loading(false);
                        if (data.code != 200) {
                        	dialogAlert(data.message, -1);
                        } else {
                        	dialogMsg(data.message, 1);
                            options.success(data);
                        }
                    },
                    error: function (data,XMLHttpRequest, textStatus, errorThrown) {
                        Loading(false);
                        dialogMsg(data.responseJSON.message+"!", 2);
                    },
                    beforeSend: function () {
                        Loading(true, options.loading);
                    },
                    complete: function () {
                        Loading(false);
                    }
                });
            }, 200);
        }
    });
}
//执行ajax方法        
$.ExcuteAjax = function (options) {
    var defaults = {
        msg: "提示信息",
        loading: "正在处理数据...",
        url: "",
        param: [],
        type: "post",
        dataType: "json",
        isOkInfo: true,
        isAlertInfo:true,
        success: null
    };
    var options = $.extend(defaults, options);
    var postdata = options.param;
    $.ajax({
        url: options.url,
        data: postdata,
        type: options.type,
        dataType: options.dataType,
        isOkInfo: options.isOkInfo,
        success: function (data) {
            Loading(false);
            if (data.code != 200) {
                if (data.isAlertInfo) {
                    dialogAlert(data.message, -1);
                }
            } else {
                if (data.isOkInfo) {
                    dialogMsg(data.message, 1);
                }                
                options.success(data);
            }
        },
        error: function (data,XMLHttpRequest, textStatus, errorThrown) {
            Loading(false);
            dialogMsg(data.responseJSON.message+"!", 2);
        },
        beforeSend: function () {
            Loading(true, options.loading);
        },
        complete: function () {
            Loading(false);
            options.complete();
        }
    });
}

