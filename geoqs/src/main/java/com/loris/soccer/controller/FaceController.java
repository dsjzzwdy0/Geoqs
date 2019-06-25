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
package com.loris.soccer.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.Rest;
import com.loris.face.FaceToolbox;
import com.loris.face.constant.FaceRecognizerType;
import com.loris.face.model.FaceInfo;
import com.loris.face.service.FaceInfoService;
import com.loris.face.util.ImageUtil;


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
public class FaceController extends BaseController
{
	private static Logger logger = Logger.getLogger(FaceController.class);
	
	/** 人脸检测工具  */
	protected FaceToolbox faceToolbox;
	
	/** 人脸数据服务类 */
	@Autowired
	protected FaceInfoService faceInfoService;
		
	/** 判断是否初始化 */
	protected boolean initialized = false;
	
	/** 识别之后的阈值 */
	protected float threshold = 3000.0f;
	
	/**
	 * 浏览头像图片数据
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/getpic", method = RequestMethod.GET)
	public void getFacePic(String id, HttpServletResponse response) throws IOException
	{
		FaceInfo faceInfo = faceInfoService.getById(id);
		if(faceInfo == null)
		{
			response.sendRedirect(getHttpServletRequest().getContextPath() + "/content/img/girl.gif");
			return;
		}
		
		response.setContentType(getMimeType(faceInfo.getFormat()));		
		OutputStream os = response.getOutputStream();
		os.write(faceInfo.getFacebytes());
		os.close();
	}
	
	@ResponseBody
	@RequestMapping("/recognize")
	public Rest recognizeFace(@RequestParam("file") MultipartFile file)
	{
		if(!initialized)
		{
			initFaceToolBox();
		}
		
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
				//image = ImageUtil.rgb2Gray(image);
				Mat face = faceToolbox.getSingleFaceImage(image);
				if(face == null || face.empty())
				{
					info = "There are no face in the image file.";
					logger.warn(info);
				}
				else
				{
					//logger.info("Channels: " + face.channels() + ", Image Type: " + face.type());
					FaceToolbox.FaceResult result = faceToolbox.predictByFaceImage(face);
					logger.info(result);
					if(result.getConfidence() > threshold)
					{						
						info = "没有识别出有效的人脸图像";
					}
					else
					{
						FaceInfo faceInfo = faceInfoService.getById(result.getLabel());
						faceInfo.setFacebytes(null);
						return Rest.okData(faceInfo);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			info = e.toString();
		}
		return Rest.failure(info);
	}


	@ResponseBody
	@RequestMapping("/regist")
	public Rest registUserFace(@RequestParam("file") MultipartFile file, String userid, String name)
	{
		if(!initialized)
		{
			initFaceToolBox();
		}
		String info = "";
		try
		{
			byte[] bytes = file.getBytes();
			//output("d:/test.jpg", bytes);
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
						faceToolbox.addFace(faceInfo);
						this.updateFaceToolBox();
						faceInfo.setFacebytes(null);
						return Rest.okData(faceInfo);
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
	
	//Only for test.
	protected void output(String path, byte[] bytes)
	{
		try
		{
			FileOutputStream outputStream = new FileOutputStream(new File(path));
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化人脸检测工具
	 */
	protected void initFaceToolBox()
	{
		if(initialized)
		{
			return;
		}
		
		try
		{
			faceToolbox = FaceToolbox.create(null, FaceRecognizerType.Eigen);
			initialized = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.warn("Error occured when initialize the FaceToolBox.");
		}
		
		List<FaceInfo> faceInfos = faceInfoService.list();
		if(faceInfos == null || faceInfos.isEmpty() || faceInfos.size() == 0)
		{
			logger.warn("The faces data is empty.");
			return;
		}
		
		faceToolbox.train(faceInfos);
	}
	
	/**
	 * 设置图像的格式
	 * @param format
	 * @return
	 */
	protected String getMimeType(String format)
	{
		String t = format.replace(".", "");
		if(StringUtils.isEmpty(t))
		{
			t = "jpeg";
		}
		return String.format("image/%s", t);
	}
	
	/**
	 * 更新人脸识别工具
	 */
	protected void updateFaceToolBox()
	{
		faceToolbox.train();
	}
}
