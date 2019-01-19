package com.tigis.geoqs.sys.wrapper;

import java.util.List;
import java.util.Map;

import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.sys.model.Dict;
import com.tigis.geoqs.util.ToolUtil;
import com.tigis.geoqs.wrapper.BaseControllerWarpper;

/**
 * 字典列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DictWarpper extends BaseControllerWarpper {

    public DictWarpper(Object list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        Integer id = (Integer) map.get("id");
        List<Dict> dicts = ConstantFactory.me().findInDict(id);
        if(dicts != null){
            for (Dict dict : dicts) {
                detail.append(dict.getNum() + ":" +dict.getName() + ",");
            }
            map.put("detail", ToolUtil.removeSuffix(detail.toString(),","));
        }
    }

}
