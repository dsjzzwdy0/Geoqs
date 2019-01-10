package cn.stylefeng.guns.modular.system.model;

import javax.validation.constraints.NotEmpty;

public class Tester
{
	@NotEmpty(message="用户不能为空")
	private String name;
	
	@NotEmpty(message="密码不能为空")
	private String password;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
}
