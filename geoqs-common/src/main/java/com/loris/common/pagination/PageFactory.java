package com.loris.common.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loris.common.constant.state.Order;
import com.loris.common.support.HttpKit;
import com.loris.common.util.ToolUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Table默认的分页参数创建
 *
 * @date 2017-04-05 22:25
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        int curPage = Integer.valueOf(request.getParameter("page"));
        int pageRows = Integer.valueOf(request.getParameter("rows"));
        String sort = request.getParameter("sidx");
        String order = request.getParameter("sord");
        Page<T> page = new Page<>(curPage, pageRows);
        if(ToolUtil.isEmpty(sort)){
            return page;
        }else{
            if(Order.ASC.getDes().equals(order)){
                page.setDesc(sort);
            }else{
                page.setAsc(sort);
            }
            return page;
        }
    }
}
