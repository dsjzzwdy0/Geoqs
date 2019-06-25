/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  DateFilter.java   
 * @Package com.loris.soccer.filter   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter;

import java.util.Calendar;
import java.util.Date;

/**   
 * @ClassName: DateFilter   
 * @Description: 日期过滤器  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class DateFilter extends ObjectFilter<Date> implements Filter<Date>
{
	public enum FilterType
	{
		/**
		 * 等于
		 */
		Equal,
		
		/**
		 * 大于
		 */
		GT,
		
		/**
		 * 小于
		 */
		LT,
		
		/**
		 * 不大于
		 */
		NotGt,
		
		/**
		 * 不小于
		 */
		NotLt,
		
		/**
		 * 日期相等
		 */
		EqualDay
	}
	
	/** 过滤的类型 */
	FilterType type;
	
	/**
	 * Create a new instance of DateFilter.
	 */
	public DateFilter()
	{
		this(null);
	}
	
	public DateFilter(Date date)
	{
		this(date, FilterType.Equal);
	}
	
	public DateFilter(Date date, FilterType type)
	{
		this.value = date;
		this.type = type;
	}
	
	public FilterType getType()
	{
		return type;
	}

	public void setType(FilterType type)
	{
		this.type = type;
	}


	/**
	 * 过滤函数
	 * @param t 时间数据
	 * @return 返回可能过滤的结果
	 */
	@Override
	public boolean accept(Date t)
	{
		//如果两个值均为空值时，则判断相等
		if(value == t) return true;
		if(value == null && t == null) return true;
		if(value == null || t == null) return false;
		
		if(type == FilterType.Equal)
		{
			return value.equals(t);
		}
		else if(type == FilterType.EqualDay)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			int year = calendar.get(Calendar.YEAR);
			int day = calendar.get(Calendar.DAY_OF_YEAR);			
			calendar.setTime(t);
			return year == calendar.get(Calendar.YEAR) && day == calendar.get(Calendar.DAY_OF_YEAR);
		}
		else if(type == FilterType.GT)
		{
			return t.getTime() > value.getTime();
		}
		else if(type == FilterType.LT)
		{
			return t.getTime() < value.getTime();
		}
		else if(type == FilterType.NotGt)
		{
			return t.getTime() <= value.getTime();
		}
		else if(type == FilterType.NotLt)
		{
			return t.getTime() >= value.getTime();
		}		
		return false;
	}
}
