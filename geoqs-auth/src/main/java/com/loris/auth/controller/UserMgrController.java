package com.loris.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loris.auth.dao.DeptMapper;
import com.loris.auth.dao.RoleMapper;
import com.loris.auth.dao.UserMapper;
import com.loris.auth.dictmap.factory.AuthDictMapNames;
import com.loris.auth.factory.ConstantFactory;
import com.loris.auth.factory.UserFactory;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.model.BillionTest;
import com.loris.auth.model.User;
import com.loris.auth.security.ShiroKit;
import com.loris.auth.security.ShiroUser;
import com.loris.auth.service.BilliontestService;
import com.loris.auth.service.UserRoleService;
import com.loris.auth.service.UserService;
import com.loris.auth.transfer.UserDto;
import com.loris.auth.wrapper.UserWrapper;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.annotation.Permission;
import com.loris.common.constant.Constants;
import com.loris.common.constant.state.ManagerStatus;
import com.loris.common.constant.tips.Tip;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;
import com.loris.common.pagination.PageFactory;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;

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
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController
{
	@Resource
	DeptMapper deptMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RoleMapper roleMapper;
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserRoleService userRoleService;

	@Resource
	private BilliontestService billionTestService;
	/**
	 * 跳转到人员列表的页面
	 */
	@RequestMapping("")
	public String index(Model model)
	{
		List<Map<String, Object>> units=deptMapper.selectDeptNameAndIds();
		model.addAttribute("units",units);
		List<Map<String, Object>> personTypes=roleMapper.selectRoleNameAndCodes(null);
		model.addAttribute("personTypes",personTypes);
		return "/beetl/system/user";
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
		
		User user = userService.getById(userId);
		model.addAttribute("userId", userId);
		model.addAttribute("userAccount", user.getAccount());
		return "/beetl/system/user_roleassign";
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
		User user = userService.getById(userId);
		model.addAttribute(user);
		model.addAttribute("roleName", ConstantFactory.me().getRoleName(userRoleService.getRoleCodes(userId)));
//		model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
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
		User user = userService.getById(userId);
		model.addAttribute(user);
		model.addAttribute("roleName", ConstantFactory.me().getRoleName(userRoleService.getRoleCodes(userId)));
//		model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
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
		User user = userService.getById(userId);
		String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
		if (user.getPassword().equals(oldMd5))
		{
			String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
			user.setPassword(newMd5);
			userService.updateById(user);
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
	public Object list(String condition,String deptId, String personType)
	{
		Page<Map<String,Object>> page=new PageFactory<Map<String,Object>>().defaultPage();
    	long startTime=System.currentTimeMillis();
    	List<Map<String,Object>> list = userMapper.list(page,condition,deptId,personType);
		page.setRecords(list);
		long stopTime=System.currentTimeMillis();
		return super.packForJqgrid(page,stopTime-startTime);
	}
	/**
	 * 百万数据测试列表
	 */
	@RequestMapping("/billionlist")
	@ResponseBody
	public Object billionlist(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid)
	{
		Page<BillionTest> page=new PageFactory<BillionTest>().defaultPage();
		long startTime=System.currentTimeMillis();
		IPage<Map<String,Object>> records = billionTestService.selectBillionsByPage(page, name, beginTime, endTime, deptid);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> result=(List<Map<String,Object>>)new UserWrapper(records.getRecords()).warp();
		records.setRecords(result);
		long stopTime=System.currentTimeMillis();
		return super.packForJqgrid((Page<Map<String,Object>>)records,stopTime-startTime);
	}
	/**
	 * 添加管理员
	 */
	@RequestMapping("/add")
	@BussinessLog(value = "添加管理员", key = "account", dict = AuthDictMapNames.UserDict)
	@Permission(Constants.ROLE_ADMIN_SUPER_CODE)
	@ResponseBody
	public Tip add(@Valid UserDto user, BindingResult result)
	{
		if (result.hasErrors())
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}

		// 判断账号是否重复
		User theUser = userService.getByAccount(user.getAccount());
		if (theUser != null)
		{
			throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
		}

		// 完善账号信息
		user.setSalt(ShiroKit.getRandomSalt(5));
		user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
		user.setStatus(ManagerStatus.OK.getCode());
		user.setCreatetime(new Date());

		userService.save(UserFactory.createUser(user));
		return SUCCESS_TIP;
	}

	/**
	 * 修改管理员
	 *
	 * @throws NoPermissionException
	 */
	@RequestMapping("/edit")
	@BussinessLog(value = "修改管理员", key = "account", dict = AuthDictMapNames.UserDict)
	@ResponseBody
	public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException
	{
		if (result.hasErrors())
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		if (ShiroKit.hasRole(Constants.ROLE_ADMIN_SUPER_CODE))
		{
			// userMapper.updateByPrimaryKeySelective(UserFactory.createUser(user));
			userService.updateById(UserFactory.createUser(user));
			return SUCCESS_TIP;
		}
		else
		{
			assertAuth(user.getId());
			ShiroUser shiroUser = ShiroKit.getUser();
			if (shiroUser.getId().equals(user.getId()))
			{
				// userMapper.updateByPrimaryKeySelective(UserFactory.createUser(user));
				userService.updateById(UserFactory.createUser(user));
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
	@BussinessLog(value = "删除管理员", key = "userId", dict = AuthDictMapNames.UserDict)
	@Permission
	@ResponseBody
	public Tip delete(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能删除超级管理员
		if (userId.equals(Constants.ADMIN_ID))
		{
			throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
		}
		assertAuth(userId);
		userService.setStatus(userId, ManagerStatus.DELETED.getCode());
		
		
		
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
		return userService.getById(userId);
	}

	/**
	 * 重置管理员的密码
	 */
	@RequestMapping("/reset")
	@BussinessLog(value = "重置管理员密码", key = "userId", dict = AuthDictMapNames.UserDict)
	@Permission(Constants.ROLE_ADMIN_SUPER_CODE)
	@ResponseBody
	public Tip reset(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		assertAuth(userId);
		User user = userService.getById(userId);
		user.setSalt(ShiroKit.getRandomSalt(5));
		user.setPassword(ShiroKit.md5(Constants.DEFAULT_PWD, user.getSalt()));
		// userMapper.updateByPrimaryKey(user);
		userService.updateById(user);
		return SUCCESS_TIP;
	}

	/**
	 * 冻结用户
	 */
	@RequestMapping("/freeze")
	@BussinessLog(value = "冻结用户", key = "userId", dict = AuthDictMapNames.UserDict)
	@Permission(Constants.ROLE_ADMIN_SUPER_CODE)
	@ResponseBody
	public Tip freeze(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能冻结超级管理员
		if (userId.equals(Constants.ADMIN_ID))
		{
			throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
		}
		assertAuth(userId);
		userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 解除冻结用户
	 */
	@RequestMapping("/unfreeze")
	@BussinessLog(value = "解除冻结用户", key = "userId", dict = AuthDictMapNames.UserDict)
	@Permission(Constants.ROLE_ADMIN_SUPER_CODE)
	@ResponseBody
	public Tip unfreeze(@RequestParam Integer userId)
	{
		if (ToolUtil.isEmpty(userId))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		assertAuth(userId);
		userService.setStatus(userId, ManagerStatus.OK.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 分配角色
	 */
	@RequestMapping("/setRole")
	@BussinessLog(value = "分配角色", key = "userId,roleIds", dict = AuthDictMapNames.UserDict)
	@Permission(Constants.ROLE_ADMIN_SUPER_CODE)
	@ResponseBody
	public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds)
	{
		if (ToolUtil.isOneEmpty(userId, roleIds))
		{
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能修改超级管理员
		if (userId.equals(Constants.ADMIN_ID))
		{
			throw new BussinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
		}
		
		userService.setRoles(userId, roleIds);
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
			String fileSavePath ="";// soccerProperties.getFileUploadPath();
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
//		User user = userService.getById(userId);
//		List<Integer> deptDataScope = ShiroKit.getDeptDataScope(user);
//		Integer deptid = user.getDeptid();
//		if (deptDataScope.contains(deptid))
//		{
//			return;
//		}
//		else
//		{
//			throw new BussinessException(BizExceptionEnum.NO_PERMITION);
//		}
	}
}
