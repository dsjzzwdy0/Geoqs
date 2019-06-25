package com.loris.auth.wrapper;


import java.util.Map;

import com.loris.auth.factory.ConstantFactory;
import com.loris.common.web.wrapper.BaseWrapper;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseWrapper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        Integer creater = (Integer) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
