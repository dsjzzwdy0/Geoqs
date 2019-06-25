/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Match.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:30:27   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class UUIDEntity implements Entity
{
	/***/
	private static final long serialVersionUID = 1L;

	/** */
	@TableId(type = IdType.UUID)
	protected String id;

	/**
	 * 获得ID值
	 * 
	 * @return 返回ID值
	 */
	@Override
	public String getId()
	{
		return id;
	}

	/**
	 * 设置ID值
	 * 
	 * @param id
	 *            ID值
	 */
	@Override
	public void setId(String id)
	{
		this.id = id;
	}
}
