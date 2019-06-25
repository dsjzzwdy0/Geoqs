/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ApplicationContextHelper.java   
 * @Package com.loris.soccer.context   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午9:06:19   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.context;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: ApplicationContextHelper
 * @Description: 应用管理类
 * @author: 天勘岩土工程信息化项目组
 * @date: 2019年1月28日 下午9:06:19
 * 
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved.
 *             注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目
 */
public class ApplicationContextHelper implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		applicationContext = context;
	}
	
	/**
	 * 生成Bean实例对象
	 * @param clazz 类
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz)
	{
		if (applicationContext == null)
		{
			return null;
		}
		return applicationContext.getBean(clazz);
	}
	
	/**
	 * 生成实例对象
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz)
	{
		if (applicationContext == null)
		{
			return null;
		}
		return applicationContext.getBean(name, clazz);
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name)
	{
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}
	
	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected()
	{
		Validate.validState(applicationContext != null,
				"applicatonContext属性未注入, 请在applicationContext.xml中定义ApplicationContextHelper.");
	}
}
