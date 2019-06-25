/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  UserController.java   
 * @Package com.loris.soccer.controller   
 * @Description: 本项目用于天津www.tigis.com.cn数据的存储、共享、处理等   
 * @author: www.tigis.com.cn    
 * @date:   2019年1月28日 下午8:38:55   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津www.tigis.com.cn有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.loris.common.web.BaseController;

/**   
 * @ClassName:  UserController   
 * @Description: 用户信息管理控制器类 
 * @author: www.tigis.com.cn
 * @date:   2019年1月28日 下午8:38:55   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院www.tigis.com.cn内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController
{	
	/**
	 * 主页登录
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("user", "no");
		return "/login";
	}
	
	@RequestMapping("/test/{page}")
	public String test(@PathVariable String page, Model model)
	{
		model.addAttribute("user", "TestUser");
		model.addAttribute("page", page);
		return "/beetl/test";
	}
	@RequestMapping("/index")
	public String index(Model model)
	{
		model.addAttribute("user", "TestUser");
		return "/beetl/index";
	}
}
