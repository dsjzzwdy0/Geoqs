package cn.stylefeng.guns.modular.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.stylefeng.guns.modular.system.model.Tester;

@Controller
@RequestMapping("/test")
public class TestController
{
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	/**
	 * 获得用户名称
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user")
	public Tester getUser(@Validated Tester user, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			for (ObjectError error : bindingResult.getAllErrors())
			{
				logger.info("Error occured when validate: " + error);
			}
			Tester user1 = new Tester();
			user1.setName("Error");
			return user1;
		}
		else {
			Tester user1 = new Tester();
			user1.setName("Test");
			
			logger.info("User: " + user1);
			return user1;
		}
	}
}
