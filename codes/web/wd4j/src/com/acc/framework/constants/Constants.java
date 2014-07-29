package com.acc.framework.constants;

/**
 * 
 * 常量类。
 * <p>系统中共用的常量都要在此类中定义。</p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by 何晓超 @ 2012年12月11日 </p>
 * @since 
 */
public class Constants {

	/**
	 * 私有构造器,不允许实例化
	 */
	private Constants() {
	}
	
	/*--------------------用户session信息---------------*/
	/** 用户id */
	public final static String USER_ID = "userId";
	/** 用户姓名 */
	public final static String USER_NAME = "userName";
	/** 用户帐号 */
	public final static String USER_ACCOUNT = "userAccount";
	/** 用户身份证号码 */
	public final static String USER_IDCARD = "userIdcard";
	/** 用户所属部门ID */
	public final static String USER_DEPT_ID = "userDeptId";
	/** 用户所属部门编号 */
	public final static String USER_DEPT_CODE = "userDeptCode"; 
	/** 用户所属部门名称 */
	public final static String USER_DEPT_NAME = "userDeptName";
	/** 用户能看到的菜单 */
	public final static String USER_MENUS = "rootMenuList";
	/** 用户可以使用的资源 */
	public final static String USER_RESOURCES = "setUserResources";
	/** 用户被禁用的资源 */
	public final static String USER_RESOURCES_FORBIDDEN = "setUserResourcesForbidden";
	/** 用户所有父组织结构 */
	public final static String USER_PARENT_ORG_CODE = "orgCode";
	/** 用户角色级别 */
	public final static String USER_ROLE_LEVEL = "roleLevel";
	
	
	/*--------------------用户登陆----------------------*/
	/** 用户登陆返回信息 */
	public final static String LOGIN_INFO = "loginInfo";
	/** 错误信息 */
	public final static String ERROR_MSG = "errorMsg";
	/** 用户登陆成功 */
	public final static String LOGIN_SUCCESS = "loginSuccess";
	/** 用户不存在 */
	public final static String USER_NOT_EXIST = "userNotExist";
	/** 密码错误 */
	public final static String PASS_ERROR = "passError";
	/** 无效的用户 */
	public final static String USER_INEFFECTIVE = "userIneffective";
	/** 无效的组织机构 */
	public final static String ORG_INEFFECTIVE = "orgIneffective";
	/** IP受限 */
	public final static String IP_LIMITED= "ipLimited";
	/**	用户对象 */
	public static final String Object_USER ="BaseUser";
	
	/** 登陆时间*/
	public static final String LAST_TIME ="LastTime";
	
	/*--------------------数据库类型说明----------------*/
	/** oracle */
	public final static String DB_ORACLE = "ORACLE";
	/** mysql */
	public final static String DB_MYSQL = "MYSQL";
	/** SQL SERVER */
	public final static String DB_SQLSERVER = "SQLSERVER";
		
	/*--------------数据库操作类型 关键字--------------*/
	/** DB操作类型 关键字 "select" */
	public static final String DB_SELECT = "select";
	/** DB操作类型 关键字 "insert" */
	public static final String DB_INSERT = "insert";
	/** DB操作类型 关键字 "update" */
	public static final String DB_UPDATE = "update";
	/** DB操作类型 关键字 "delete" */
	public static final String DB_DELETE = "delete";

	/** HttpRequest存放ID的属性名 */
	public static final String HTTP_REQUEST_PARAMETER_ID = "ID";
	/**	HttpRequest中翻页页码的参数名 */
	public static final String HTTP_PARAMETER_PAGE_NUM = "pageNumber";
	
	
}
