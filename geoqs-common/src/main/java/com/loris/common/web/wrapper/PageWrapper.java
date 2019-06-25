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
package com.loris.common.web.wrapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**   
 * @ClassName:  League   
 * @Description: 分页查询数据包装类
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class PageWrapper<T>
{
	private int total = 0;
	private int page = 0;
	private int size = 0;
	private List<T> rows = null;
	
	/**
	 * 分页查询数据实例
	 */
	public PageWrapper()
	{		
	}
	
	/**
	 * 分页查询数据实例
	 * @param pageobj
	 */
	public PageWrapper(IPage<T> pageobj)
	{
		if(pageobj != null)
		{
			total = (int)pageobj.getTotal();
			page = (int)pageobj.getPages();
			size = (int)pageobj.getSize();
			rows = pageobj.getRecords();
		}
	}
	
	/**
	 * Create a new instance of PageWrapper.
	 * 
	 * @param rows
	 */
	public PageWrapper(List<T> rows)
	{
		if(rows != null)
		{
			this.rows = rows;
			total = rows.size();
			size = rows.size();
			page = 1;
		}
	}
	
	/**
	 * Create a new PageWrapper.
	 * 
	 * @param rows
	 * @param total
	 * @param size
	 * @param pages
	 */
	public PageWrapper(List<T> rows, int total, int size, int page)
	{
		this.rows = rows;
		this.total = total;
		this.size = size;
		this.page = page;
	}
	
	public int getTotal()
	{
		return total;
	}
	public void setTotal(int total)
	{
		this.total = total;
	}
	public List<T> getRows()
	{
		return rows;
	}
	public void setRows(List<T> rows)
	{
		this.rows = rows;
	}
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public int getSize()
	{
		return size;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
}
