/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WafRequestWrapper.java   
 * @Package com.loris.common.controller.wrapper   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.web.wrapper;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.loris.common.support.WafKit;
/**   
 * @ClassName:  WafRequestWrapper    
 * @Description: 防止Xss、SQL注入的信息  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WafRequestWrapper extends HttpServletRequestWrapper
{
	private boolean filterXSS = true;

	private boolean filterSQL = true;


	public WafRequestWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
		super(request);
		this.filterXSS = filterXSS;
		this.filterSQL = filterSQL;
	}


	public WafRequestWrapper(HttpServletRequest request) {
		this(request, true, true);
	}


	/**
	 * @Description 数组参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if ( values == null ) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for ( int i = 0 ; i < count ; i++ ) {
			encodedValues[i] = filterParamString(values[i]);
		}

		return encodedValues;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterMap() {
		Map<String, String[]> primary = super.getParameterMap();
		Map<String, String[]> result = new HashMap<String, String[]>(primary.size());
		for ( Map.Entry<String, String[]> entry : primary.entrySet() ) {
			result.put(entry.getKey(), filterEntryString(entry.getValue()));
		}
		return result;

	}
	
	protected String[] filterEntryString(String[] rawValue) {
		for ( int i = 0 ; i < rawValue.length ; i++ ) {
			rawValue[i] = filterParamString(rawValue[i]);
		}
		return rawValue;
	}

	/**
	 * @Description 参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String getParameter(String parameter) {
		return filterParamString(super.getParameter(parameter));
	}


	/**
	 * @Description 请求头过滤 
	 * @param name
	 * 				过滤内容
	 * @return
	 */
	@Override
	public String getHeader(String name) {
		return filterParamString(super.getHeader(name));
	}


	/**
	 * @Description Cookie内容过滤
	 * @return
	 */
	@Override
	public Cookie[] getCookies() {
		Cookie[] existingCookies = super.getCookies();
		if (existingCookies != null) {
			for (int i = 0 ; i < existingCookies.length ; ++i) {
				Cookie cookie = existingCookies[i];
				cookie.setValue(filterParamString(cookie.getValue()));
			}
		}
		return existingCookies;
	}

	/**
	 * @Description 过滤字符串内容
	 * @param rawValue
	 * 				待处理内容
	 * @return
	 */
	protected String filterParamString(String rawValue) {
		if (null == rawValue) {
			return null;
		}
		String tmpStr = rawValue;
		if (this.filterXSS) {
			tmpStr = WafKit.stripXSS(rawValue);
		}
		if (this.filterSQL) {
			tmpStr = WafKit.stripSqlInjection(tmpStr);
		}
		return tmpStr;
	}
}