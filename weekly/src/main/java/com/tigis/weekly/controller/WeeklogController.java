package com.tigis.weekly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tigis.geoqs.wrapper.Rest;
import com.tigis.weekly.model.Weeklog;
import com.tigis.weekly.service.WeeklogService;

@Controller
@RequestMapping("/weeklog")
public class WeeklogController
{
	@Autowired
	private WeeklogService weeklogService;
	
	/**
	 * 查询我的周报记录
	 * @param pageNum 当前页数
	 * @param pageCount 每页的记录数
	 * @param model 模型视图
	 * @return 视图页面
	 */
	@RequestMapping("/myweek/{pageNum}/{pageCount}")
	public String myWeeklog(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageCount") Integer pageCount, Model model)
	{
		Page<Weeklog> page = new Page<>(pageNum, pageCount);
		QueryWrapper<Weeklog> queryWrapper = new QueryWrapper<>();
		
		IPage<Weeklog> list = weeklogService.page(page, queryWrapper);
		model.addAttribute("list", list);
		model.addAttribute("total", list.getTotal());// 总条数
		model.addAttribute("pageSize", list.getPages());// 每页的总容量
		model.addAttribute("pageNum", list.getCurrent());
		
		return "myWeeklogList";
	}
	
	
	@RequestMapping("/edit")
	public Rest editWeeklog(Weeklog weeklog)
	{
		return Rest.ok();
	}
}
