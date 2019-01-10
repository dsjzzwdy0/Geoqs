package cn.stylefeng.guns.modular.system.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.stylefeng.guns.modular.system.model.User;

@Controller
@RequestMapping("/test")
public class TestController
{
	
	/**
	 * 获得用户名称
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user")
	public User getUser(@Valid User user)
	{
		return user;
	}
}
