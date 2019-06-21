/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FaceService.java   
 * @Package com.loris.geoqs.face.service   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.geoqs.face.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.geoqs.face.model.FaceInfo;

/**   
 * @ClassName:  FaceService    
 * @Description: 脸孔服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface FaceInfoService extends IService<FaceInfo>
{
	/**
	 * 更新或增加Face数据
	 * @param face 
	 * @return 是否更新成功的标志
	 */
	boolean addOrUpdateFace(FaceInfo face);
	
	/**
	 * 获得有效的数据
	 * @return
	 */
	List<FaceInfo> getAllValidateFaces();
}
