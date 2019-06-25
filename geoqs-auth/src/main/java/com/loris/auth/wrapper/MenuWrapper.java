package com.loris.auth.wrapper;

import java.util.Map;
import java.util.List;

import com.loris.auth.factory.ConstantFactory;
import com.loris.common.constant.tips.IsMenu;
import com.loris.common.web.wrapper.BaseWrapper;

public class MenuWrapper extends BaseWrapper 
{

    public MenuWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }
}
