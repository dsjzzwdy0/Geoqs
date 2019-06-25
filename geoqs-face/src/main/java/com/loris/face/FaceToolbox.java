/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FaceUtil.java   
 * @Package com.loris.geoqs.face   
 * @Description: 本项目用于天津www.tigis.com.cn数据的存储、共享、处理等   
 * @author: www.tigis.com.cn    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津www.tigis.com.cn有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.face;

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

import com.loris.face.constant.FaceConstant;
import com.loris.face.constant.FaceRecognizerType;
import com.loris.face.model.FaceInfo;
import com.loris.face.util.ImageUtil;


/**   
 * @ClassName:  FaceUtil    
 * @Description: 用于人脸识别与人脸检测  
 * @author: www.tigis.com.cn
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院www.tigis.com.cn内部传阅，禁止外泄以及用于其他的商业目 
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
	
	/** 已知的脸 */
	protected KnowFaces knowFaces = new KnowFaces();
	
	/** 已知的脸 */
	class KnowFaces
	{
		List<Mat> faces = new ArrayList<>();
		List<Integer> labels = new ArrayList<>();
		
		public void addFace(int label, Mat face)
		{
			face = normalizeFace(face);
			labels.add(label);
			faces.add(face);
			
			//logger.info("Channels: " + face.channels() + ", Image Type: " + face.type());
		}
		
		public void remove(int label)
		{
			int size = labels.size();
			for(int i = size - 1; i >= 0; i --)
			{
				if(labels.get(i) == label)
				{
					labels.remove(i);
					faces.remove(i);
				}
			}
		}
		
		public Mat getLables()
		{
			int size = size();
			final Mat ls = new Mat(size, 1, CvType.CV_32SC1);
			for (int i = 0; i < size; i ++)
			{
				ls.put(i, 0, labels.get(i));
			}
			return ls;
		}
		
		public void clear()
		{
			labels.clear();
			faces.clear();
		}
		
		public int size()
		{
			if(labels.size() != faces.size()) throw new IllegalArgumentException("The label size and face size is not equal.");
			return labels.size();
		}
	}
	
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

		@Override
		public String toString()
		{
			return "FaceResult [label=" + label + ", confidence=" + confidence + "]";
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
		logger.info(filePath);
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
	 * 删除某一标签的人脸数据
	 * @param label
	 */
	public void removeFace(int label)
	{
		knowFaces.remove(label);
	}
	
	/**
	 * 添加人脸列表信息
	 * @param faceInfos
	 */
	public void addFaces(List<FaceInfo> faceInfos)
	{
		StreamSupport.stream(faceInfos.spliterator(), false).forEach(face -> {
			addFace(face);
		});
	}
	
	/**
	 * 添加人脸信息
	 * @param faceinfo
	 */
	public void addFace(FaceInfo faceinfo)
	{
		Mat face = faceToMat(faceinfo);
		int label = Integer.parseInt(faceinfo.getId());
		
        this.addFace(label, face);
	}
	
	/**
	 * 添加具有标签的人脸
	 * @param label
	 * @param face
	 */
	public void addFace(int label, Mat face)
	{
        knowFaces.addFace(label, face);
	}
	
	/**
	 * 训练数据模型
	 * @param faces
	 */
	public void train()
	{
		if(knowFaces.size() <= 0)
		{
			logger.warn("There are no faces in the database.");
			return;
		}
		recognizer.train(knowFaces.faces, knowFaces.getLables());
	}
	
	/**
	 * 训练数据模型
	 * @param faceInfos
	 */
	public void train(List<FaceInfo> faceInfos)
	{
		update(faceInfos);
	}
	
	/**
	 * 更新数据库
	 * @param faces
	 * @param width
	 * @param height
	 */
	public void update(List<FaceInfo> faces)
	{
		knowFaces.clear();
		addFaces(faces);
		train();
	}
	
	/**
	 * 设置图像数据与标签的类
	 * @param faces
	 * @param images
	 * @param labels
	 * @param width
	 * @param height
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
            logger.info("Face: " + face);
            labels.put(images.size(), 0, Integer.parseInt(face.getId())); 
        });
	}*/
	
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
		/*if(image.channels() > 1)
        {
			image = ImageUtil.rgb2Gray(image);
        }*/
		
		MatOfRect rects = checkFaces(image);
		if(rects == null || rects.empty() || rects.toArray().length > 1)
		{
			logger.info("The image is null, has no face data or has more than one faces");
			return null;
		}
		Rect rect = rects.toArray()[0];
		//logger.info("width: " + rect.width + ", height: " + rect.height);
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
		dest = normalizeFace(dest);
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

		face = normalizeFace(face);        
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
	 * 标准化图像
	 * @param face
	 * @return
	 */
	protected Mat normalizeFace(Mat face)
	{
		//logger.info("Normalize the face object.");
        if(face.channels() > 1)
        {
        	face = ImageUtil.rgb2Gray(face);
        }
		Size rect = face.size();
		//重新设置图像的宽与高
        if(rect.width != width || rect.height != height)
        {
        	face = ImageUtil.imageResize(face, width, height);
        }
        return face;
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
