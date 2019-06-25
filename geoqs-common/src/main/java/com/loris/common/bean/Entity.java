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

import java.io.Serializable;

public interface Entity extends Serializable
{
	/**
	 * 设置ID值
	 * 
	 * @param id
	 */
	void setId(String id);

	/**
	 * 获得ID值
	 * 
	 * @return
	 */
	String getId();
}
