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
import org.opencv.core.Mat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.loris.geoqs.face.FaceToolbox;
import com.loris.geoqs.face.constant.FaceRecognizerType;
import com.loris.geoqs.face.model.FaceInfo;
import com.loris.geoqs.face.service.FaceInfoService;
import com.loris.geoqs.face.util.ImageUtil;
import com.tigis.geoqs.wrapper.Rest;

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
@RequestMapping("/face")
public class FaceController
{
	private static Logger logger = Logger.getLogger(FaceController.class);
	
	/** 人脸检测工具  */
	protected FaceToolbox faceToolbox;
	
	/** 人脸数据服务类 */
	protected FaceInfoService faceInfoService;
	
	/** 人脸库数据 */
	protected List<FaceInfo> faceInfos;
	
	/**
	 * 控制器的初始化
	 */
	public FaceController()
	{
		initFaceToolBox();
	}
	
	@ResponseBody
	@RequestMapping("/regist")
	public Rest registUserFace(String userid, String name, MultipartFile file)
	{
		String info = "";
		try
		{
			byte[] bytes = file.getBytes();
			Mat image = ImageUtil.bytes2Mat(bytes);
			if(image == null || image.empty())
			{
				info = "The file is not a validate image file.";
				logger.warn(info);
			}
			else
			{
				Mat face = faceToolbox.getLabelFaceImage(image);
				if(face == null || face.empty())
				{
					info = "There are no face in the image file.";
					logger.warn(info);
				}
				else
				{				
					FaceInfo faceInfo = new FaceInfo();
					faceInfo.setUserid(userid);
					faceInfo.setName(name);
					faceInfo.setFormat(ImageUtil.FILE_IMAGE_EXT_PNG);
					faceInfo.setFacebytes(ImageUtil.mat2Bytes(face, faceInfo.getFormat()));
					
					if(faceInfoService.save(faceInfo))
					{
						faceInfos.add(faceInfo);
						this.updateFaceToolBox();
						return Rest.ok();
					}
					else
					{
						info = "Error when save the face image.";
					}
				}					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.warn("Error occured when process register.");
			info = e.toString();
		}
		return Rest.failure(info);
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
		
		faceInfos = faceInfoService.list();
		if(faceInfos == null || faceInfos.isEmpty())
		{
			logger.warn("The faces data is empty.");
			return;
		}
		
		faceToolbox.train(faceInfos);
	}
	
	/**
	 * 更新人脸识别工具
	 */
	protected void updateFaceToolBox()
	{
		faceToolbox.update(faceInfos);
	}
}
