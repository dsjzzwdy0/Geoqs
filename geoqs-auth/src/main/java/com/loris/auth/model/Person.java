package com.loris.auth.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 *人员信息表
 * </p>
 *
 * @since 2019-6-12
 */
@TableName("sys_person")
public class Person extends Model<Person>
{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 账号ID
	 */
	private Integer accountid;
	/**
	 * 单位ID
	 */
	private Integer deptid;
	/**
	 * 人员姓名
	 */
	private String realname;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 身份证号码
	 */
	private String cardno;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 电话号码
	 */
	private String tel;
	/**
	 * 专业技术职称
	 */
	private String techtitle;
	/**
	 * 注册岩土证书编号
	 */
	private String zcytno;
	/**
	 * 注册岩土证书
	 */
	private String zcytcard;
	/**
	 * 项目负责人证书号码
	 */
	private String xmfzrno;
	/**
	 * 项目负责人证书
	 */
	private String xmfzrcard;
	/**
	 * 司钻员证书号码
	 */
	private String szyno;
	/**
	 * 司钻员证书
	 */
	private String szycard;
	/**
	 * 描述员证书编号
	 */
	private String msyno;
	/**
	 * 描述员证书
	 */
	private String msycard;
	/**
	 * 备注
	 */
	private String bz;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTechtitle() {
		return techtitle;
	}

	public void setTechtitle(String techtitle) {
		this.techtitle = techtitle;
	}

	public String getZcytno() {
		return zcytno;
	}

	public void setZcytno(String zcytno) {
		this.zcytno = zcytno;
	}

	public String getZcytcard() {
		return zcytcard;
	}

	public void setZcytcard(String zcytcard) {
		this.zcytcard = zcytcard;
	}

	public String getXmfzrno() {
		return xmfzrno;
	}

	public void setXmfzrno(String xmfzrno) {
		this.xmfzrno = xmfzrno;
	}

	public String getXmfzrcard() {
		return xmfzrcard;
	}

	public void setXmfzrcard(String xmfzrcard) {
		this.xmfzrcard = xmfzrcard;
	}

	public String getSzyno() {
		return szyno;
	}

	public void setSzyno(String szyno) {
		this.szyno = szyno;
	}

	public String getSzycard() {
		return szycard;
	}

	public void setSzycard(String szycard) {
		this.szycard = szycard;
	}

	public String getMsyno() {
		return msyno;
	}

	public void setMsyno(String msyno) {
		this.msyno = msyno;
	}

	public String getMsycard() {
		return msycard;
	}

	public void setMsycard(String msycard) {
		this.msycard = msycard;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	@Override
	protected Serializable pkVal()
	{
		return this.id;
	}
	@Override
	public String toString()
	{
		return "Person{" + "id=" + id + ", realname=" + realname + "}";
	}
}
