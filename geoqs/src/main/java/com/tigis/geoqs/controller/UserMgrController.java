package com.tigis.geoqs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tigis.geoqs.common.annotation.BussinessLog;
import com.tigis.geoqs.common.annotation.Permission;
import com.tigis.geoqs.common.constant.Const;
import com.tigis.geoqs.common.constant.Dict;
import com.tigis.geoqs.common.constant.factory.ConstantFactory;
import com.tigis.geoqs.common.constant.state.ManagerStatus;
import com.tigis.geoqs.common.constant.tips.Tip;
import com.tigis.geoqs.common.exception.BizExceptionEnum;
import com.tigis.geoqs.common.exception.BussinessException;
import com.tigis.geoqs.common.log.LogObjectHolder;
import com.tigis.geoqs.core.DataScope;
import com.tigis.geoqs.security.ShiroKit;
import com.tigis.geoqs.security.ShiroUser;
import com.tigis.geoqs.sys.GeoqsProperties;
import com.tigis.geoqs.sys.dao.UserMapper;
import com.tigis.geoqs.sys.factory.UserFactory;
import com.tigis.geoqs.sys.model.User;
import com.tigis.geoqs.sys.transfer.UserDto;
import com.tigis.geoqs.sys.wrapper.UserWrapper;
import com.tigis.geoqs.util.ToolUtil;

import javax.annotation.Resource;
import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController
{
	@Resource
	private GeoqsProperties gunsProperties;

	@Resource
	private UserMapper userMapper;

	/**
	 * 跳转到查看管理员列表的页面
	 */
	@RequestMapping("")
	public String index()
	{
		return "user.system";
	}

	/**
	 * 跳转到查看管理员列表的页面
	 */
	@RequestMapping("/user_add")
	public String addView()
	{
		return "user_add.system";
	}

	/**
	 * 跳转到角色分配页面
	 */
	@Permission
	@RequestMapping("/role_assign/{userId}")
	public String roleAssign(@PathVariable Integer userId, Model model)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		
		User user = userMapper.selectById(userId);
		model.addAttribute("userId", userId);
		model.addAttribute("userAccount", user.getAccount());
		return "user_roleassign.system";
	}

	/**
	 * 跳转到编辑管理员页面
	 */
	@Permission
	@RequestMapping("/user_edit/{userId}")
	public String userEdit(@PathVariable Integer userId, Model model)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		assertAuth(userId);
		User user = userMapper.selectById(userId);
		model.addAttribute(user);
		model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
		model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
		LogObjectHolder.me().set(user);
		return "user_edit.system";
	}

	/**
	 * 跳转到查看用户详情页面
	 */
	@RequestMapping("/user_info")
	public String userInfo(Model model)
	{
		Integer userId = ShiroKit.getUser().getId();
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		User user = userMapper.selectById(userId);
		model.addAttribute(user);
		model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
		model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
		LogObjectHolder.me().set(user);
		return "user_view.system";
	}

	/**
	 * 跳转到修改密码界面
	 */
	@RequestMapping("/user_chpwd")
	public String chPwd()
	{
		return "user_chpwd.system";
	}

	/**
	 * 修改当前用户的密码
	 */
	@RequestMapping("/changePwd")
	@ResponseBody
	public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd)
	{
		if (!newPwd.equals(rePwd))
		{
			throw new BussinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
		}
		Integer userId = ShiroKit.getUser().getId();
		User user = userMapper.selectById(userId);
		String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
		if (user.getPassword().equals(oldMd5))
		{
			String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
			user.setPassword(newMd5);
			userMapper.updateById(user);
			return SUCCESS_TIP;
		}
		else
		{
			throw new BussinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
		}
	}

	/**
	 * 查询管理员列表
	 */
	@RequestMapping("/list")
	@Permission
	@ResponseBody
	public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid)
	{
		Integer userId = ShiroKit.getUser().getId();
		User user = userMapper.selectById(userId);
		DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope(user));
		List<Map<String, Object>> users = userMapper.selectUsers(dataScope, name, beginTime, endTime, deptid);
		return new UserWrapper(users).warp();
	}

	/**
	 * 添加管理员
	 */
	@RequestMapping("/add")
	@BussinessLog(value = "添加管理员", key = "account", dict = Dict.UserDict)
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Tip add(@Valid UserDto user, BindingResult result)
	{
		if (result.hasErrors())
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}

		// 判断账号是否重复
		User theUser = userMapper.getByAccount(user.getAccount());
		if (theUser != null)
		{
			throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
		}

		// 完善账号信息
		user.setSalt(ShiroKit.getRandomSalt(5));
		user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
		user.setStatus(ManagerStatus.OK.getCode());
		user.setCreatetime(new Date());

		userMapper.insert(UserFactory.createUser(user));
		return SUCCESS_TIP;
	}

	/**
	 * 修改管理员
	 *
	 * @throws NoPermissionException
	 */
	@RequestMapping("/edit")
	@BussinessLog(value = "修改管理员", key = "account", dict = Dict.UserDict)
	@ResponseBody
	public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException
	{
		if (result.hasErrors())
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		if (ShiroKit.hasRole(Const.ADMIN_NAME))
		{
			// userMapper.updateByPrimaryKeySelective(UserFactory.createUser(user));
			userMapper.updateById(UserFactory.createUser(user));
			return SUCCESS_TIP;
		}
		else
		{
			assertAuth(user.getId());
			ShiroUser shiroUser = ShiroKit.getUser();
			if (shiroUser.getId().equals(user.getId()))
			{
				// userMapper.updateByPrimaryKeySelective(UserFactory.createUser(user));
				userMapper.updateById(UserFactory.createUser(user));
				return SUCCESS_TIP;
			}
			else
			{
				throw new BussinessException(BizExceptionEnum.NO_PERMITION);
			}
		}
	}

	/**
	 * 删除管理员（逻辑删除）
	 */
	@RequestMapping("/delete")
	@BussinessLog(value = "删除管理员", key = "userId", dict = Dict.UserDict)
	@Permission
	@ResponseBody
	public Tip delete(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能删除超级管理员
		if (userId.equals(Const.ADMIN_ID))
		{
			throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
		}
		assertAuth(userId);
		userMapper.setStatus(userId, ManagerStatus.DELETED.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 查看管理员详情
	 */
	@RequestMapping("/view/{userId}")
	@ResponseBody
	public User view(@PathVariable Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		assertAuth(userId);
		return userMapper.selectById(userId);
	}

	/**
	 * 重置管理员的密码
	 */
	@RequestMapping("/reset")
	@BussinessLog(value = "重置管理员密码", key = "userId", dict = Dict.UserDict)
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Tip reset(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		assertAuth(userId);
		User user = userMapper.selectById(userId);
		user.setSalt(ShiroKit.getRandomSalt(5));
		user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
		// userMapper.updateByPrimaryKey(user);
		userMapper.updateById(user);
		return SUCCESS_TIP;
	}

	/**
	 * 冻结用户
	 */
	@RequestMapping("/freeze")
	@BussinessLog(value = "冻结用户", key = "userId", dict = Dict.UserDict)
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Tip freeze(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能冻结超级管理员
		if (userId.equals(Const.ADMIN_ID))
		{
			throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
		}
		assertAuth(userId);
		userMapper.setStatus(userId, ManagerStatus.FREEZED.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 解除冻结用户
	 */
	@RequestMapping("/unfreeze")
	@BussinessLog(value = "解除冻结用户", key = "userId", dict = Dict.UserDict)
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Tip unfreeze(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		assertAuth(userId);
		userMapper.setStatus(userId, ManagerStatus.OK.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 分配角色
	 */
	@RequestMapping("/setRole")
	@BussinessLog(value = "分配角色", key = "userId,roleIds", dict = Dict.UserDict)
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds)
	{
		if (ToolUtil.isOneEmpty(userId, roleIds))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能修改超级管理员
		if (userId.equals(Const.ADMIN_ID))
		{
			throw new BussinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
		}
		assertAuth(userId);
		userMapper.setRoles(userId, roleIds);
		return SUCCESS_TIP;
	}

	/**
	 * 上传图片(上传到项目的webapp/static/img)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	@ResponseBody
	public String upload(@RequestPart("file") MultipartFile picture)
	{
		String pictureName = UUID.randomUUID().toString() + ".jpg";
		try
		{
			String fileSavePath = gunsProperties.getFileUploadPath();
			picture.transferTo(new File(fileSavePath + pictureName));
		}
		catch (Exception e)
		{
			throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
		}
		return pictureName;
	}

	/**
	 * 判断当前登录的用户是否有操作这个用户的权限
	 */
	private void assertAuth(Integer userId)
	{
		User user = userMapper.selectById(userId);
		List<Integer> deptDataScope = ShiroKit.getDeptDataScope(user);
		Integer deptid = user.getDeptid();
		if (deptDataScope.contains(deptid))
		{
			return;
		}
		else
		{
			throw new BussinessException(BizExceptionEnum.NO_PERMITION);
		}

	}
}