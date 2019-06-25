/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  KeyMap.java   
 * @Package com.loris.common.util   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.util;

import java.util.HashMap;
import java.util.Map;

/**   
 * @ClassName: KeyMap   
 * @Description: 关键字值键对管理类  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class KeyMap extends HashMap<String, String>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KeyMap()
	{
	}
	
	public KeyMap(String key, String value)
	{
		put(key, value);
	}
	
	/**
	 * 创建一个关键词－值对
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map<String, String> createMap(String key, String value)
	{
		return new KeyMap(key, value);
	}
}
