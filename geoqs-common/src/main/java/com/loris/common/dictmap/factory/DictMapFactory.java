package com.loris.common.dictmap.factory;

import com.loris.common.dictmap.AbstractDictMap;
import com.loris.common.dictmap.SystemDict;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;

/**
 * 字典的创建工厂
 *
 * @author fengshuonan
 * @date 2017-05-06 15:12
 */
public class DictMapFactory
{
	/**
	 * 通过类名创建具体的字典类
	 */
	public static AbstractDictMap createDictMap(String className)
	{
		if ("com.loris.common.dicmap.SystemDict".equals(className))
		{
			return new SystemDict();
		}
		else
		{
			try
			{
				@SuppressWarnings("unchecked")
				Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(className);
				return clazz.newInstance();
			}
			catch (Exception e)
			{
				throw new BussinessException(BizExceptionEnum.ERROR_CREATE_DICT);
			}
		}
	}
}
