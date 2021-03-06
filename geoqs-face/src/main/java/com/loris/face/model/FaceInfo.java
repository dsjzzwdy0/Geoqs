/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Face.java   
 * @Package com.loris.geoqs.face.model   
 * @Description: 本项目用于天津www.tigis.com.cn数据的存储、共享、处理等   
 * @author: www.tigis.com.cn    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津www.tigis.com.cn有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.face.model;

import com.loris.common.bean.AutoIdEntity;

/**   
 * @ClassName:  Face    
 * @Description: 实体信息   
 * @author: www.tigis.com.cn
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院www.tigis.com.cn内部传阅，禁止外泄以及用于其他的商业目 
 */
public class FaceInfo extends AutoIdEntity
{
	/** */
	private static final long serialVersionUID = 1L;

	private String userid;
	private String name;
	private byte[] facebytes;
	private String format;
	
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public byte[] getFacebytes()
	{
		return facebytes;
	}
	public void setFacebytes(byte[] facebytes)
	{
		this.facebytes = facebytes;
	}
	public String getFormat()
	{
		return format;
	}
	public void setFormat(String format)
	{
		this.format = format;
	}
	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "FaceInfo [userid=" + userid + ", name=" + name
				+ ", format=" + format + ", id=" + id + "]";
	}
}
