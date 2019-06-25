/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  XssFilter.java   
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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.loris.common.web.filter.wrapper.XsslHttpServletRequestWrapper;

/**
 * @ClassName: XssFilter
 * @Description: Xss过滤器
 * @author: 天勘岩土工程信息化项目组
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.loris.com Inc. All rights reserved.
 *             注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目
 */
@WebFilter(filterName="xssfilter", urlPatterns="/*") 
public class XssFilter implements Filter
{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	/**
	 * 过路Xss攻击漏洞数据
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		XsslHttpServletRequestWrapper xssRequest = new XsslHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy()
	{
	}
}