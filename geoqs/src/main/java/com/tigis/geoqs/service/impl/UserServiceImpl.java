package com.tigis.geoqs.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigis.geoqs.dao.UserMapper;
import com.tigis.geoqs.model.User;
import com.tigis.geoqs.service.UserService;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
{

}
