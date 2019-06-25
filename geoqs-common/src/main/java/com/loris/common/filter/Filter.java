/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Filter.java   
 * @Package com.loris.common.filter   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter;

/**
 * @ClassName: Filter
 * @Description: 对象过滤器
 * @author: 天勘岩土工程信息化项目组
 * @date: 2019年1月28日 下午8:59:32
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved.
 *             注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目
 */
public interface Filter<T>
{
	/**
	 * 数据过滤器对数据进行检测
	 * 
	 * @param obj
	 *            被检测过滤对象
	 * @return 是否被过滤
	 */
	boolean accept(T obj);

	/**
	 * 设置数据对象
	 * 
	 * @param value
	 */
	void setValue(T value);
}
