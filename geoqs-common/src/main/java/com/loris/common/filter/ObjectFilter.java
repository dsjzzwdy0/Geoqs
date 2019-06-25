/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SingleObjectFilter.java   
 * @Package com.loris.soccer.service.filter   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter;

/**   
 * @ClassName:  SingleObjectFilter    
 * @Description: 数据过滤器  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ObjectFilter<T> implements Filter<T>
{
	/** The object value.*/
	protected T value;
	
	/**
	 * Create a new instance of SingleObjectFilter.
	 */
	public ObjectFilter()
	{
	}
	
	public ObjectFilter(T value)
	{
		this.value = value;
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.AbstractFilter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(T obj)
	{
		if(obj == value) return true;
		if(value == null) return false;
		return value.equals(obj);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.Filter#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(T value)
	{
		this.value = value;		
	}
}
