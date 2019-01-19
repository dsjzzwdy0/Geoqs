package com.tigis.geoqs.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.tigis.geoqs.common.constant.state.Order;
import com.tigis.geoqs.common.constant.tips.SuccessTip;
import com.tigis.geoqs.common.exception.ServiceException;
import com.tigis.geoqs.common.exception.enums.CoreExceptionEnum;
import com.tigis.geoqs.common.page.PageInfoWrapper;
import com.tigis.geoqs.common.page.PageReq;
import com.tigis.geoqs.core.support.HttpKit;
import com.tigis.geoqs.util.ToolUtil;
import com.tigis.geoqs.wrapper.BaseControllerWarpper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class BaseController
{

	protected static String SUCCESS = "SUCCESS";
	protected static String ERROR = "ERROR";

	protected static String REDIRECT = "redirect:";
	protected static String FORWARD = "forward:";

	protected static SuccessTip SUCCESS_TIP = new SuccessTip();

	protected HttpServletRequest getHttpServletRequest()
	{
		return HttpKit.getRequest();
	}

	protected HttpServletResponse getHttpServletResponse()
	{
		return HttpKit.getResponse();
	}

	protected HttpSession getSession()
	{
		return HttpKit.getRequest().getSession();
	}

	protected HttpSession getSession(Boolean flag)
	{
		return HttpKit.getRequest().getSession(flag);
	}

	protected String getPara(String name)
	{
		return HttpKit.getRequest().getParameter(name);
	}

	protected void setAttr(String name, Object value)
	{
		HttpKit.getRequest().setAttribute(name, value);
	}

	protected Integer getSystemInvokCount()
	{
		return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
	}

	/**
	 * 把service层的分页信息，封装为bootstrap table通用的分页封装
	 */
	protected <T> PageInfoWrapper<T> packForBT(List<T> page)
	{
		return new PageInfoWrapper<T>(page);
	}

	public PageReq defaultPage()
	{
		HttpServletRequest request = HttpKit.getRequest();
		int limit = Integer.valueOf(request.getParameter("limit"));
		int offset = Integer.valueOf(request.getParameter("offset"));
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		PageReq pageReq = new PageReq(limit, offset, sort, order);
		if (ToolUtil.isEmpty(sort))
		{
			pageReq.setOpenSort(false);
		}
		else
		{
			pageReq.setOpenSort(true);
			if (Order.ASC.getDes().equals(order))
			{
				pageReq.setAsc(true);
			}
			else
			{
				pageReq.setAsc(false);
			}
		}
		return pageReq;
	}

	/**
	 * 包装一个list，让list增加额外属性
	 */
	protected Object warpObject(BaseControllerWarpper warpper)
	{
		return warpper.warp();
	}

	/**
     * 删除所有cookie
     */
    protected void deleteAllCookie() {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            Cookie temp = new Cookie(cookie.getName(), "");
            temp.setMaxAge(0);
            this.getHttpServletResponse().addCookie(temp);
        }
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<InputStreamResource> renderFile(String fileName, String filePath) {
        try {
            final FileInputStream inputStream = new FileInputStream(filePath);
            return renderFile(fileName, inputStream);
        } catch (FileNotFoundException e) {
            throw new ServiceException(CoreExceptionEnum.FILE_READING_ERROR);
        }
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<InputStreamResource> renderFile(String fileName, byte[] fileBytes) {
        return renderFile(fileName, new ByteArrayInputStream(fileBytes));
    }

    /**
     * 返回前台文件流
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return
     * @author 0x0001
     */
    protected ResponseEntity<InputStreamResource> renderFile(String fileName, InputStream inputStream) {
        InputStreamResource resource = new InputStreamResource(inputStream);
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }
}
