package com.tigis.weekly.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigis.weekly.dao.DaylogMapper;
import com.tigis.weekly.model.Daylog;
import com.tigis.weekly.service.DaylogService;

@Service("daylogService")
public class DaylogServiceImpl extends ServiceImpl<DaylogMapper, Daylog> implements DaylogService
{

}
