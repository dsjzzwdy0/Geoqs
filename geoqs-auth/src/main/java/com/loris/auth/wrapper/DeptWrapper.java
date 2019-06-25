package com.loris.auth.wrapper;

import java.util.Map;

import com.loris.auth.factory.ConstantFactory;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.wrapper.BaseWrapper;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DeptWrapper extends BaseWrapper {

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
