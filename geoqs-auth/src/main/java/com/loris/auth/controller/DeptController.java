package com.loris.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loris.auth.dictmap.factory.AuthDictMapNames;
import com.loris.auth.factory.ConstantFactory;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.model.Dept;
import com.loris.auth.model.Dict;
import com.loris.auth.service.DeptService;
import com.loris.auth.service.DictService;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.annotation.Permission;
import com.loris.common.constant.Constants;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;
import com.loris.common.pagination.PageFactory;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;
import com.loris.auth.dao.DeptMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController 
{
	@Resource
	DeptMapper deptMapper;
	 
    @Resource
    DeptService deptService;
    
    @Resource
    DictService dictService;
    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
    	List<Dict> unitTypes = dictService.selectByPCode("unit_type");
        model.addAttribute("unitTypes", unitTypes);
        return "/beetl/system/dept";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return "dept_add.system";
    }

    /**
     * 跳转到修改部门
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping("/dept_detail/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.getById(deptId);
        model.addAttribute(dept);
        LogObjectHolder.me().set(dept);
        return "dept_edit.system";
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition,String unittype) {
    	Page<Map<String,Object>> page=new PageFactory<Map<String,Object>>().defaultPage();
    	long startTime=System.currentTimeMillis();
    	List<Map<String,Object>> list = deptMapper.list(page,condition,unittype);
		page.setRecords(list);
		long stopTime=System.currentTimeMillis();
		return super.packForJqgrid(page,stopTime-startTime);
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptService.getById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = AuthDictMapNames.DeptDict)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        deptService.updateById(dept);
        return BaseController.SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = AuthDictMapNames.DeleteDict)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));
        deptService.deleteDept(deptId);
        return SUCCESS_TIP;
    }

}
