/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SQLFilter.java   
 * @Package com.loris.common.filter   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.web.filter;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;


/**
 * @ClassName: SQLFilter
 * @Description: 过滤SQL语句的过滤器，在所有的数据访问之前进行处理
 * @author: 天勘岩土工程信息化项目组
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.loris.com Inc. All rights reserved.
 *             注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目
 */
public class SQLFilter implements Filter
{
	/** SQL语句的标准 */
	private static String[] injectStrs;
	
	static {
		String injectStr = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		injectStrs = injectStr.split("\\|");
	}
	
	
	/**
	 *  (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}
	
	/**
	 * 对提交的参数数据进行拦截处理
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{	
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Iterator<String[]> values = req.getParameterMap().values().iterator();// 获取所有的表单参数
		while (values.hasNext())
		{
			String[] value = (String[]) values.next();
			for (int i = 0; i < value.length; i++)
			{
				if (StringUtils.isNotEmpty(value[i]) && isSqlInject(value[i]))
				{
					// TODO这里发现sql注入代码的业务逻辑代码
					resp.sendRedirect(req.getContextPath()+"/Home/login");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	/**
	 * 判断是否有SQL注入的内容
	 * @param str 被判断的内容
	 * @return
	 */
	public boolean isSqlInject(String str)
	{
		String value = str.toLowerCase();
		for (int i = 0; i < injectStrs.length; i++)
		{
			if (value.indexOf("" + injectStrs[i] + "") >= 0)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 *  (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy()
	{
	}
}
