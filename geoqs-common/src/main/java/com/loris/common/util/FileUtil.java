package com.loris.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;

public class FileUtil
{

	private static Logger log = Logger.getLogger(FileUtil.class);

	/**
	 * NIO way
	 */
	public static byte[] toByteArray(String filename)
	{

		File f = new File(filename);
		if (!f.exists())
		{
			log.error("文件未找到！" + filename);
			throw new BussinessException(BizExceptionEnum.FILE_NOT_FOUND);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try
		{
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0)
			{
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		}
		catch (IOException e)
		{
			throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
		}
		finally
		{
			try
			{
				channel.close();
			}
			catch (IOException e)
			{
				throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
			}
			try
			{
				fs.close();
			}
			catch (IOException e)
			{
				throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
			}
		}
	}

	/**
	 * 将文本文件中的内容读入到buffer中
	 * 
	 * @param buffer
	 *            buffer
	 * @param filePath
	 *            文件路径
	 * @throws IOException
	 *             异常
	 * @date 2013-1-7
	 */
	public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException
	{
		InputStream is = new FileInputStream(filePath);
		String line; // 用来保存每行读取的内容
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is)))
		{
			line = reader.readLine(); // 读取第一行
			while (line != null)
			{ // 如果 line 为空说明读完了
				buffer.append(line); // 将读到的内容添加到 buffer 中
				buffer.append("\n"); // 添加换行符
				line = reader.readLine(); // 读取下一行
			}
		}
		finally
		{
			is.close();
		}
	}
}