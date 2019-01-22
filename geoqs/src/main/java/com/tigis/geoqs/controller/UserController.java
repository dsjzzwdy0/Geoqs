package com.tigis.geoqs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController
{
	
	@RequestMapping("/list/{page}")
	public String list(@PathVariable String page, Model model)
	{
		model.addAttribute("user", "test");
		model.addAttribute("page", page);
		return "/user/test";
	}
}
