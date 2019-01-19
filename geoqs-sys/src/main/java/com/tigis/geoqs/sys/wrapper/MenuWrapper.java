package com.tigis.geoqs.sys.wrapper;

import java.util.Map;
import java.util.List;

import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.common.constant.state.IsMenu;
import com.tigis.geoqs.wrapper.BaseControllerWarpper;

public class MenuWrapper extends BaseControllerWarpper 
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
