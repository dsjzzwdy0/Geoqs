/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MenuTree.java   
 * @Package com.loris.auth.model.view   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.auth.model.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.auth.model.node.MenuNode;

/**   
 * @ClassName:  MenuTree    
 * @Description: 菜单数据表格树结构 
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年5月24日   
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("sys_menu_treegrid")
public class MenuTreeGridEntity extends MenuNode
{
	private Boolean isLeaf;
	/*
	 * 菜单编号
	 */
	private String code;
	/*
	 * 菜单编号
	 */
	private String pcode;
	/*
	 * 菜单状态
	 */
	private Integer status;
	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
}
