package com.loris.auth.security.service.impl;

import cn.hutool.core.convert.Convert;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loris.auth.factory.ConstantFactory;
import com.loris.auth.model.User;
import com.loris.auth.security.ShiroUser;
import com.loris.auth.security.exception.InvalidUserException;
import com.loris.auth.security.service.UserAuthService;
import com.loris.auth.service.MenuService;
import com.loris.auth.service.UserRoleService;
import com.loris.auth.service.UserService;
import com.loris.common.constant.state.ManagerStatus;
import com.loris.common.context.ApplicationContextHelper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserAuthServiceServiceImpl implements UserAuthService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MenuService menuService;

    public static UserAuthService me() {
        return ApplicationContextHelper.getBean(UserAuthService.class);
    }

    @Override
    public User user(String account) {

        User user = userService.getByAccount(account);

        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        //账号未审核通过
        if (user.getRegstatus()!=1) {
            throw new InvalidUserException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId());
        shiroUser.setAccount(user.getAccount());
        shiroUser.setDeptId(user.getDeptid());
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));
        shiroUser.setName(user.getName());

        String[] roleArray = Convert.toStrArray(userRoleService.getRoleCodes(user.getId()));
        List<String> roleList = new ArrayList<String>();
        List<String> roleNameList = new ArrayList<String>();
        for (String roleCode : roleArray) {
            roleList.add(roleCode);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleCode));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        return menuService.getResUrlsByRoleId(roleId);
    }
    @Override
    public List<String> findPermissionsByRoleCode(String roleCode) {
        return menuService.getResUrlsByRoleCode(roleCode);
    }
    
    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();

        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

	@Override
	public String findRoleNameByRoleCode(String roleCode) {
		return ConstantFactory.me().getSingleRoleName(roleCode);
	}

}
