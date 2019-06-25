package com.loris.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.auth.dao.MenuMapper;
import com.loris.auth.dictmap.factory.AuthDictMapNames;
import com.loris.auth.factory.ConstantFactory;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.model.Menu;
import com.loris.auth.model.node.MenuNode;
import com.loris.auth.model.node.TreeNode;
import com.loris.auth.model.node.ZTreeNode;
import com.loris.auth.service.MenuService;
import com.loris.auth.util.TreeJson;
import com.loris.auth.wrapper.MenuTreeGridWrapper;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.annotation.Permission;
import com.loris.common.constant.Constants;
import com.loris.common.constant.state.MenuStatus;
import com.loris.common.constant.tips.SuccessTip;
import com.loris.common.constant.tips.Tip;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;
import com.loris.common.support.BeanKit;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController 
{
	@Resource
	MenuMapper menuMapper;
	
    @Resource
    MenuService menuService;

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return "/beetl/system/menu";
    }

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_add/{menuId}")
    public String menuAdd(@PathVariable Integer menuId, Model model) {
    	if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	Map<String, Object> menuMap = new HashMap<String, Object>();
    	String menuName=ConstantFactory.me().getMenuName(menuId.longValue());
    	if(StringUtils.isBlank(menuName)) {
    		menuName="无";
    	}
    	menuMap.put("pcodeName",menuName);
    	menuMap.put("pcode", menuId);
        model.addAttribute("menu", menuMap);
        return "/beetl/system/menuForm";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping(value = "/menu_edit/{menuId}")
    public String menuEdit(@PathVariable Integer menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = menuService.getById(menuId);

        //获取父级菜单的id
        Menu temp = new Menu();
        temp.setCode(menu.getPcode());
        Menu pMenu = menuService.getOne(new QueryWrapper<Menu>(temp));

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            //设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(temp.getCode()));
        model.addAttribute("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return "/beetl/system/menuForm";
    }

    /**
     * 修该菜单
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改菜单", key = "name", dict = AuthDictMapNames.MenuDict)
    @ResponseBody
    public Tip edit(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //设置父级菜单编号
        menuSetPcode(menu);
        menuService.updateById(menu);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单树
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping(value = "/tree",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String tree(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
    	return menuTreeJson(true);
    }
    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String selectMenuTreeList() {
        return menuTreeJson(false);
    }
    /**
     * 返回菜单构造树
     * @param onlyMenu 是否只显示菜单不显示按钮
     * @return
     */
    private String menuTreeJson(boolean onlyMenu) {
    	List<MenuNode> menus = menuService.getMenusTree(true);
    	List<TreeNode> treeList=new ArrayList<TreeNode>();
    	TreeNode tree=new TreeNode();
    	tree.setId("0");
		tree.setText("顶级");
		tree.setValue("0");
		tree.setParentId("");
		tree.setIsexpand(true);
		tree.setComplete(true);
		tree.setHasChildren(menus.size()>0?true:false);
		treeList.add(tree);
    	for(MenuNode node :menus){
    		tree=new TreeNode();
    		long childCount=menus.stream().filter(item->item.getParentId().equals(node.getId())).count();
    		Boolean hasChildren=childCount==0?false:true;
    		tree.setId(node.getId().toString());
    		tree.setText(node.getName());
    		tree.setValue(tree.getId());
    		tree.setParentId(node.getParentId().toString());
    		tree.setImg(node.getIcon());
    		tree.setIsexpand(false);
    		tree.setComplete(true);
    		tree.setHasChildren(hasChildren);
    		treeList.add(tree);
    	}
    	return TreeJson.TreeToJson(treeList,"");
	}
    /**
     * 获取菜单列表
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String pid) {
//        List<Map<String, Object>> menus = menuService.selectMenus(menuName, level);
    	if(StringUtils.isBlank(pid)) {
    		pid="0";
    	}
    	List<Map<String, Object>> menus = menuService.selectMenusForgrid(menuName, pid);
        return super.warpObject(new MenuTreeGridWrapper(menus));
    }

    /**
     * 新增菜单
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping(value = "/add")
    @BussinessLog(value = "菜单新增", key = "name", dict = AuthDictMapNames.MenuDict)
    @ResponseBody
    public Tip add(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new BussinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        //设置父级菜单编号
        menuSetPcode(menu);

        menu.setStatus(MenuStatus.ENABLE.getCode());
        menuService.save(menu);
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Constants.ROLE_ADMIN_SUPER_CODE)
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除菜单", key = "menuId", dict = AuthDictMapNames.DeleteDict)
    @ResponseBody
    public Tip remove(@RequestParam Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName((Long)menuId.longValue()));

        menuService.delMenuContainSubMenus(menuId.longValue());
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}")
    @ResponseBody
    public Tip view(@PathVariable Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        menuService.getById(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = menuService.menuTreeList();
        return roleTreeList;
    }
    /**
     * 获取当前角色菜单列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public Tip menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds = menuService.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = this.menuMapper.menuTreeList();
            return new SuccessTip(roleTreeList);
        } else {
            List<ZTreeNode> roleTreeListByUserId =this.menuMapper.menuTreeListByMenuIds(menuIds);
            return new SuccessTip(roleTreeListByUserId);
        }
    }

    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(@Valid Menu menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            int code = Integer.parseInt(menu.getPcode());
            Menu pMenu = menuService.getById(code);
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new BussinessException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
    }

}
