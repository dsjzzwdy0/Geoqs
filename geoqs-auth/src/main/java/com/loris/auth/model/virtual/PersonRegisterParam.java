package com.loris.auth.model.virtual;

import com.loris.auth.model.Person;
import com.loris.auth.model.User;
/**
 * 人员注册数据传递类
 * @author xx
 *
 */
public class PersonRegisterParam {
	/**
	 * 人员信息
	 */
	private Person person;
	/**
	 * 账号信息
	 */
	private User user;
	/**
	 * 人员角色
	 */
	private String rolecodes;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User account) {
		this.user = account;
	}
	public String getRolecodes() {
		return rolecodes;
	}
	public void setRolecodes(String rolecodes) {
		this.rolecodes = rolecodes;
	}
	
}
