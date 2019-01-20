package com.tigis.geoqs.sys.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.tigis.geoqs.common.annotation.BussinessLog;
import com.tigis.geoqs.common.constant.dictmap.base.AbstractDictMap;
import com.tigis.geoqs.common.constant.dictmap.factory.DictMapFactory;
import com.tigis.geoqs.common.log.LogManager;
import com.tigis.geoqs.common.log.LogObjectHolder;
import com.tigis.geoqs.common.log.LogTaskFactory;
import com.tigis.geoqs.core.support.HttpKit;
import com.tigis.geoqs.security.ShiroKit;
import com.tigis.geoqs.security.ShiroUser;
import com.tigis.geoqs.sys.util.Contrast;

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
public class LogAop {

    private Logger logger = Logger.getLogger(this.getClass());

    @Pointcut(value = "@annotation(com.tigis.geoqs.common.annotation.BussinessLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
    	logger.info("Starting to record log service.");
        //先执行业务
        Object result = point.proceed();
        try {
            handle(point);
        } catch (Exception e) {
            logger.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果当前用户未登录，不做日志
        ShiroUser user = ShiroKit.getUser();
        if (null == user) {
            return;
        }

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        String dictClass = annotation.dict();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        //如果涉及到修改,比对变化
        String msg;
        if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1) {
            Object obj1 = LogObjectHolder.me().get();
            Map<String, String> obj2 = HttpKit.getRequestParameters();
            msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
        } else {
            Map<String, String> parameters = HttpKit.getRequestParameters();
            AbstractDictMap dictMap = DictMapFactory.createDictMap(dictClass);
            msg = Contrast.parseMutiKey(dictMap,key,parameters);
        }

        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(), bussinessName, className, methodName, msg));
    }
}