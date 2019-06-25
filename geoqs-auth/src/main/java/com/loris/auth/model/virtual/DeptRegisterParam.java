package com.loris.auth.model.virtual;

import com.loris.auth.model.Dept;
import com.loris.auth.model.User;
/**
 * 单位注册数据传递类
 * @author xx
 *
 */
public class DeptRegisterParam {
	/**
	 * 单位信息
	 */
	private Dept dept;
	/**
	 * 用户信息
	 */
	private User user;
	/**
	 * 单位类型编码
	 */
	private String deptTypes;
	
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDeptTypes() {
		return deptTypes;
	}
	public void setDeptTypes(String deptTypes) {
		this.deptTypes = deptTypes;
	}
	
}
