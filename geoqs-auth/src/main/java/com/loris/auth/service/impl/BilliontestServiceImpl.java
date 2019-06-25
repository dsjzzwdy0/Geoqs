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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.auth.dao.BilliontestMapper;
import com.loris.auth.model.BillionTest;
import com.loris.auth.service.BilliontestService;
import com.loris.common.util.ToolUtil;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
@Service
public class BilliontestServiceImpl extends ServiceImpl<BilliontestMapper, BillionTest> implements BilliontestService {

	final protected int USER_STATUS_FORBIDDEN = 3;
    @Override
    public IPage<Map<String, Object>> selectBillionsByPage(IPage<BillionTest> page,String name, String beginTime, String endTime, Integer deptid)
    {
    	QueryWrapper<BillionTest> queryWrapper = new QueryWrapper<>();
//    	queryWrapper.ne("status", USER_STATUS_FORBIDDEN);
    	
    	if(StringUtils.isNotBlank(name))
    	{
    		queryWrapper.and(wrapper-> wrapper.like("phone", name).or().like("account", name).or().like("name", name));
    	}
    	
    	if(ToolUtil.isNotEmpty(deptid))
    	{
    		queryWrapper.and(wrapper->wrapper.eq("deptid", deptid).or()
    				.inSql(deptid.toString(), "select id from sys_dept where pids like CONCAT('%" + deptid + "%')"));
    	}
    	
    	if(StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime))
    	{
    		queryWrapper.and(wrapper->wrapper.between("createTime", beginTime + " 00:00:00", endTime + " 23:59:59"));
    	}
    	
    	return baseMapper.selectMapsPage(page,queryWrapper);
    }

}
