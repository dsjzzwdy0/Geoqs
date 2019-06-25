package com.loris.auth.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * 单位信息表
 * </p>
 *
 */
@TableName("sys_dept")
public class Dept extends Model<Dept> {

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
     * 简称
     */
    private String simplename;
    /**
     * 全称
     */
    private String fullname;
    /**
     * 省代码
     */
    private String province;
    /**
     * 城市代码
     */
    private String city;
    /**
     * 区代码
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 法人
     */
    private String faren;
    /**
     * 法人电话
     */
    private String farentel;
    /**
     * 法人身份证号
     */
    private String farencardno;
    /**
     * 法人身份证路径
     */
    private String farencard;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 联系人电话
     */
    private String contacttel;
    /**
     * 联系人身份证号
     */
    private String contactcardno;
    /**
     * 联系人身份证路径
     */
    private String contactcard;
    /**
     * 企业信用代码
     */
    private String unitcode;
    /**
     * 企业信用证书路径
     */
    private String unitcard;
    /**
     * 资质证书编号
     */
    private String zzzsno;
    /**
     * 资质证书路径
     */
    private String zzzscard;
    /**
     * 管理委托书路径
     */
    private String wts;
    /**
     * 邮箱地址
     */
    private String email;
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

	public String getSimplename() {
        return simplename;
    }

    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    
    
    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFaren() {
		return faren;
	}

	public void setFaren(String faren) {
		this.faren = faren;
	}

	public String getFarentel() {
		return farentel;
	}

	public void setFarentel(String farentel) {
		this.farentel = farentel;
	}

	public String getFarencardno() {
		return farencardno;
	}

	public void setFarencardno(String farencardno) {
		this.farencardno = farencardno;
	}

	public String getFarencard() {
		return farencard;
	}

	public void setFarencard(String farencard) {
		this.farencard = farencard;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContacttel() {
		return contacttel;
	}

	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}

	public String getContactcardno() {
		return contactcardno;
	}

	public void setContactcardno(String contactcardno) {
		this.contactcardno = contactcardno;
	}

	public String getContactcard() {
		return contactcard;
	}

	public void setContactcard(String contactcard) {
		this.contactcard = contactcard;
	}

	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}

	public String getUnitcard() {
		return unitcard;
	}

	public void setUnitcard(String unitcard) {
		this.unitcard = unitcard;
	}

	public String getZzzsno() {
		return zzzsno;
	}

	public void setZzzsno(String zzzsno) {
		this.zzzsno = zzzsno;
	}

	public String getZzzscard() {
		return zzzscard;
	}

	public void setZzzscard(String zzzscard) {
		this.zzzscard = zzzscard;
	}

	public String getWts() {
		return wts;
	}

	public void setWts(String wts) {
		this.wts = wts;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", simplename=" + simplename +
                ", fullname=" + fullname +
                "}";
    }
}
