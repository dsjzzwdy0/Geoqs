package com.loris.auth.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.auth.dao.PersonMapper;
import com.loris.auth.dao.RoleMapper;
import com.loris.auth.dictmap.factory.AuthDictMapNames;
import com.loris.auth.log.LogManager;
import com.loris.auth.log.LogTaskFactory;
import com.loris.auth.model.Dept;
import com.loris.auth.model.Dict;
import com.loris.auth.model.Person;
import com.loris.auth.model.User;
import com.loris.auth.model.node.MenuNode;
import com.loris.auth.model.virtual.DeptRegisterParam;
import com.loris.auth.model.virtual.PersonRegisterParam;
import com.loris.auth.security.ShiroKit;
import com.loris.auth.security.ShiroUser;
import com.loris.auth.service.DeptService;
import com.loris.auth.service.DictService;
import com.loris.auth.service.MenuService;
import com.loris.auth.service.UserService;
import com.loris.common.annotation.BussinessLog;
import com.loris.common.exception.BussinessException;
import com.loris.common.exception.enums.BizExceptionEnum;
import com.loris.common.util.ToolUtil;
import com.loris.common.web.BaseController;
import com.loris.common.constant.state.ManagerStatus;

import static com.loris.common.support.HttpKit.getIp;
/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController
{
	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;

	@Resource
    DictService dictService;
	
	@Resource
    DeptService deptService;
	
	@Resource
    private PersonMapper personMapper;
	
	@Resource
	private RoleMapper roleMapper;
	
	/**
	 * 跳转到主页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model)
	{
		// 获取菜单列表
		List<String> roleList = ShiroKit.getUser().getRoleList();
		if (roleList == null || roleList.size() == 0)
		{
			ShiroKit.getSubject().logout();
			model.addAttribute("tips", "该用户没有角色，无法登陆");
			return "/login";
		}
		List<MenuNode> menus = menuService.getMenusByRoleCodes(roleList);
		List<MenuNode> titles = MenuNode.buildTitle(menus);
		model.addAttribute("titles", titles);

		// 获取用户头像
		Integer id = ShiroKit.getUser().getId();
		User user = userService.getById(id);
		String avatar = user.getAvatar();
		model.addAttribute("avatar", avatar);

		return "/beetl/index";
	}

	/**
	 * 跳转到登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		if (ShiroKit.isAuthenticated() && ShiroKit.getUser() != null)
		{
			return REDIRECT + "/";
		}
		else
		{
			return "/login";
		}
	}
	/**
	 * 跳转到注册页面
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(String type,Model model)
	{
		if(StringUtils.isBlank(type)) {
			type="person";
		}
		if(type.equals("unit")) {
			List<Dict> unitTypes = dictService.selectByPCode("unit_type");
	        model.addAttribute("unitTypes", unitTypes);
		}else {
			List<Dept> depts=deptService.list();
			model.addAttribute("depts", depts);
			List<Map<String, Object>> personTypes=roleMapper.selectRoleNameAndCodes(1);
			model.addAttribute("personTypes",personTypes);
		}
		model.addAttribute("type", type);
		return "/beetl/register";
	}
	/**
     * 注册部门
     */
    @BussinessLog(value = "注册部门", key = "simplename", dict = AuthDictMapNames.DeptDict)
    @RequestMapping(value = "/doRegisterUnit")
    @ResponseBody
    public Object doRegisterUnit(@RequestBody DeptRegisterParam param)
    {
    	Dept dept=param.getDept();
    	User user=param.getUser();
    	String deptTypes=param.getDeptTypes();
        if (ToolUtil.isOneEmpty(dept, user,deptTypes)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 根据账号判断单位是否重复
        User theUser = userService.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 根据企业信用代码判断单位是否重复
        Dept theDept =deptService.getOne(new QueryWrapper<Dept>().eq("unitcode", dept.getUnitcode()));
        if (theDept != null) {
            throw new BussinessException(BizExceptionEnum.DEPT_ALREADY_REG);
        }
        
        // 完善用户信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setType(0);//单位用户
        user.setRegstatus(0);//注册申请状态
        user.setCreatetime(new Date());
        deptService.registerDept(dept, user, deptTypes);
        return BaseController.SUCCESS_TIP;
    }
    /**
     * 注册人员
     */
    @BussinessLog(value = "注册人员", key = "simplename", dict = AuthDictMapNames.DeptDict)
    @RequestMapping(value = "/doRegisterPerson")
    @ResponseBody
    public Object doRegisterPerson(@RequestBody PersonRegisterParam param)
    {
    	User account=param.getUser();
    	Person person=param.getPerson();
    	String roleCodes=param.getRolecodes();
        if (ToolUtil.isOneEmpty(account, person)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 根据账号判断用户是否重复
        User theUser = userService.getByAccount(account.getAccount());
        if (theUser != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 根据身份证号判断用户是否重复
        Person  thePerson =personMapper.selectOne(new QueryWrapper<Person>().eq("cardno", person.getCardno()));
        if (thePerson != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        
        // 完善用户信息
        account.setSalt(ShiroKit.getRandomSalt(5));
        account.setPassword(ShiroKit.md5(account.getPassword(), account.getSalt()));
        account.setStatus(ManagerStatus.OK.getCode());
        account.setType(1);//个人用户
        account.setRegstatus(0);//注册申请状态
        account.setCreatetime(new Date());
        deptService.registerPerson(person, account,roleCodes);
        return BaseController.SUCCESS_TIP;
    }
	/**
	 * 点击登录执行的动作
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginVali(String username, String password, String kaptcha, HttpServletRequest request)
	{
		Subject currentUser = ShiroKit.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
		token.setRememberMe(true);

		currentUser.login(token);

		ShiroUser shiroUser = ShiroKit.getUser();
		super.getSession().setAttribute("shiroUser", shiroUser);
		super.getSession().setAttribute("username", shiroUser.getAccount());

		LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

		ShiroKit.getSession().setAttribute("sessionFlag", true);

		return REDIRECT + "/";	
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut()
	{
		LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
		ShiroKit.getSubject().logout();
		return REDIRECT + "/login";
	}
}
