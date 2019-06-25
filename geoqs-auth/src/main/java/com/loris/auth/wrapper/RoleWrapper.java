package com.loris.auth.wrapper;

import java.util.List;
import java.util.Map;

import com.loris.common.web.wrapper.BaseWrapper;

/**
 * 角色列表的包装类
 *
 */
public class RoleWrapper extends BaseWrapper {

    public RoleWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        
    }

}
