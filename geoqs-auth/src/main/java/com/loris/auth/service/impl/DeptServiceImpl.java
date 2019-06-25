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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.auth.dao.DeptMapper;
import com.loris.auth.dao.DeptTypeMapper;
import com.loris.auth.dao.PersonMapper;
import com.loris.auth.dao.UserMapper;
import com.loris.auth.dao.UserRoleMapper;
import com.loris.auth.model.Dept;
import com.loris.auth.model.DeptType;
import com.loris.auth.model.Person;
import com.loris.auth.model.User;
import com.loris.auth.model.UserRole;
import com.loris.auth.service.DeptService;
import com.loris.common.constant.Constants;

import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 单位服务
 *
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

	@Resource
	private DeptMapper deptMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private DeptTypeMapper deptTypeMapper;

	@Resource
	private PersonMapper personMapper;

	@Override
	@Transactional
	public void deleteDept(Integer deptId) {
		Dept dept = deptMapper.selectById(deptId);

		QueryWrapper<Dept> wrapper = new QueryWrapper<>();
		wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
		List<Dept> subDepts = deptMapper.selectList(wrapper);
		for (Dept temp : subDepts) {
			temp.deleteById();
		}

		dept.deleteById();
	}

	@Override
	public IPage<Map<String, Object>> list(IPage<Dept> page, String condition, String unittype) {
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(condition)) {
			queryWrapper.and(wrapper -> wrapper.like("simplename", condition).or().like("fullname", condition));
		}
		if (StringUtils.isNotBlank(unittype)) {
			queryWrapper.and(wrapper -> wrapper.eq("unittype", unittype));
		}
		return baseMapper.selectMapsPage(page, queryWrapper);
	}

	@Override
	@Transactional
	public void registerDept(Dept dept, User user, String deptTypes) {
		// 插入账户信息表
		this.userMapper.insert(user);
		// 插入单位信息表
		dept.setAccountid(user.getId());
		this.deptMapper.insert(dept);
		// 插入单位类型信息表
		int deptId = dept.getId();
		String[] types = StrUtil.split(deptTypes, ",");
		for (String type : types) {
			DeptType deptType = new DeptType();
			deptType.setDeptid(deptId);
			deptType.setTypecode(type);
			this.deptTypeMapper.insert(deptType);
		}
		// 插入账户角色表
		UserRole userRole = new UserRole();
		userRole.setAccountid(user.getId());
		userRole.setRolecode(Constants.ROLE_ADMIN_DEPT_CODE);
		this.userRoleMapper.insert(userRole);
	}

	@Override
	@Transactional
	public void registerPerson(Person person, User user, String roleCodes) {
		// 插入账户信息
		this.userMapper.insert(user);
		// 插入人员信息
		person.setAccountid(user.getId());
		this.personMapper.insert(person);
		// 插入账户角色表
		String[] codes = StrUtil.split(roleCodes, ",");
		for (String rolecode : codes) {
			UserRole userRole = new UserRole();
			userRole.setAccountid(user.getId());
			userRole.setRolecode(rolecode);
			this.userRoleMapper.insert(userRole);
		}
	}
}
