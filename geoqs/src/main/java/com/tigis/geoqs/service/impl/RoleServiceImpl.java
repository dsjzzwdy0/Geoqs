package com.tigis.geoqs.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigis.geoqs.dao.RoleMapper;
import com.tigis.geoqs.model.Role;
import com.tigis.geoqs.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService
{

}
