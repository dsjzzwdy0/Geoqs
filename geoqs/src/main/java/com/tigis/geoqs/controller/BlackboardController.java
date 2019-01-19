package com.tigis.geoqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigis.geoqs.sys.dao.NoticeMapper;

import java.util.List;
import java.util.Map;

/**
 * 总览信息
 *
 * @author fengshuonan
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    NoticeMapper noticeMapper;

    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model)
    {
        List<Map<String, Object>> notices = noticeMapper.list(null);
        model.addAttribute("noticeList", notices);
        return "blackboard.system";
    }
}
