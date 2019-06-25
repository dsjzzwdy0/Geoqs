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
package com.loris.auth.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loris.auth.model.Dept;

/**
 * <p>
 * 单位表 Mapper 接口
 * </p>
 *
 */
public interface DeptMapper extends BaseMapper<Dept> {
	/**
	 * 分页列表所有的单位
	 * @param page
	 * @param condition 查询关键字
	 * @param unittype  单位类型
	 * @return
	 */
	List<Map<String, Object>> list(@Param("page") IPage<Map<String, Object>> page,@Param("condition") String condition ,@Param("unittype") String unittype);
	/**
	 * 获得所有单位名称和ID号
	 * @return
	 */
	List<Map<String, Object>> selectDeptNameAndIds();
}