package com.tigis.geoqs.model;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;


@TableName("sys_user")
public class User
{
	private String name;
	private String password;
	
	private List<Role> roles;
	
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
	
	public List<Role> getRoles()
	{
		return roles;
	}
	public void setRoles(List<Role> roles)
	{
		this.roles = roles;
	}
	@Override
	public String toString()
	{
		return "User [name=" + name + ", password=" + password + "]";
	}
}
