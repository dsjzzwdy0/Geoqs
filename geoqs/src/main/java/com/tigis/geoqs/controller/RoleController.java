package com.tigis.geoqs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tigis.geoqs.common.annotation.BussinessLog;
import com.tigis.geoqs.common.annotation.Permission;
import com.tigis.geoqs.common.constant.Const;
import com.tigis.geoqs.common.constant.Dict;
import com.tigis.geoqs.common.constant.cache.Cache;
import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.common.constant.tips.Tip;
import com.tigis.geoqs.common.exception.BizExceptionEnum;
import com.tigis.geoqs.common.exception.BussinessException;
import com.tigis.geoqs.common.log.LogObjectHolder;
import com.tigis.geoqs.common.node.ZTreeNode;
import com.tigis.geoqs.core.cache.CacheKit;
import com.tigis.geoqs.sys.dao.RoleMapper;
import com.tigis.geoqs.sys.dao.UserMapper;
import com.tigis.geoqs.sys.model.Role;
import com.tigis.geoqs.sys.model.User;
import com.tigis.geoqs.sys.service.RoleService;
import com.tigis.geoqs.sys.wrapper.RoleWrapper;
import com.tigis.geoqs.util.Convert;
import com.tigis.geoqs.util.ToolUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
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

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return "role.system";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd() {
        return "role_add.system";
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
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        LogObjectHolder.me().set(role);
        return "role_edit.system";
    }

    /**
     * 跳转到角色分配
     */
    @Permission
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return "role_assign.system";
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
    @BussinessLog(value = "添加角色", key = "name", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setId(null);
        roleMapper.insert(role);
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改角色", key = "name", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip edit(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
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
    @BussinessLog(value = "删除角色", key = "roleId", dict = Dict.DeleteDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip remove(@RequestParam Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if(roleId.equals(Const.ADMIN_ROLE_ID)){
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
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = Dict.RoleDict)
    @Permission(Const.ADMIN_NAME)
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
        User theUser = userMapper.selectById(userId);
        String roleid = theUser.getRoleid();
        if (ToolUtil.isEmpty(roleid)) {
            List<ZTreeNode> roleTreeList = roleMapper.roleTreeList();
            return roleTreeList;
        } else {
            String[] strArray = Convert.toStrArray(",", roleid);
            List<ZTreeNode> roleTreeListByUserId = roleMapper.roleTreeListByRoleId(strArray);
            return roleTreeListByUserId;
        }
    }

}
