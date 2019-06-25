/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  CookieUtils.java   
 * @Package com.loris.soccer.controller   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**   
 * @ClassName:  CookieUtils    
 * @Description: Cookie的值处理  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class CookieUtils
{
	// cookie的有效期默认为30天
    public final static int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; 
    
	/**
	 * 添加一个新Cookie
	 * 
	 * @author zifangsky
	 * @param response
	 *            HttpServletResponse
	 * @param cookie
	 *            新cookie
	 * 
	 * @return null
	 */
	public static void addCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null)
			response.addCookie(cookie);
	}

	/**
	 * 添加一个新Cookie
	 * 
	 * @author zifangsky
	 * @param response
	 *            HttpServletResponse
	 * @param cookieName
	 *            cookie名称
	 * @param cookieValue
	 *            cookie值
	 * @param domain
	 *            cookie所属的子域
	 * @param httpOnly
	 *            是否将cookie设置成HttpOnly
	 * @param maxAge
	 *            设置cookie的最大生存期
	 * @param path
	 *            设置cookie路径
	 * @param secure
	 *            是否只允许HTTPS访问
	 * 
	 * @return null
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain,
			boolean httpOnly, int maxAge, String path, boolean secure)
	{
		if (StringUtils.isNotBlank(cookieName)) {
			if (cookieValue == null)
				cookieValue = "";

			Cookie newCookie = new Cookie(cookieName, cookieValue);
			if (domain != null)
				newCookie.setDomain(domain);

			newCookie.setHttpOnly(httpOnly);

			if (maxAge > 0)
				newCookie.setMaxAge(maxAge);

			if (path == null)
				newCookie.setPath("/");
			else
				newCookie.setPath(path);

			newCookie.setSecure(secure);

			addCookie(response, newCookie);
		}
	}

	/**
	 * 添加一个新Cookie
	 * 
	 * @author zifangsky
	 * @param response
	 *            HttpServletResponse
	 * @param cookieName
	 *            cookie名称
	 * @param cookieValue
	 *            cookie值
	 * @param domain
	 *            cookie所属的子域
	 * 
	 * @return null
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain) {
		addCookie(response, cookieName, cookieValue, domain, true, COOKIE_MAX_AGE, "/", false);
	}

	/**
	 * 根据Cookie名获取对应的Cookie
	 * 
	 * @author zifangsky
	 * @param request
	 *            HttpServletRequest
	 * @param cookieName
	 *            cookie名称
	 * 
	 * @return 对应cookie，如果不存在则返回null
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) 
	{
		Cookie[] cookies = request.getCookies();

		if (StringUtils.isBlank(cookieName))
			return null;

		for (Cookie c : cookies) {
			if (c.getName().equals(cookieName))
				return (Cookie) c;
		}
		return null;
	}

	/**
	 * 根据Cookie名获取对应的Cookie值
	 * 
	 * @author zifangsky
	 * @param request
	 *            HttpServletRequest
	 * @param cookieName
	 *            cookie名称
	 * 
	 * @return 对应cookie值，如果不存在则返回null
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie == null)
			return null;
		else
			return cookie.getValue();
	}

	/**
	 * 删除指定Cookie
	 * 
	 * @author zifangsky
	 * @param response
	 *            HttpServletResponse
	 * @param cookie
	 *            待删除cookie
	 */
	public static void delCookie(HttpServletResponse response, Cookie cookie) 
	{
		if (cookie != null) {
			cookie.setPath("/"); 
			cookie.setMaxAge(0);
			cookie.setValue(null);

			response.addCookie(cookie);
		}
	}

	/**
	 * 根据cookie名删除指定的cookie
	 * 
	 * @author zifangsky
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param cookieName
	 *            待删除cookie名
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie c = getCookie(request, cookieName);
		if (c != null && c.getName().equals(cookieName)) {
			delCookie(response, c);
		}
	}

	/**
	 * 根据cookie名修改指定的cookie
	 * 
	 * @author zifangsky
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param cookieName
	 *            cookie名
	 * @param cookieValue
	 *            修改之后的cookie值
	 * @param domain
	 *            修改之后的domain值
	 */
	public static void editCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue,String domain) 
	{
		Cookie c = getCookie(request, cookieName);
		if (c != null && StringUtils.isNotBlank(cookieName) && c.getName().equals(cookieName)) {
			addCookie(response, cookieName, cookieValue, domain);
		}
	}
}
