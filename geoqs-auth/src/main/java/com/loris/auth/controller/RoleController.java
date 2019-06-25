package com.loris.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.auth.dao.RoleMapper;
import com.loris.auth.dao.UserMapper;
import com.loris.auth.dictmap.factory.AuthDictMapNames;
import com.loris.auth.factory.ConstantFactory;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.model.Role;
import com.loris.auth.model.node.ZTreeNode;
import com.loris.auth.service.RoleService;
import com.loris.auth.service.UserRoleService;
import com.loris.auth.wrapper.RoleWrapper;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.annotation.Permission;
import com.loris.common.cache.CacheKit;
import com.loris.common.constant.Constants;
import com.loris.common.constant.cache.Cache;
import com.loris.common.constant.tips.Tip;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;
import com.loris.common.util.Convert;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController 
{
    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleService roleService;
    
    @Resource
    UserRoleService userRoleService;
    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return "/beetl/system/role";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd(Model model) {
    	Role role=new Role();
    	model.addAttribute(role);
        return "/beetl/system/roleForm";
    }

    /**
     * 跳转到修改角色
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = roleMapper.selectById(roleId);
        model.addAttribute(role);
        LogObjectHolder.me().set(role);
        return "/beetl/system/roleForm";
    }

    /**
     * 跳转到角色分配
     */
//    @Permission
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return "/beetl/system/roleAssign";
    }

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String roleName) {
        List<Map<String, Object>> roles = roleMapper.selectRoles(super.getPara("roleName"));
        return super.warpObject(new RoleWrapper(roles));
    }

    /**
     * 角色新增
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "添加角色", key = "name", dict = AuthDictMapNames.RoleDict)
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Tip add(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 根据角色编号判断角色是否重复
        Role theRole = roleMapper.selectOne(new QueryWrapper<Role>().eq("code", role.getCode()));
        if (theRole != null) {
            throw new BussinessException(BizExceptionEnum.ROLE_ALREADY_EXISTED);
        }
        role.setId(null);
        roleMapper.insert(role);
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改角色", key = "name", dict = AuthDictMapNames.RoleDict)
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Tip edit(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 根据角色编号判断角色是否重复
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    	queryWrapper.ne("id", role.getId());
    	queryWrapper.and(wrapper-> wrapper.eq("code", role.getCode()));
        Role theRole = roleMapper.selectOne(queryWrapper);
        if (theRole != null) {
            throw new BussinessException(BizExceptionEnum.ROLE_ALREADY_EXISTED);
        }
        roleMapper.updateById(role);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除角色", key = "roleId", dict = AuthDictMapNames.DeleteDict)
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Tip remove(@RequestParam Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if(roleId.equals(Constants.ADMIN_ROLE_ID)){
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        roleService.delRoleById(roleId);
        
        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色
     */
    @RequestMapping(value = "/view/{roleId}")
    @ResponseBody
    public Tip view(@PathVariable Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        roleMapper.selectById(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = AuthDictMapNames.RoleDict)
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        roleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = roleMapper.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Integer userId) {
        String rolecode = userRoleService.getRoleCodes(userId);
        if (ToolUtil.isEmpty(rolecode)) {
            List<ZTreeNode> roleTreeList = roleMapper.roleTreeList();
            return roleTreeList;
        } else {
            String[] strArray = Convert.toStrArray(",", rolecode);
            List<ZTreeNode> roleTreeListByUserId = roleMapper.roleTreeListByRoleCode(strArray);
            return roleTreeListByUserId;
        }
    }

}
