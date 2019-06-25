package com.loris.auth.wrapper;

import java.util.Map;

import com.loris.auth.factory.ConstantFactory;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.wrapper.BaseWrapper;

/**
 * 日志列表的包装类
 *
 * @author fengshuonan
 * @date 2017年4月5日22:56:24
 */
public class LogWrapper extends BaseWrapper
{
	// 记录每个修改字段的分隔符
	public static final String separator = ";;;";

	public LogWrapper(Object list)
	{
		super(list);
	}

	@Override
	public void wrapTheMap(Map<String, Object> map)
	{
		String message = (String) map.get("message");

		Integer userid = (Integer) map.get("userid");
		map.put("userName", ConstantFactory.me().getUserNameById(userid));

		// 如果信息过长,则只截取前100位字符串
		if (ToolUtil.isNotEmpty(message) && message.length() >= 100)
		{
			String subMessage = message.substring(0, 100) + "...";
			map.put("message", subMessage);
		}

		// 如果信息中包含分割符号;;; 则分割字符串返给前台
		if (ToolUtil.isNotEmpty(message) && message.indexOf(separator) != -1)
		{
			String[] msgs = message.split(separator);
			map.put("regularMessage", msgs);
		}
		else
		{
			map.put("regularMessage", message);
		}
	}

}
