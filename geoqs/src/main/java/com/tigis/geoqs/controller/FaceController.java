/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FaceController.java   
 * @Package com.loris.soccer.controller   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.tigis.geoqs.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.loris.geoqs.face.FaceToolbox;
import com.loris.geoqs.face.constant.FaceRecognizerType;
import com.loris.geoqs.face.model.FaceInfo;
import com.loris.geoqs.face.service.FaceInfoService;

/**   
 * @ClassName:  FaceController    
 * @Description: 人脸识别服务类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
public class FaceController
{
	private static Logger logger = Logger.getLogger(FaceController.class);
	
	/** 人脸检测工具  */
	protected FaceToolbox faceToolbox;
	
	/** 人脸数据服务类 */
	protected FaceInfoService faceInfoService;
	
	/** 人脸库数据 */
	protected List<FaceInfo> faces;
	
	/**
	 * 控制器的初始化
	 */
	public FaceController()
	{
	}
	
	/**
	 * 初始化人脸检测工具
	 */
	protected void initFaceToolBox()
	{
		try
		{
			faceToolbox = FaceToolbox.create(null, FaceRecognizerType.Eigen);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.warn("Error occured when initialize the FaceToolBox.");
		}
		
		faces = faceInfoService.list();
		if(faces == null || faces.isEmpty())
		{
			logger.warn("The faces data is empty.");
			return;
		}
		
		faceToolbox.train(faces);
	}
	
	/**
	 * 更新人脸识别工具
	 */
	protected void updateFaceToolBox()
	{
		faceToolbox.update(faces);
	}
}
