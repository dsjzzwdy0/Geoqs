package com.tigis.geoqs.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("sys_user")
public class User
{
	@NotNull(message="用户名不能为空")
	@Size(min=5, max=15, message="用户名长度要在5-15个字符之间")
	private String username;
	
	@NotNull(message="密码不能为空")
	@Size(min=5, max=15, message="密码长度要在5-15个字符之间")
	private String password;
	
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
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
