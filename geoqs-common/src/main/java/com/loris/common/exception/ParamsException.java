/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ParamsException.java   
 * @Package com.loris.common.exception   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.exception;

import java.util.Map;

/**   
 * @ClassName: ParamsException   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ParamsException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamsException()
	{
	}
	
	public ParamsException(Map<String, String> infos)
	{
		super(infos.toString());
	}
}
