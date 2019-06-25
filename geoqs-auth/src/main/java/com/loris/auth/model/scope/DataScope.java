package com.loris.auth.model.scope;

import java.util.List;

public class DataScope
{
	private String scopeName = "deptid";

	private List<Integer> deptIds;

	public DataScope()
	{
	}

	public DataScope(List<Integer> deptIds)
	{
		this.deptIds = deptIds;
	}

	public DataScope(String scopeName, List<Integer> deptIds)
	{
		this.scopeName = scopeName;
		this.deptIds = deptIds;
	}

	public List<Integer> getDeptIds()
	{
		return deptIds;
	}

	public void setDeptIds(List<Integer> deptIds)
	{
		this.deptIds = deptIds;
	}

	public String getScopeName()
	{
		return scopeName;
	}

	public void setScopeName(String scopeName)
	{
		this.scopeName = scopeName;
	}
}