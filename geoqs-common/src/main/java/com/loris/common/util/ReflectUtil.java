/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOpService.java   
 * @Package com.loris.soccer   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:33:06   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**   
 * @ClassName:  OddsOpService   
 * @Description: Java反射工具 
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:33:06   
 *     
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ReflectUtil
{
	/**
	 * 获取所有的方法
	 * @param clazz
	 * @return
	 */
	public static List<Method> getAllMethods(Class<?> clazz, boolean containsObject)
	{
		List<Method> methods = new ArrayList<>();
		Class<?> tmpClazz = clazz;
		while(tmpClazz != null)
		{
			if(!containsObject && tmpClazz.getName().toLowerCase().equals("java.lang.object"))
			{
				break;
			}
			methods.addAll(Arrays.asList(tmpClazz.getDeclaredMethods()));
			tmpClazz = tmpClazz.getSuperclass(); //得到父类,然后赋给自己
		}
		return methods;
	}
	
	/**
	 * 获得所声明的字段值
	 * @param clazz
	 * @param containsObject
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> clazz, boolean containsObject)
	{
		List<Field> fields = new ArrayList<>();
		Class<?> tmpClazz = clazz;
		while(tmpClazz != null)
		{
			if(!containsObject && tmpClazz.getName().toLowerCase().equals("java.lang.object"))
			{
				break;
			}
			fields.addAll(Arrays.asList(tmpClazz.getDeclaredFields()));
			tmpClazz = tmpClazz.getSuperclass(); //得到父类,然后赋给自己
		}
		return fields;
	}
}
