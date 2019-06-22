/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @App.java   
 * @Package com.tigis.geoqscom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.tigis.geoqs;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import com.loris.geoqs.face.util.ImageViewer;

/**   
 * @ClassName:  App.java   
 * @Description: 这个类是属于测试作用   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class App
{
	static
	{
		try
		{
			// 加载本地动态链接库
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		}
		catch (UnsatisfiedLinkError ignore)
		{
			// 使用spring-dev-tools之后，上下文会被加载多次，所以这里会抛出链接库已被加载的异常。
			// 有这个异常则说明链接库已被加载，直接吞掉异常即可
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			// testMat();
			
			testView();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void testView() throws IOException
	{
		ImageViewer.showFrame();
	}
	
	
	public static void testMat() throws IOException
	{
		Mat labels = new Mat(6, 1, CvType.CV_32SC1);
		labels.put(0, 0, 10);
		labels.put(1, 0, 20);
		labels.put(2, 0, 30);
		labels.put(3, 0, 40);
		
		Mat labels2 = new Mat(4, 1, CvType.CV_32SC1);
		labels.copyTo(labels2);
		
		Mat label3 = Mat.zeros(new Size(1, 6), CvType.CV_32SC1);
		//labels2.reshape(0, 2);
		//labels2.put(3, 0, 40);
		
		int[] n = new int[1];
		System.out.println("width: " + labels2.width() + ", height: " + labels2.height());
		
		System.out.println(label3.get(0, 0, n) + ", " + n[0]);
		System.out.println(label3.get(1, 0, n) + ", " + n[0]);
		System.out.println(label3.get(2, 0, n) + ", " + n[0]);
		System.out.println(label3.get(3, 0, n) + ", " + n[0]);
		System.out.println(label3.get(4, 0, n) + ", " + n[0]);
		System.out.println(label3.get(5, 0, n) + ", " + n[0]);
	}
}
