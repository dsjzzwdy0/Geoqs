package com.loris.auth.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.auth.dao.DeptMapper;
import com.loris.auth.dao.DictMapper;
import com.loris.auth.dao.MenuMapper;
import com.loris.auth.dao.NoticeMapper;
import com.loris.auth.dao.RoleMapper;
import com.loris.auth.dao.UserMapper;
import com.loris.auth.log.LogObjectHolder;
import com.loris.auth.model.Dept;
import com.loris.auth.model.Dict;
import com.loris.auth.model.Menu;
import com.loris.auth.model.Notice;
import com.loris.auth.model.Role;
import com.loris.auth.model.User;
import com.loris.common.constant.cache.Cache;
import com.loris.common.constant.cache.CacheKey;
import com.loris.common.constant.state.ManagerStatus;
import com.loris.common.constant.state.MenuStatus;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.util.Convert;
import com.loris.common.util.ToolUtil;

import cn.hutool.core.util.StrUtil;

/**
 * 常量的生产工厂
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:55:21
 */
@Component
public class ConstantFactory
{
	@Autowired
	private RoleMapper roleMapper; // = ApplicationContextHelper.getBean(RoleMapper.class);
	@Autowired
	private DeptMapper deptMapper;// = ApplicationContextHelper.getBean(DeptMapper.class);
	@Autowired
	private DictMapper dictMapper;// = ApplicationContextHelper.getBean(DictMapper.class);
	@Autowired
	private UserMapper userMapper;// = ApplicationContextHelper.getBean(UserMapper.class);
	@Autowired
	private MenuMapper menuMapper;// = ApplicationContextHelper.getBean(MenuMapper.class);
	@Autowired
	private NoticeMapper noticeMapper;// = ApplicationContextHelper.getBean(NoticeMapper.class);
	
	
	private static ConstantFactory instance = null;

	/**
	 * 创建一个管理工厂
	 * @return
	 */
	public static ConstantFactory me()
	{
		if(instance == null)
		{
			instance = ApplicationContextHelper.getBean(ConstantFactory.class);
		}
		return instance;
	}

	/**
	 * 根据用户id获取用户名称
	 *
	 * @author stylefeng
	 * @Date 2017/5/9 23:41
	 */
	public String getUserNameById(Integer userId)
	{
		User user = userMapper.selectById(userId);
		if (user != null)
		{
			return user.getName();
		}
		else
		{
			return "--";
		}
	}

	/**
	 * 根据用户id获取用户账号
	 *
	 * @author stylefeng
	 * @date 2017年5月16日21:55:371
	 */
	public String getUserAccountById(Integer userId)
	{
		User user = userMapper.selectById(userId);
		if (user != null)
		{
			return user.getAccount();
		}
		else
		{
			return "--";
		}
	}

	/**
	 * 通过角色编号获取角色名称
	 */
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
	public String getRoleName(String roleCodes)
	{
		if (ToolUtil.isEmpty(roleCodes))
		{
			return "";
		}
		String[] roles = Convert.toStrArray(roleCodes);
		StringBuilder sb = new StringBuilder();
		for (String code : roles)
		{
			Role roleObj = roleMapper.selectByCode(code);
			if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName()))
			{
				sb.append(roleObj.getName()).append(",");
			}
		}
		return StrUtil.removeSuffix(sb.toString(), ",");
	}

	/**
	 * 通过角色编号获取角色名称
	 */
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleCode")
	public String getSingleRoleName(String roleCode)
	{
		if (StringUtils.isEmpty(roleCode))
		{
			return "--";
		}
		Role roleObj = roleMapper.selectByCode(roleCode);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName()))
		{
			return roleObj.getName();
		}
		return "";
	}
	/**
	 * 通过角色id获取角色名称
	 */
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
	public String getSingleRoleName(Integer roleId)
	{
		if (0==roleId)
		{
			return "--";
		}
		Role roleObj = roleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName()))
		{
			return roleObj.getName();
		}
		return "";
	}
	/**
	 * 通过角色id获取角色英文名称
	 */
	@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
	public String getSingleRoleTip(Integer roleId)
	{
		if (0 == roleId)
		{
			return "--";
		}
		Role roleObj = roleMapper.selectById(roleId);
		if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName()))
		{
			return roleObj.getTips();
		}
		return "";
	}

	/**
	 * 获取部门名称
	 */
	//@Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
	public String getDeptName(Integer deptId)
	{
		Dept dept = deptMapper.selectById(deptId);
		if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname()))
		{
			return dept.getFullname();
		}
		return "";
	}

	/**
	 * 获取菜单的名称们(多个)
	 */
	public String getMenuNames(String menuIds)
	{
		Integer[] menus = Convert.toIntArray(menuIds);
		StringBuilder sb = new StringBuilder();
		for (int menu : menus)
		{
			Menu menuObj = menuMapper.selectById(menu);
			if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName()))
			{
				sb.append(menuObj.getName()).append(",");
			}
		}
		return StrUtil.removeSuffix(sb.toString(), ",");
	}

	/**
	 * 获取菜单名称
	 */
	public String getMenuName(Long menuId)
	{
		if (ToolUtil.isEmpty(menuId))
		{
			return "";
		}
		else
		{
			Menu menu = menuMapper.selectById(menuId);
			if (menu == null)
			{
				return "";
			}
			else
			{
				return menu.getName();
			}
		}
	}

	/**
	 * 获取菜单名称通过编号
	 */
	public String getMenuNameByCode(String code)
	{
		if (ToolUtil.isEmpty(code))
		{
			return "";
		}
		else
		{
			QueryWrapper<Menu> qw = new QueryWrapper<>();
			qw.eq("code", code);
			
			//Menu param = new Menu();
			//param.setCode(code);
			Menu menu = menuMapper.selectOne(qw);
			//Menu menu = menuMapper.selectOne(param);
			if (menu == null)
			{
				return "";
			}
			else
			{
				return menu.getName();
			}
		}
	}

	/**
	 * 获取字典名称
	 */
	public String getDictName(Integer dictId)
	{
		if (ToolUtil.isEmpty(dictId))
		{
			return "";
		}
		else
		{
			Dict dict = dictMapper.selectById(dictId);
			if (dict == null)
			{
				return "";
			}
			else
			{
				return dict.getName();
			}
		}
	}

	/**
	 * 获取通知标题
	 */
	public String getNoticeTitle(Integer dictId)
	{
		if (ToolUtil.isEmpty(dictId))
		{
			return "";
		}
		else
		{
			Notice notice = noticeMapper.selectById(dictId);
			if (notice == null)
			{
				return "";
			}
			else
			{
				return notice.getTitle();
			}
		}
	}

	/**
	 * 根据字典名称和字典中的值获取对应的名称
	 */
	public String getDictsByName(String name, Integer val)
	{
		//Dict temp = new Dict();
		//temp.setName(name);
		QueryWrapper<Dict> ew = new QueryWrapper<Dict>();
		ew.eq("name", name);		
		Dict dict = dictMapper.selectOne(ew);
		if (dict == null)
		{
			return "";
		}
		else
		{
			QueryWrapper<Dict> wrapper = new QueryWrapper<>();
			wrapper = wrapper.eq("pid", dict.getId());
			List<Dict> dicts = dictMapper.selectList(wrapper);
			for (Dict item : dicts)
			{
				if (item.getNum() != null && item.getNum().equals(val))
				{
					return item.getName();
				}
			}
			return "";
		}
	}

	/**
	 * 获取性别名称
	 */
	public String getSexName(Integer sex)
	{
		return getDictsByName("性别", sex);
	}

	/**
	 * 获取用户登录状态
	 */
	public String getStatusName(Integer status)
	{
		return ManagerStatus.valueOf(status);
	}

	/**
	 * 获取菜单状态
	 */
	public String getMenuStatusName(Integer status)
	{
		return MenuStatus.valueOf(status);
	}

	/**
	 * 查询字典
	 */
	public List<Dict> findInDict(Integer id)
	{
		if (ToolUtil.isEmpty(id))
		{
			return null;
		}
		else
		{
			QueryWrapper<Dict> wrapper = new QueryWrapper<>();
			List<Dict> dicts = dictMapper.selectList(wrapper.eq("pid", id));
			if (dicts == null || dicts.size() == 0)
			{
				return null;
			}
			else
			{
				return dicts;
			}
		}
	}

	/**
	 * 获取被缓存的对象(用户删除业务)
	 */
	public String getCacheObject(String para)
	{
		return LogObjectHolder.me().get().toString();
	}

	/**
	 * 获取子部门id
	 */
	public List<Integer> getSubDeptId(Integer deptid)
	{
		QueryWrapper<Dept> wrapper = new QueryWrapper<>();
		wrapper = wrapper.like("pids", "%[" + deptid + "]%");
		List<Dept> depts = this.deptMapper.selectList(wrapper);

		ArrayList<Integer> deptids = new ArrayList<>();

		if (depts != null && depts.size() > 0)
		{
			for (Dept dept : depts)
			{
				deptids.add(dept.getId());
			}
		}

		return deptids;
	}
}