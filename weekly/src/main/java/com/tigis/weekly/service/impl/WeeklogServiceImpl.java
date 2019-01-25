package com.tigis.weekly.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigis.weekly.dao.WeeklogMapper;
import com.tigis.weekly.model.Weeklog;
import com.tigis.weekly.service.WeeklogService;

@Service("weeklogService")
public class WeeklogServiceImpl extends ServiceImpl<WeeklogMapper, Weeklog> implements WeeklogService
{

}
