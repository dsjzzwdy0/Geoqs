package com.tigis.geoqs.controller;

import java.util.List;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Constants;
import com.tigis.geoqs.common.exception.InvalidKaptchaException;
import com.tigis.geoqs.common.log.LogManager;
import com.tigis.geoqs.common.log.LogTaskFactory;
import com.tigis.geoqs.common.node.MenuNode;
import com.tigis.geoqs.security.ShiroKit;
import com.tigis.geoqs.security.ShiroUser;
import com.tigis.geoqs.sys.model.User;
import com.tigis.geoqs.sys.service.MenuService;
import com.tigis.geoqs.sys.service.UserService;
import com.tigis.geoqs.util.KaptchaUtil;
import com.tigis.geoqs.util.ToolUtil;

import static com.tigis.geoqs.util.HttpContext.getIp;

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

	/**
	 * 跳转到主页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model)
	{
		// 获取菜单列表
		List<Integer> roleList = ShiroKit.getUser().getRoleList();
		if (roleList == null || roleList.size() == 0)
		{
			ShiroKit.getSubject().logout();
			model.addAttribute("tips", "该用户没有角色，无法登陆");
			return "/login";
		}
		List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
		List<MenuNode> titles = MenuNode.buildTitle(menus);
		model.addAttribute("titles", titles);

		// 获取用户头像
		Integer id = ShiroKit.getUser().getId();
		User user = userService.selectById(id);
		String avatar = user.getAvatar();
		model.addAttribute("avatar", avatar);

		return "/index";
	}

	/**
	 * 跳转到登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null)
		{
			return REDIRECT + "/";
		}
		else
		{
			return "/login";
		}
	}

	/**
	 * 点击登录执行的动作
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginVali()
	{

		String username = super.getPara("username").trim();
		String password = super.getPara("password").trim();

		// 验证验证码是否正确
		if (KaptchaUtil.getKaptchaOnOff())
		{
			String kaptcha = super.getPara("kaptcha").trim();
			String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equals(code))
			{
				throw new InvalidKaptchaException();
			}
		}

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
