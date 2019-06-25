/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LoginUser.java   
 * @Package com.loris.common.bean   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.model;

/**   
 * @ClassName:  LoginUser    
 * @Description: 用户接口信息   
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface LoginUser
{
	/**
	 * 用户的名称
	 * @return
	 */
	String getName();
	
	/**
	 * 获得登录名称
	 * @return
	 */
	String getLoginName();
	
	/**
	 * 获得用户密码
	 * @return
	 */
	String getPassword();
}
