/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FaceConstant.java   
 * @Package com.loris.geoqs.face.constant   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.geoqs.face.constant;

/**   
 * @ClassName:  FaceConstant    
 * @Description: 常量定义  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class FaceConstant
{	
	final public static String HAARCASCADE_FACE_XML_FILE = "haarcascade_frontalface_alt.xml";
	
	/**
	 * 判断是否是Windows系统
	 * @return
	 */
	public static boolean isWindowsSystem()
	{
		String os = System.getProperty("os.name");
		if(os.startsWith("win") || os.startsWith("Win"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
