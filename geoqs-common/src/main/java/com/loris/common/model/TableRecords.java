/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.model;

import java.util.HashMap;
import java.util.List;

/**   
 * @ClassName:  ParserResults  
 * @Description: 页面解析的结果类，可以是很多的内容，根据需要来进行定义 
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TableRecords extends HashMap<String, Object>
{
	/***/
	private static final long serialVersionUID = 1L;

	protected boolean success; 			//解析页面是否成功
	protected String info;				//解析过程中的信息
	
	/**
	 * Create a new instance of ParserResults.
	 */
	public TableRecords()
	{
		this(true, "Success");
	}
	
	/**
	 * 创建一个解析结果
	 * @param success
	 * @param info
	 */
	public TableRecords(boolean success, String info)
	{
		this.success = success;
		this.info = info;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	/**
	 * 创建一个失败的结果
	 * @param info
	 * @return
	 */
	public static TableRecords failure(String info)
	{
		return new TableRecords(false, info);
	}
	
	/**
	 * 创建一个失败的数据结果
	 * @return
	 */
	public static TableRecords failure()
	{
		return new TableRecords(false, "Error result.");
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for (String key : keySet())
		{
			Object object = get(key);
			sb.append(key + ": " + object.getClass().getName());
			if(object instanceof List)
			{
				sb.append("[" + ((List<?>)object).size() + "]");
			}
			sb.append("; ");
		};
		return sb.toString();
	}
}
