package com.loris.auth.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.auth.dao.UserRoleMapper;
import com.loris.auth.model.UserRole;
import com.loris.auth.service.UserRoleService;

/**
 * <p>
 * 账号角色信息表 服务实现类
 * </p>
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
	@Override
	public String getRoleCodes(Integer accountId) {
		// TODO Auto-generated method stub
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("accountid", accountId);
        List<UserRole> list= baseMapper.selectList(queryWrapper);
        if(list==null || list.size()==0)return "";
        String roleCodes="";
        for(UserRole userRole:list) {
        	if(roleCodes.equals("")) {
        		roleCodes=userRole.getRolecode();
        	}else {
        		roleCodes+=","+userRole.getRolecode();
        	}
        }
        return roleCodes;
	}
}
