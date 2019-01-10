package cn.stylefeng.guns.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public class MyRealm extends AuthorizingRealm
{
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
	{
		// 获取登录时输入的用户名
		String username = (String) principalCollection.getPrimaryPrincipal();
		System.out.println("UserName: " + username);
		return null;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		
		// 通过表单接收的用户名
		String username = token.getUsername();
		System.out.println("username:" + username);
		if (username != null && !"".equals(username))
		{
			//User user = userService.getUserByUsername(username);
			//if (user != null)
			{
				//return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
			}
		}
		System.out.println("认证失败");
		return null;
	}

}
