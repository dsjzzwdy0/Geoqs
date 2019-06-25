/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ImageViewer.java   
 * @Package com.loris.geoqs.face   
 * @Description: 本项目用于天津www.tigis.com.cn数据的存储、共享、处理等   
 * @author: www.tigis.com.cn    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津www.tigis.com.cn有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.face.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
/**   
 * @ClassName:  ImageViewer    
 * @Description: 图像显示  
 * @author: www.tigis.com.cn
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院www.tigis.com.cn内部传阅，禁止外泄以及用于其他的商业目 
 */
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import com.loris.face.FaceToolbox;
import com.loris.face.constant.FaceRecognizerType;

/**
 * 
 * @ClassName:  ImageViewer    
 * @Description: 人脸检测测试图像程序 
 * @author: www.tigis.com.cn
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院www.tigis.com.cn内部传阅，禁止外泄以及用于其他的商业目
 */
public class ImageViewer
{
    private JLabel imageView;
    private String windowName;
	private JFileChooser fileChooser;
	
	/** 图像 */
	private Mat image;
	
	/** 人脸检测工具  */
	FaceToolbox faceToolbox;
	
	
	static
	{
		//加载本地动态链接库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
 
    /**
     * 如果使用junit测试时调用该方法，图像会一闪而过，可通过sleep()等方式暂时显示
     * @param
     */
    public ImageViewer() {
    	this("人脸检测");
    }
 
 
    /**
     * @param image      要显示的mat
     * @param windowName 窗口标题
     */
    public ImageViewer(String windowName) {
        this.windowName = windowName;
    }
 
    /**
     * 图片显示
     */
    public void imshow()
    {
        setSystemLookAndFeel();
        //Image loadedImage = ImageUtil.mat2BufferedImage(image);
        JFrame frame = createJFrame(windowName, 800, 600);
        fileChooser = new JFileChooser();
        //imageView.setIcon(new ImageIcon(loadedImage));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
    /**
     * 设置系统外观数据形象
     */
    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //Make sure we have nice window decorations.
            JFrame.setDefaultLookAndFeelDecorated(true);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 创建显示窗体
     * @param windowName
     * @param width
     * @param height
     * @return
     */
    private JFrame createJFrame(String windowName, int width, int height) 
    {
        JFrame frame = new JFrame(windowName);
        
        imageView = new JLabel();
        final JScrollPane imageScrollPane = new JScrollPane(imageView);
        imageScrollPane.setPreferredSize(new Dimension(width, height));
        frame.add(imageScrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //创建并添加菜单栏
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        //创建并添加各菜单，注意：菜单的快捷键是同时按下Alt键和字母键，方法setMnemonic('F')是设置快捷键为Alt +F
        JMenu menuFile = new JMenu("文件(F)"), menuEdit = new JMenu("编辑(E)"), menuView = new JMenu("查看(V)");
        menuFile.setMnemonic('F'); 
        menuEdit.setMnemonic('E'); 
        menuView.setMnemonic('V'); 
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuView);
        
        
        
        JMenuItem itemOpen = new JMenuItem ("打开(O)");
        itemOpen.setMnemonic('O');
        menuFile.add(itemOpen);
        
        itemOpen.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				openImageFile();
			}
		});

        JMenuItem itemCheck = new JMenuItem("人脸检测(C)");
        itemCheck.setMnemonic('C');
        menuEdit.add(itemCheck);
        itemCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				checkFaceImage();
			}
		});
        
        JMenuItem itemCut = new JMenuItem("人脸识别(T)");
        itemCut.setMnemonic('T');
        menuEdit.add(itemCut);
        itemCut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				recognizeFaceImage();
			}
		});
        
        try
        {
        	faceToolbox = FaceToolbox.create(null, FaceRecognizerType.Eigen);
        }
        catch(Exception exception)
        {
        	exception.printStackTrace();
        }
        
        return frame;
    }
    
    /**
     * 打开图像文件进行处理
     */
    protected void openImageFile()
    {
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter= new FileNameExtensionFilter("打开 *.png *.jpg", "png","jpg");
		fileChooser.setFileFilter(filter);
		
		if(fileChooser.showDialog(new JLabel(), "选择") == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			//System.out.println("文件:" + file.getAbsolutePath());
			openImageFile(file.getAbsolutePath());
		}
	}
    
    /**
     * 显示图像
     * @param imagePath
     */
    protected void openImageFile(String imagePath)
    {
    	image = ImageUtil.read(imagePath);
    	showImage(image);
    }
    
    /**
     * 显示图像
     * @param image
     */
    protected void showImage(Mat image)
    {
    	if(image != null && !image.empty())
    	{
    		BufferedImage image2 = ImageUtil.mat2BufferedImage(image);
    		imageView.setIcon(new ImageIcon(image2));
    	}
    }
    
    /**
     * 人脸检测
     */
    protected void checkFaceImage()
    {
    	if(image == null || image.empty())
    	{
    		System.out.println("There are no image data, please select a image file first.");
    		return;
    	}
    	
    	Mat dest = faceToolbox.checkFacesAndDrawRectangle(image, new Scalar(0, 255, 255));
    	showImage(dest);
    }
    
    /**
     * 人脸检测和识别
     */
    protected void recognizeFaceImage()
    {
    	if(image == null || image.empty())
    	{
    		System.out.println("There are no image data, please select a image file first.");
    		return;
    	}
    	Mat dest = faceToolbox.getSingleFaceImage(image);
    	showImage(dest);
    }
    
    /**
     * 开始运行图像处理器
     */
    public static void showFrame()
	{
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	ImageViewer viewer = new ImageViewer();
            	viewer.imshow();
            }
        });
	}
}