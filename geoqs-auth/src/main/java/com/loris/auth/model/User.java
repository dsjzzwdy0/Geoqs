package com.loris.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 账号信息表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@TableName("sys_account")
public class User extends Model<User>
{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 账号
	 */	
	@NotBlank(message="不为空")
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * md5密码盐
	 */
	private String salt;
	/**
	 * 用户名称
	 */
	@TableField(exist=false)
	private String name;
	/**
	 * 状态(1：启用 2：冻结 3：删除）
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 保留字段
	 */
	private Integer version;
	/**
	 * 用户类型：0,单位用户；1，个人用户
	 */
	private Integer type;
	/**
	 * 注册状态：0，注册申请；1，审核通过；2，审核不通过
	 */
	private Integer regstatus;
	/**
	 * 审核日期
	 */
	private Date shDate;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getSalt()
	{
		return salt;
	}

	public void setSalt(String salt)
	{
		this.salt = salt;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRegstatus() {
		return regstatus;
	}

	public void setRegstatus(Integer regstatus) {
		this.regstatus = regstatus;
	}

	public Date getShDate() {
		return shDate;
	}

	public void setShDate(Date shDate) {
		this.shDate = shDate;
	}
	
	//临时
	public  Integer getDeptid() {
		return 10;
	}
	@Override
	protected Serializable pkVal()
	{
		return this.id;
	}

	@Override
	public String toString()
	{
		return "User{" + "id=" + id + ", avatar=" + avatar + ", account=" + account + ", password=" + password
				+ ", salt=" + salt +  ", status=" + status
				+ ", createtime=" + createtime + ", version=" + version + "}";
	}
}
