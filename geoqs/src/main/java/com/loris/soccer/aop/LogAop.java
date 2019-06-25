package com.loris.soccer.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.loris.auth.log.LogManager;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.log.LogTaskFactory;
import com.loris.auth.security.ShiroKit;
import com.loris.auth.security.ShiroUser;
import com.loris.auth.util.Contrast;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.dictmap.AbstractDictMap;
import com.loris.common.dictmap.factory.DictMapFactory;
import com.loris.common.support.HttpKit;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志记录
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午8:48:30
 */
@Aspect
@Component
public class LogAop
{

	private static Logger logger = Logger.getLogger(LogAop.class);

	@Pointcut(value = "@annotation(com.loris.common.annotation.BussinessLog)")
	public void cutService()
	{
	}

	@Around("cutService()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable
	{
		logger.info("Starting to record log service.");
		// 先执行业务
		Object result = point.proceed();
		try
		{
			handle(point);
		}
		catch (Exception e)
		{
			logger.error("日志记录出错!", e);
		}

		return result;
	}

	private void handle(ProceedingJoinPoint point) throws Exception
	{
		// 获取拦截的方法名
		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature))
		{
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		String methodName = currentMethod.getName();

		// 如果当前用户未登录，不做日志
		ShiroUser user = ShiroKit.getUser();
		if (null == user)
		{
			return;
		}

		// 获取拦截方法的参数
		String className = point.getTarget().getClass().getName();
		Object[] params = point.getArgs();

		// 获取操作名称
		BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
		String bussinessName = annotation.value();
		String key = annotation.key();
		String dictClass = annotation.dict();

		StringBuilder sb = new StringBuilder();
		for (Object param : params)
		{
			sb.append(param);
			sb.append(" & ");
		}

		// 如果涉及到修改,比对变化
		String msg;
		if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1)
		{
			Object obj1 = LogObjectHolder.me().get();
			Map<String, String> obj2 = HttpKit.getRequestParameters();
			msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
		}
		else
		{
			Map<String, String> parameters = HttpKit.getRequestParameters();
			AbstractDictMap dictMap = DictMapFactory.createDictMap(dictClass);
			msg = Contrast.parseMutiKey(dictMap, key, parameters);
		}

		LogManager.me()
				.executeLog(LogTaskFactory.bussinessLog(user.getId(), bussinessName, className, methodName, msg));
	}
}