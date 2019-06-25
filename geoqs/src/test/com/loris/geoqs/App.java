package com.loris.geoqs;

import org.opencv.core.Core;

import com.loris.face.util.ImageViewer;

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
			ImageViewer.showFrame();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}