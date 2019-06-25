/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  Constants.java   
 * @Package com.loris.common.constant   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.constant;

/**   
 * @ClassName:  Constants    
 * @Description: 全局应用常量数据的定义  
 * @author: 天勘岩土工程信息化项目组
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface Constants
{
	/** 登录的用户 */
	final static public String SESSION_USER = "login_user";
	final static public String SESSION_LOGINTIME = "login_time";
	final static public String KAPTCHA_SESSION_KEY = "kaptcha";
	/**单位管理员的角色编号*/
	final static public String ROLE_ADMIN_DEPT_CODE = "role_admin_dept";
	/**超级管理员的角色编号*/
	final static public String ROLE_ADMIN_SUPER_CODE = "role_admin_super";
	/**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = "111111";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

}
