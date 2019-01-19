package com.tigis.geoqs.sys.wrapper;

import java.util.List;
import java.util.Map;

import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.wrapper.BaseControllerWarpper;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class RoleWrapper extends BaseControllerWarpper {

    public RoleWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}
