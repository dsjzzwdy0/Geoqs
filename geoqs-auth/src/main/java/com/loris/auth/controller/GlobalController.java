/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  GlobalController.java   
 * @Package com.loris.common.web   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**   
 * @ClassName:  GlobalController    
 * @Description: 通用控制器   
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/global")
public class GlobalController
{
	/**
     * 跳转到404页面
     *
     * @author fengshuonan
     */
    @RequestMapping(path = "/notfound")
    public String notFoundPage() {
        return "/404";
    }
    /**
     * 跳转到500页面
     *
     * @author fengshuonan
     */
    @RequestMapping(path = "/error")
    public String errorPage() {
        return "/500";
    }
    /**
     * 跳转到session超时页面
     *
     * @author fengshuonan
     */
    @RequestMapping(path = "/sessionError")
    public String errorPageInfo(Model model) 
    {
        model.addAttribute("tips", "session超时");
        return "/login";
    }
}
