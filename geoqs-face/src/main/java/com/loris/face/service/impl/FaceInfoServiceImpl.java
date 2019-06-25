/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FaceServiceImpl.java   
 * @Package com.loris.geoqs.face.service.impl   
 * @Description: 本项目用于天津www.tigis.com.cn数据的存储、共享、处理等   
 * @author: www.tigis.com.cn    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津www.tigis.com.cn有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.face.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.face.dao.FaceInfoMapper;
import com.loris.face.model.FaceInfo;
import com.loris.face.service.FaceInfoService;

/**   
 * @ClassName:  FaceServiceImpl    
 * @Description: 人脸数据服务   
 * @author: www.tigis.com.cn
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院www.tigis.com.cn内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("faceInfoService")
public class FaceInfoServiceImpl extends ServiceImpl<FaceInfoMapper, FaceInfo> implements FaceInfoService
{
	/**
	 *  (non-Javadoc)
	 * @see com.loris.face.service.FaceInfoService#addOrUpdateFace(com.loris.face.model.FaceInfo)
	 */
	@Override
	public boolean addOrUpdateFace(FaceInfo face)
	{
		if(StringUtils.isAllBlank(face.getId()))
		{
			return baseMapper.insert(face) > 0;
		}
		else
		{
			return baseMapper.updateById(face) > 0;
		}
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.face.service.FaceInfoService#getAllValidateFaces()
	 */
	@Override
	public List<FaceInfo> getAllValidateFaces()
	{
		QueryWrapper<FaceInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.isNotNull("facebytes");
		queryWrapper.isNotNull("userid");
		return baseMapper.selectList(queryWrapper);
	}

}
