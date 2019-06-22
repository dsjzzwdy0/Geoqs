/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ImageUtil.java   
 * @Package com.loris.geoqs.face   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.geoqs.face.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @ClassName: ImageUtil
 * @Description: 图像应用的类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.loris.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ImageUtil
{
	public static final String FILE_IMAGE_EXT_JPG = ".jpg";
	public static final String FILE_IMAGE_EXT_PNG = ".png";
	public static final String FILE_IMAGE_EXT_BMP = "bmp";
	
	/**
	 * 读取文件数据
	 * @param imagePath
	 * @return
	 */
	public static Mat read(String imagePath)
	{
		return Imgcodecs.imread(imagePath);
	}
	
	/**
	 * 图像灰度化
	 * @param image
	 * @return
	 */
	public static Mat rgb2Gray(Mat image)
	{
		Mat dest = new Mat();
		Imgproc.cvtColor(image, dest, Imgproc.COLOR_BGR2GRAY);
		return dest;
	}
	
	/**
	 * 图像重新设置高和宽
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static Mat imageResize(Mat image, int width, int height)
	{
		Size size = new Size(width, height);
		Mat mat = new Mat();
		Imgproc.resize(image, mat, size);
		return mat;
	}

	/**
	 * 图像的剪切
	 * @param image
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 * @return
	 */
	public static Mat imageCut(Mat image, int posX, int posY, int width, int height) 
	{
		// 截取的区域：参数,坐标X,坐标Y,截图宽度,截图长度
		Rect rect = new Rect(posX, posY, width, height);
		// 两句效果一样
		Mat sub = image.submat(rect); // Mat sub = new Mat(image,rect);
		Mat mat = new Mat();
		Size size = new Size(width, height);
		Imgproc.resize(sub, mat, size);			// 将人脸进行截图并保存
		return mat;
	}
	
	/**
	 * byte数组转换成opencv能够处理的图像
	 * @param bytes
	 * @return
	 */
	public static Mat bytes2Mat(byte[] bytes)
	{
		Mat mat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
		return mat;
	}
	
	/**
	 * Java图像转换为opencv的图像
	 * @param image
	 * @param mtype
	 * @return
	 */
	public static Mat bufferedImage2Mat(BufferedImage image, int mtype)
	{
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(image.getHeight(), image.getWidth(), mtype);
        mat.put(0, 0, pixels);
        return mat;
	}
	
	/**
	 * Mat转换成byte数组
	 *
	 * @param matrix 要转换的Mat
	 * @param fileExtension 格式为 ".jpg", ".png", etc
	 * @return
	 */
	public static byte[] mat2Bytes(Mat matrix, String fileExtension)
	{
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(fileExtension, matrix, mob);
		byte[] byteArray = mob.toArray();
		return byteArray;
	}
	
	/**
	 * Mat 转换为Java能够识别与处理的图像
	 * @param matrix
	 * @return
	 */
	public static BufferedImage mat2BufferedImage(Mat matrix) 
    {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1)
        {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
        byte[] buffer = new byte[bufferSize];
        matrix.get(0, 0, buffer); // 获取所有的像素点
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }
}
