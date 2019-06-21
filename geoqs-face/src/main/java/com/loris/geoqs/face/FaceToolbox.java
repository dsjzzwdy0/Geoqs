/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FaceUtil.java   
 * @Package com.loris.geoqs.face   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.geoqs.face;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.loris.geoqs.face.constant.FaceConstant;
import com.loris.geoqs.face.constant.FaceRecognizerType;
import com.loris.geoqs.face.model.FaceInfo;
import com.loris.geoqs.face.util.ImageUtil;


/**   
 * @ClassName:  FaceUtil    
 * @Description: 用于人脸识别与人脸检测  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class FaceToolbox
{
	private static Logger logger = Logger.getLogger(FaceToolbox.class);
	
	/** 人脸检测类 */
	protected CascadeClassifier faceDetector;
	
	/** 人脸识别类*/
	protected FaceRecognizer recognizer;
	
	/** The face width */
	protected int width = -1;
	
	/** The face height */
	protected int height = -1;
	
	static
	{
		//加载本地动态链接库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static class FaceResult
	{
		private int label;
		private double confidence;
		
		public FaceResult()
		{
		}
		
		public FaceResult(int label, double confidence)
		{
			this.label = label;
			this.confidence = confidence;
		}
		
		public int getLabel()
		{
			return label;
		}
		public void setLabel(int label)
		{
			this.label = label;
		}
		public double getConfidence()
		{
			return confidence;
		}
		public void setConfidence(double confidence)
		{
			this.confidence = confidence;
		}
	}
	
	/**
	 * Create the new instance of FaceToolBox
	 */
	private FaceToolbox()
	{
	}
	
	/**
	 * 创建人脸识别工具类
	 * @return
	 */
	public static FaceToolbox create(Map<String, String> params, FaceRecognizerType type) throws IOException
	{				
		try
		{
			FaceToolbox instance = new FaceToolbox();
			instance.initialize(FaceConstant.HAARCASCADE_FACE_XML_FILE, params, type);
			return instance;
		}
		catch (Exception e) {
			//instance = null;
			throw e;
		}
	}
	
	/**
	 * 初始化数据文件
	 * @param xmlFaceFile
	 * @throws IOException
	 */
	protected void initialize(String xmlFaceFile, Map<String, String> params, 
			FaceRecognizerType type) throws IOException
	{
		boolean isWindows = FaceConstant.isWindowsSystem();
		String path = this.getClass().getClassLoader().getResource("").getPath();
		if(isWindows)
		{
			path = path.replaceFirst("/", "");
		}
		if(path.endsWith("test-classes/"))
		{
			logger.debug("This is in test class.");
			path = path.replaceFirst("test-classes/", "classes/");
		}
		String filePath = path + "face/" + xmlFaceFile;
		logger.debug(filePath);
		faceDetector = new CascadeClassifier(filePath);
		
		if(type == FaceRecognizerType.Eigen)
		{
			recognizer = EigenFaceRecognizer.create();
		}
		else if(type == FaceRecognizerType.Fisher)
		{
			recognizer = FisherFaceRecognizer.create();
		}
		else if(type == FaceRecognizerType.LBPH)
		{
			recognizer = LBPHFaceRecognizer.create();
		}
		
		setDefaultRect();
	}
	
	/**
	 * 训练数据模型
	 * @param faces
	 */
	public void train(List<FaceInfo> faces)
	{
		if(width <= 0 || height <= 0)
		{
			setDefaultRect();
		}
		train(faces, width, height);
	}
	
	/**
	 * 训练数据模型
	 * @param faces
	 * @param width
	 * @param height
	 */
	public void train(List<FaceInfo> faces, int width, int height)
	{
		this.width = width;
		this.height = height;
		int size = faces.size();
		final List<Mat> images = new ArrayList<>();
        final Mat labels = new Mat(size, 1, CvType.CV_32SC1);
        setImagesAndLabels(faces, images, labels, width, height);
        
		recognizer.train(images, labels);
	}
	
	/**
	 * 更新数据库
	 * @param faces
	 * @param width
	 * @param height
	 */
	public void update(List<FaceInfo> faces)
	{
		int size = faces.size();
		final List<Mat> images = new ArrayList<>();
        final Mat labels = new Mat(size, 1, CvType.CV_32SC1);
        setImagesAndLabels(faces, images, labels, width, height);
		recognizer.update(images, labels);
	}
	
	/**
	 * 设置图像数据与标签的类
	 * @param faces
	 * @param images
	 * @param labels
	 * @param width
	 * @param height
	 */
	private void setImagesAndLabels(List<FaceInfo> faces, List<Mat> images, Mat labels, int width, int height)
	{
		StreamSupport.stream(faces.spliterator(), false).forEach(face -> {
            Mat img = faceToMat(face);
            Size rect = img.size();
            
            //重新设置图像的宽与高
            if(rect.width != width || rect.height != height)
            {
            	img = ImageUtil.imageResize(img,  width, height);
            }            
            images.add(img);
            labels.put(images.size(), 0, Integer.getInteger(face.getId())); 
        });
	}
	
	/**
	 * 检测人脸并把人脸用框标识出来
	 * @param image
	 * @return
	 */
	public Mat checkFacesAndDrawRectangle(Mat image, Scalar color)
	{
		MatOfRect rects = checkFaces(image);
		if(rects == null || rects.elemSize() <= 0)
		{
			logger.warn("There are no face to be detected.");
			return null;
		}
		
		Mat dest = image.clone();
		
		// 制图将图填充到image中
		for (Rect rect : rects.toArray())
		{
			Imgproc.rectangle(dest, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					color, 3);
			Imgproc.putText(dest, "Human Face", new Point(rect.x, rect.y), 1, 1.0, color, 1,
					Imgproc.LINE_AA, false);
		}
		return dest;
	}
	
	/**
	 * 检测图片中的人脸
	 * @param image
	 * @return
	 */
	public MatOfRect checkFaces(Mat image)
	{
		MatOfRect faceDetections = new MatOfRect();
		
		// 进行人脸检测
		faceDetector.detectMultiScale(image, faceDetections);
		if(faceDetections.empty())
		{
			return null;
		}		
		return faceDetections;
	}
	
	/**
	 * 获得单一的人脸图像
	 * @param image
	 * @return
	 */
	public Mat getSingleFaceImage(Mat image)
	{
		MatOfRect rects = checkFaces(image);
		if(rects == null || rects.empty() || rects.toArray().length > 1)
		{
			logger.info("The image is null, has no face data or has more than one faces");
			return null;
		}
		Rect rect = rects.toArray()[0];
		logger.info("width: " + rect.width + ", height: " + rect.height);
		return ImageUtil.imageCut(image, rect.x, rect.y, rect.width, rect.height);
	}
	
	/**
	 * 获得用于标注的单一人脸图像，在这里需要化成灰度图像
	 * @param image
	 * @return
	 */
	public Mat getLabelFaceImage(Mat image)
	{
		Mat dest = getSingleFaceImage(image);
		if(dest == null || dest.empty())
		{
			return null;
		}
		dest = ImageUtil.rgb2Gray(dest);
		dest = ImageUtil.imageResize(dest, width, height);
		return dest;
	}
	
	/**
	 * 预测数据
	 * @param face
	 * @return
	 */
	public FaceResult predictByFaceImage(Mat face)
	{
		if(recognizer == null || recognizer.empty())
		{
			logger.warn("There are no FaceRecognizer to be set, please train the FaceRecognizer first.");
			return null;
		}
		if(face == null || face.empty())
		{
			logger.warn("There are no face image to be set, please check the image data.");
			return null;
		}
		if(face.type() == CvType.CV_8UC3 || face.channels() > 1)
		{
			face = ImageUtil.rgb2Gray(face);
		}
		Size rect = face.size();
		//重新设置图像的宽与高
        if(rect.width != width || rect.height != height)
        {
        	face = ImageUtil.imageResize(face, width, height);
        }
        
        int labels[] = new int[1];
        double[] confidences = new double[1];        
        recognizer.predict(face, labels, confidences);
		return new FaceResult(labels[0], confidences[0]);
	}
	

	public CascadeClassifier getFaceDetector()
	{
		return faceDetector;
	}

	public void setFaceDetector(CascadeClassifier faceDetector)
	{
		this.faceDetector = faceDetector;
	}

	public FaceRecognizer getRecognizer()
	{
		return recognizer;
	}

	public void setRecognizer(FaceRecognizer recognizer)
	{
		this.recognizer = recognizer;
	}
	
	public void setDefaultRect()
	{
		this.width = 64;
		this.height = 64;
	}
	
	/**
     * Converts Blob to mat.
     *
     * @param face face.
     * @return Mat from blob.
     */
    private Mat faceToMat(final FaceInfo face) 
    {
        try {
            Mat image = ImageUtil.bytes2Mat(face.getFacebytes());
            return image;
        }
        catch (final Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
