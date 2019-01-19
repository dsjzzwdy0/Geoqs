package com.tigis.geoqs.sys.wrapper;

import java.util.Map;

import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.util.ToolUtil;
import com.tigis.geoqs.wrapper.BaseControllerWarpper;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DeptWrapper extends BaseControllerWarpper {

    public DeptWrapper(Object list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
