package com.loris.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.auth.dao.LoginLogMapper;
import com.loris.auth.dao.OperationLogMapper;
import com.loris.auth.model.OperationLog;
import com.loris.auth.wrapper.LogWrapper;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.annotation.Permission;
import com.loris.common.constant.Constants;
import com.loris.common.support.BeanKit;
import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.PageWrapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author fengshuonan
 * @Date 2017年4月5日 19:45:36
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController 
{
    @Resource
    private OperationLogMapper operationLogMapper;

    @Resource
    private LoginLogMapper loginLogMapper;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return "log.system";
    }

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
//        PageReq params = defaultPage();
//        PageHelper.offsetPage(params.getOffset(), params.getLimit());
        List<Map<String, Object>> result =null;// loginLogMapper.getOperationLogs(beginTime, endTime, logName, BizLogType.valueOf(logType), params.getSort(), params.isAsc());
        return new PageWrapper<>(result);
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping("/detail/{id}")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        OperationLog operationLog = operationLogMapper.selectById(id);
        Map<String, Object> stringObjectMap = BeanKit.beanToMap(operationLog);
        return super.warpObject(new LogWrapper(stringObjectMap));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object delLog() {
        //operationLogMapper.delete(new OperationLog());
        operationLogMapper.delete(new QueryWrapper<OperationLog>());
        return BaseController.SUCCESS_TIP;
    }
}
