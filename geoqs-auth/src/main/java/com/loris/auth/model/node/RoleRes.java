/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  RoleRes.java   
 * @Package com.loris.auth.model.view   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.auth.model.node;

import com.baomidou.mybatisplus.annotation.TableName;

/**   
 * @ClassName:  RoleRes    
 * @Description: 权限资源类
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("sys_role_res")
public class RoleRes
{
	private String id;
	private String url;
	private String roleid;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getRoleid()
	{
		return roleid;
	}
	public void setRoleid(String roleid)
	{
		this.roleid = roleid;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
}
