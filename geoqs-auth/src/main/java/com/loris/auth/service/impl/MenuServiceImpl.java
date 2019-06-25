/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
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
package com.loris.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.auth.dao.MenuMapper;
import com.loris.auth.dao.RelationMapper;
import com.loris.auth.dao.view.MenuNodeEntityMapper;
import com.loris.auth.dao.view.MenuTreeGridMapper;
import com.loris.auth.dao.view.MenuTreeNodeMapper;
import com.loris.auth.dao.view.RoleResMapper;
import com.loris.auth.model.Menu;
import com.loris.auth.model.Relation;
import com.loris.auth.model.node.MenuNode;
import com.loris.auth.model.node.RoleRes;
import com.loris.auth.model.node.ZTreeNode;
import com.loris.auth.model.view.MenuNodeEntity;
import com.loris.auth.model.view.MenuTreeGridEntity;
import com.loris.auth.model.view.MenuTreeNode;
import com.loris.auth.service.MenuService;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    
    @Resource
    private RelationMapper relationMapper;
    
    @Resource
    private MenuTreeNodeMapper menuTreeMapper;
    
    @Resource
    private MenuNodeEntityMapper menuNodeMapper;
    
    @Resource
    private MenuTreeGridMapper menuTreegridMapper;
    
    @Resource
    private RoleResMapper roleResMapper;

    @Override
    @Transactional
    public void delMenu(Long menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.relationMapper.delete(new QueryWrapper<Relation>().eq("menuid", menuId));
        //.deleteRelationByMenu(menuId);
    }

    @Override
    @Transactional
    public void delMenuContainSubMenus(Long menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }

    @Override
    public List<Map<String, Object>> selectMenus( String condition, String level) {
    	QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("status", 1);
    	if(StringUtils.isNotBlank(condition))
    	{
    		queryWrapper.and(wrapper->wrapper.like("name", condition).or().like("code", condition));
    	}
    	if(StringUtils.isNotBlank(level))
    	{
    		queryWrapper.and(wrapper->wrapper.eq("levels", level));
    	}    	
    	return baseMapper.selectMaps(queryWrapper);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Integer roleId)
    {
    	QueryWrapper<Relation> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("roleid", roleId);    	
    	List<Relation> relations = relationMapper.selectList(queryWrapper);
    	List<Long> menuIds = new ArrayList<>();
    	for (Relation relation : relations)
		{
			menuIds.add(relation.getMenuid());
		}
        return menuIds;
    }

    @Override
    public List<ZTreeNode> menuTreeList()
    {
    	List<MenuTreeNode> nodes = menuTreeMapper.selectList(new QueryWrapper<MenuTreeNode>());
    	List<ZTreeNode> list = new ArrayList<>(nodes);
        return list;
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
    	QueryWrapper<MenuTreeNode> queryWrapper = new QueryWrapper<>();
    	queryWrapper.in("id", menuIds);
    	List<MenuTreeNode> nodes = menuTreeMapper.selectList(new QueryWrapper<MenuTreeNode>());
    	List<ZTreeNode> list = new ArrayList<>(nodes);
        return list;
    }

    @Override
    public int deleteRelationByMenu(Long menuId) {
    	QueryWrapper<Relation> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("menuid", menuId);
        return relationMapper.delete(queryWrapper);
    }

    @Override
    public List<String> getResUrlsByRoleId(Integer roleId) {
    	return getResUrlsByKeyVal("roleid",roleId);
    }

    @Override
    public List<MenuNode> getMenusByRoleCodes(List<String> roleCodes) {
    	QueryWrapper<MenuNodeEntity> queryWrapper = new QueryWrapper<>();
    	queryWrapper.in("rolecode", roleCodes);
    	List<? extends MenuNode> nodes = menuNodeMapper.selectList(queryWrapper);    	
    	List<MenuNode> list = new ArrayList<>(nodes);
        return list;
    }

	@Override
	public List<Map<String, Object>> selectMenusForgrid(String condition,String pid) {
		QueryWrapper<MenuTreeGridEntity> queryWrapper = new QueryWrapper<>();
    	if(StringUtils.isNotBlank(condition))
    	{
    		queryWrapper.and(wrapper->wrapper.like("name", condition).or().like("code", condition));
    		if(!pid.equals("0")) {//有关键字查询且当前不为根菜单才增加pid查询
    			queryWrapper.and(wrapper->wrapper.eq("parentid", pid));
    		}
    	} 
    	else {
    		queryWrapper.and(wrapper->wrapper.eq("parentid", pid));
    	}
    	List<Map<String, Object>> list =menuTreegridMapper.selectMaps(queryWrapper);    	
    	return list;
	}

	@Override
	public List<MenuNode> getMenusTree(boolean onlyMenu) {
		QueryWrapper<MenuTreeGridEntity> queryWrapper = new QueryWrapper<>();
    	if(onlyMenu) {
    		queryWrapper.eq("ismenu", "1");
    	}
    	List<? extends MenuNode> nodes = menuTreegridMapper.selectList(queryWrapper);    	
    	List<MenuNode> list = new ArrayList<>(nodes);
        return list;
	}

	@Override
	public List<String> getResUrlsByRoleCode(String roleCode) {
		// TODO Auto-generated method stub
		return getResUrlsByKeyVal("rolecode",roleCode);
	}
	
	private List<String> getResUrlsByKeyVal(String key,Object value) {
    	QueryWrapper<RoleRes> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq(key, value);
    	List<RoleRes> res = roleResMapper.selectList(queryWrapper);
    	
    	List<String> urls = new ArrayList<>();
    	for (RoleRes r : res)
		{
			urls.add(r.getUrl());
		}    	
        return urls;
    }
}
