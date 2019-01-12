package com.tigis.geoqs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tigis.geoqs.sys.model.User;


/**
 * 用户控件基础
 * 
 * @author deng
 *
 */
@Controller
@RequestMapping("/user")
public class UserController
{
	/**
	 * 系统登录
	 * 
	 * @return
	 */
	@RequestMapping("login")
	public String login()
	{
		return "login";
	}

	@ResponseBody
	@RequestMapping("/read")
	public User readUser(String name)
	{
		User user = new User();
		user.setName(name);
		user.setPassword("Test");
		return user;
	}
}
