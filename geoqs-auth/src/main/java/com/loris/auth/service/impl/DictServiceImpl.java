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
package com.loris.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.auth.dao.DictMapper;
import com.loris.auth.model.Dict;
import com.loris.auth.service.DictService;
import com.loris.common.exception.ServiceException;
import com.loris.common.exception.enums.BizExceptionEnum;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.loris.auth.factory.MutiStrFactory.*;

import java.util.List;
import java.util.Map;

/**
 * 字典服务
 *
 * @author fengshuonan
 * @Date 2018/10/15 下午11:39
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    @Transactional
    public void addDict(String dictCode, String dictName, String dictTips, String dictValues)
    {    	
        //判断有没有该字典
        List<Dict> dicts = dictMapper.selectList(new QueryWrapper<Dict>().eq("code", dictCode).eq("pid", 0));
        if (dicts != null && dicts.size() > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }
        //添加字典
        Dict dict = new Dict();
        dict.setName(dictName);
        dict.setCode(dictCode);
        dict.setTips(dictTips);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //解析dictValues
        List<Map<String, String>> items = parseKeyValue(dictValues);
        //添加字典条目
        for (Map<String, String> item : items) {
            String code = item.get(MUTI_STR_CODE);
            String name = item.get(MUTI_STR_NAME);
            String num = item.get(MUTI_STR_NUM);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setCode(code);
            itemDict.setName(name);

            try {
                itemDict.setNum(Integer.valueOf(num));
            } catch (NumberFormatException e) {
                throw new ServiceException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    @Transactional
    public void editDict(Integer dictId, String dictCode, String dictName, String dictTips, String dicts) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictCode, dictName, dictTips, dicts);
    }

    @Override
    @Transactional
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
    	QueryWrapper<Dict> dictEntityWrapper = new QueryWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }

    @Override
    public List<Dict> selectByCode(String code) {
    	QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("code", code);
    	return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public IPage<Map<String, Object>> list(IPage<Dict> page,String condition) {
    	QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("pid", 0);
    	if(StringUtils.isNotBlank(condition))
    	{
    		queryWrapper.and(wrapper->wrapper.like("name", condition).or().like("code", condition));
    	}  	
    	return baseMapper.selectMapsPage(page,queryWrapper);
    }

	@Override
	public List<Dict> selectByPCode(String pcode) {
		// TODO Auto-generated method stub
		List<Dict> list=this.selectByCode(pcode);
		Integer dictId=list.get(0).getId();
		return baseMapper.selectList(new QueryWrapper<Dict>().eq("pid", dictId));
	}
}
