package com.loris.auth.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loris.auth.model.User;

/**
 * <p>
 * 人员表 Mapper 接口
 * </p>
 *
 */
public interface UserMapper extends BaseMapper<User> {
	List<Map<String, Object>> list(@Param("page") IPage<Map<String, Object>> page,@Param("condition") String condition ,@Param("deptId") String deptId,@Param("persontype") String persontype);
}