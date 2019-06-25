/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.loris.auth.security.check;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loris.auth.security.ShiroKit;
import com.loris.auth.security.ShiroUser;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.context.ConfigListener;
import com.loris.common.support.CollectionKit;
import com.loris.common.support.HttpKit;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限自定义检查
 */
@Service
@Transactional(readOnly = true)
public class PermissionCheckFactory implements ICheck
{
	public static ICheck me()
	{
		return ApplicationContextHelper.getBean(ICheck.class);
	}

	@Override
	public boolean check(Object[] permissions)
	{
		ShiroUser user = ShiroKit.getUser();
		if (null == user)
		{
			return false;
		}
		String join = CollectionKit.join(permissions, ",");
		if (ShiroKit.hasAnyRoles(join))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean checkAll()
	{
		HttpServletRequest request = HttpKit.getRequest();
		ShiroUser user = ShiroKit.getUser();
		if (null == user)
		{
			return false;
		}
		String requestURI = request.getRequestURI().replace(ConfigListener.getConf().get("contextPath"), "");
		String[] str = requestURI.split("/");
		if (str.length > 3)
		{
			requestURI = "/" + str[1] + "/" + str[2];
		}
		if (ShiroKit.hasPermission(requestURI))
		{
			return true;
		}
		return false;
	}

}
