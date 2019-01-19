package com.tigis.geoqs.sys.wrapper;


import java.util.Map;

import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.wrapper.BaseControllerWarpper;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        Integer creater = (Integer) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
