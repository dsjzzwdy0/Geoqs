/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.loris.auth.service;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.auth.model.Dict;

import java.util.List;
import java.util.Map;

/**
 * 字典服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface DictService extends IService<Dict> {

    /**
     * 添加字典
     */
    void addDict(String dictCode, String dictName, String dictTips, String dictValues);

    /**
     * 编辑字典
     */
    void editDict(Integer dictId, String dictCode, String dictName, String dictTips, String dicts);

    /**
     * 删除字典
     */
    void delteDict(Integer dictId);

    /**
     * 根据编码获取词典列表
     */
    List<Dict> selectByCode(@Param("code") String code);
    /**
     * 根据父编码获取所有的字典值
     * @param pcode
     * @return
     */
    List<Dict> selectByPCode(@Param("pcode") String pcode);
    /**
     * 查询字典列表
     */
    IPage<Map<String, Object>> list(@Param("page") IPage<Dict> page,@Param("condition") String conditiion);

}
