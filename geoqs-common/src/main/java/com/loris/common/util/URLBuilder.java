/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

/**   
 * @ClassName:  League   
 * @Description: Url地址编辑类 
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public class URLBuilder
{
	private static boolean[] eucIgnore = new boolean[256];

	protected StringBuilder builder = new StringBuilder();
	protected boolean firstParam = true;
	protected boolean hasPath = false;
	
	/**
	 * 组成访问页面地址
	 * @param basicUrl 基础服务地址
	 * @param method 
	 * @param params
	 * @return
	 */
	public static String makeDefaultUrl(String basicUrl, Map<String, String> params)
	{
		if(params == null)
		{
			return basicUrl;
		}
		if (basicUrl.contains("?"))
		{
			basicUrl += "&";
		}
		else
		{
			basicUrl += "?";
		}
		String paramStr = "";
		int i = 0;
		for (String key : params.keySet())
		{
			if (i != 0)
			{
				paramStr += "&";
			}
			paramStr += key + "=" + params.get(key);
			i++;
		}
		basicUrl += paramStr;
		return basicUrl;
	}

	private static String percentify(String s)
	{
		StringBuilder sb = new StringBuilder();

		ByteBuffer bb = Charset.forName("utf-8").encode(s);
		int size = bb.limit();
		for (int i = 0; i < size; i++)
		{
			sb.append(String.format("%%%02x", new Object[]
			{
					Integer.valueOf(bb.get() & 0xFF)
			}));
		}

		return sb.toString();
	}

	public static String encodeURIComponent(String s)
	{
		if (s == null)
		{
			return null;
		}
		if ("".equals(s))
		{
			return "";
		}
		StringBuilder sb = new StringBuilder();

		int _i = 0;
		int c = Character.codePointAt(s, _i);
		boolean ignore = (c < 256) && (eucIgnore[c] != false);

		for (int i = 0; i < s.length(); i++)
		{
			c = Character.codePointAt(s, i);
			if (ignore != ((c < 256) && (eucIgnore[c] != false)))
			{
				if (ignore)
					sb.append(s.substring(_i, i));
				else
					sb.append(percentify(s.substring(_i, i)));
				ignore = !ignore;
				_i = i;
			}
		}

		if (ignore)
			sb.append(s.substring(_i, s.length()));
		else
		{
			sb.append(percentify(s.substring(_i, s.length())));
		}

		return sb.toString();
	}

	protected void appendParamPrefix()
	{
		if (this.firstParam)
		{
			this.firstParam = false;
			if (this.hasPath)
				this.builder.append('?');
		}
		else
		{
			this.builder.append('&');
		}
	}

	public URLBuilder appendPath(String path)
	{
		if (path == null)
		{
			return this;
		}
		if ((this.hasPath) || (!this.firstParam))
		{
			throw new IllegalStateException("Missed the trick to set path.");
		}
		this.hasPath = true;

		this.builder.append(path);

		return this;
	}

	public URLBuilder appendParam(String key, String value)
	{
		if ((key != null) && (value != null))
		{
			appendParamPrefix();
			this.builder.append(key).append('=');
			this.builder.append(value);
		}

		return this;
	}

	public URLBuilder appendParamEncode(String key, String value)
	{
		if ((key != null) && (value != null))
		{
			appendParamPrefix();
			this.builder.append(key).append('=');
			this.builder.append(encodeURIComponent(value));
		}

		return this;
	}

	public URLBuilder appendParamEncode(String key, String value, String charset)
	{
		appendParamEncode(key, value);

		return this;
	}

	public URLBuilder appendLabel(String label)
	{
		this.builder.append('#').append(label);

		return this;
	}

	public URLBuilder append(String str)
	{
		this.builder.append(str);

		return this;
	}

	public String toString()
	{
		return this.builder.toString();
	}

	static
	{
		String ignore = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM-_.!~*'()";
		for (int i = 0; i < ignore.length(); i++)
			eucIgnore[Character.codePointAt(ignore, i)] = true;
	}
}
