package com.loris.auth.service;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.auth.model.UserRole;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 查询某账号的角色编号信息
     */
    String getRoleCodes(@Param("accountid") Integer accountId);
}
