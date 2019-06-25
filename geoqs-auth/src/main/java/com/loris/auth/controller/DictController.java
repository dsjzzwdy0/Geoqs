package com.loris.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loris.auth.dao.DictMapper;
import com.loris.auth.dictmap.factory.AuthDictMapNames;
import com.loris.auth.factory.ConstantFactory;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.model.Dict;
import com.loris.auth.service.DictService;
import com.loris.auth.wrapper.DictWarpper;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.annotation.Permission;
import com.loris.common.constant.Constants;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;
import com.loris.common.pagination.PageFactory;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 *
 * @author fengshuonan
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/beetl/system/";

    @Resource
    DictMapper dictMapper;
    
    @Resource
    DictService dictService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dictForm";
    }

    /**
     * 跳转到修改字典
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping("/dict_edit/{dictId}")
    public String deptUpdate(@PathVariable Integer dictId, Model model) {
        Dict dict =dictMapper.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictMapper.selectList(new QueryWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dictForm";
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = AuthDictMapNames.DictMap)
    @RequestMapping(value = "/add")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object add(String dictName, String dictValues,String dictCode,String dictTips) {
        if (ToolUtil.isOneEmpty(dictName, dictCode)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALID_PARAM);
        }
        this.dictService.addDict(dictCode,dictName, dictTips,dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object list(String condition) {
    	Page<Dict> page=new PageFactory<Dict>().defaultPage();
		long startTime=System.currentTimeMillis();
        IPage<Map<String, Object>> list = this.dictService.list(page,condition);
        @SuppressWarnings("unchecked")
		List<Map<String,Object>> result=(List<Map<String,Object>>)new DictWarpper(list.getRecords()).warp();
        list.setRecords(result);
        long stopTime=System.currentTimeMillis();
		return super.packForJqgrid((Page<Map<String,Object>>)list,stopTime-startTime);
    }

    /**
     * 字典详情
     */
    @RequestMapping(value = "/detail/{dictId}")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object detail(@PathVariable("dictId") Integer dictId) {
        return dictMapper.selectById(dictId);
    }

    /**
     * 修改字典
     */
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = AuthDictMapNames.DictMap)
    @RequestMapping(value = "/update")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object update(Integer dictId, String dictName, String dictValues,String dictCode,String dictTips) {
        if (ToolUtil.isOneEmpty(dictId, dictName, dictCode)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALID_PARAM);
        }
        dictService.editDict(dictId,dictCode, dictName,dictTips, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = AuthDictMapNames.DeleteDict)
    @RequestMapping(value = "/delete")
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Object delete(@RequestParam Integer dictId) {

        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));

        this.dictService.delteDict(dictId);
        return SUCCESS_TIP;
    }

}
