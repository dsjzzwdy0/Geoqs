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
package com.loris.auth.service;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.auth.model.Dept;
import com.loris.auth.model.Person;
import com.loris.auth.model.User;

import java.util.Map;

/**
 * 单位服务
 *
 */
public interface DeptService extends IService<Dept> {

    /**
     * 删除部门
     */
    void deleteDept(Integer deptId);
    /**
     * 注册部门
     * @param dept 单位信息
     * @param user  用户信息
     * @param deptTypes  单位类型，多种类型以“,”分割
     */
    void registerDept(Dept dept,User user,String deptTypes);
    /**
     * 注册人员
     * @param person  人员信息
     * @param account 账号信息
     * @param roleCode 人员角色编号
     */
    void registerPerson(Person person,User account,String roleCode);
    /**
     * 获取所有部门列表
     */
    IPage <Map<String, Object>> list(@Param("page") IPage<Dept> page,@Param("condition") String condition,@Param("unittype") String unittype);
}
