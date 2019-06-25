/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  PageInfo.java   
 * @Package com.loris.common.page   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.pagination;

import javax.validation.constraints.Min;

/**   
 * @ClassName:  PageInfo    
 * @Description: 分页查询数据  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class PageInfo
{
	@Min(value=0)
	int index;
	
	@Min(value=5)
	int pernum;
	
	
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;
	}
	public int getPernum()
	{
		return pernum;
	}
	public void setPernum(int pernum)
	{
		this.pernum = pernum;
	}
}
