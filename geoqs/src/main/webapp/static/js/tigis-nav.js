$(function () {
	$('.menuItem').on('click', menuItem);
    function menuItem() {
        // 获取标识数据
        var dataUrl = $(this).attr('href');
        if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
        $("#mainFrame").attr('src', dataUrl);
        Loading(true);
        $('#mainContent iframe:visible').load(function () {
            Loading(false);
        });
        $(".layui-nav-item").removeClass("layui-this");
        if($(this).parent().prop("tagName")=="LI"){//当前非子菜单
        	$(this).parent().addClass("layui-this");
        }else{//当前是子菜单
        	$(this).parent().parent().parent().addClass("layui-this");
        }
        return false;
    }
});
