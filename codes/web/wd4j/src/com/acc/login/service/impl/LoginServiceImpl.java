package com.acc.login.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acc.framework.constants.Constants;
import com.acc.login.service.ILoginService;
import com.acc.sys.commons.dao.BaseOrganizeDao;
import com.acc.sys.commons.dao.BaseResourceDao;
import com.acc.sys.commons.dao.BaseUserDao;
import com.acc.sys.commons.pojo.BaseOrganize;
import com.acc.sys.commons.pojo.BaseResource;

/**
 * 用户登陆的服务实现类。
 * <p> </p>
 * @author 张琦
 * @version v1.0.1
 * <p>最后更新 by 张琦 @ 2012-12-12</p>
 */
@Component
@SuppressWarnings("unchecked")
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private BaseUserDao userDao;
	@Autowired
	private BaseResourceDao resourceDao;

	@Autowired
	private BaseOrganizeDao orgDao;
	/**
	 * 
	 * 登陆方法。
	 * <p> </p>
	 * @param param 登陆参数
	 * @param session
	 * @param request
	 * @return
	 */
	public Map login(Map param, HttpSession session, HttpServletRequest request) {
		// 返回值
		Map rsMap = new HashMap();
		// 数据库中用户密码
		String userPsw = "";
		// 查询到的用户对象
		Map user = null;
		//查询条件map
		Map userMap = new HashMap();
		String userName = param.get("userName") == null ? "" : param.get("userName").toString();
		String password = param.get("password") == null ? "" : param.get("password").toString();
		//根据用户名查询用户信息
		userMap.put("userName", userName);
		request.setAttribute("loginUserName", userName);
		List userList = userDao.login(userMap);
		// 验证用户名密码
		if (userList == null || userList.size() <= 0) {//用户不存在
			rsMap.put(Constants.LOGIN_INFO, Constants.USER_NOT_EXIST);
		} 
		else {
			//取出用户信息
			user = (Map) userList.get(0);
			userPsw = (String) user.get("USER_PASSWORD");
			if ("".equals(password) || !userPsw.equals(password)) {//密码错误
				rsMap.put(Constants.LOGIN_INFO, Constants.PASS_ERROR);
			}
			else{
				if (!"".equals(password) && userPsw.equals(password)) {//无效的用户
					if ("0".equals(user.get("USER_IS_EFFECTIVE").toString())) {
						rsMap.put(Constants.LOGIN_INFO, Constants.USER_INEFFECTIVE);
					}
					else{
						if("0".equals(user.get("ORG_IS_EFFECTIVE").toString())){//无效的组织机构
							rsMap.put(Constants.LOGIN_INFO, Constants.ORG_INEFFECTIVE);
						}
						else{// 登录成功，将该用户的用户信息
							session.setAttribute(Constants.USER_ACCOUNT, user.get("USER_ACCOUNT"));
							session.setAttribute(Constants.USER_ID, user.get("ID"));
							session.setAttribute(Constants.USER_NAME, user.get("USER_NAME"));
							session.setAttribute(Constants.USER_DEPT_CODE, user.get("ORG_CODE"));
							session.setAttribute(Constants.USER_DEPT_ID, user.get("ORG_ID"));
							session.setAttribute(Constants.USER_DEPT_NAME, user.get("ORG_NAME"));
							session.setAttribute(Constants.USER_IDCARD, user.get("USER_ID_CARD"));
							session.setAttribute(Constants.Object_USER, user);
							List list = orgDao.selectAllParentOrgCodeByOrgCode(String.valueOf(user.get("ORG_CODE")));
							if(list!=null&&list.size()>0){
								int count =list.size();
								for(int i=0;i<count;i++){
									BaseOrganize org = (BaseOrganize)list.get(i);
									session.setAttribute(Constants.USER_PARENT_ORG_CODE+i+"", org);
								}
							}
							Map level = userDao.selectRoleLevelByUserId((String) user.get("ID"));
							if(level != null)
								session.setAttribute(Constants.USER_ROLE_LEVEL, level.get("ROLELEVEL"));
							userMap.put("userId", (String) user.get("ID"));
							boolean bl=true;
//							if(!"04".equals(session.getAttribute(Constants.USER_ROLE_LEVEL))){
//								List menuList = resourceDao.queryMenuByUser(userMap);
//								request.setAttribute("menuList", menuList);
//							}
							// 菜单信息存入用户对象
							List rootMenuList = queryMenu(userMap);
							List allResList = resourceDao.queryResource(userMap);
							Set setUserRes = new HashSet();
							for (int i = 0; i < allResList.size(); i++) {
								Map temp = (Map) allResList.get(i);
								setUserRes.add(temp.get("RES_URL"));
							}
							session.setAttribute(Constants.USER_MENUS, rootMenuList);
							session.setAttribute(Constants.USER_RESOURCES, setUserRes);
							rsMap.put(Constants.LOGIN_INFO, Constants.LOGIN_SUCCESS);
							
							/** start 该用户没有的权限资源存入session，供权限校验使用 */
							List listResources = resourceDao.selectAll();
							Set setAllRes = new HashSet();
							for (int i = 0; i < listResources.size(); i++) {
								BaseResource temp = (BaseResource) listResources.get(i);
								setAllRes.add(temp.getResUrl());
							}
							//得到该用户被禁用的资源
							setAllRes.removeAll(setUserRes);
							session.setAttribute(Constants.USER_RESOURCES_FORBIDDEN, setAllRes);
							/** end */
							session.setAttribute("loginFlag", "true");
						}
					}
				}
			}
		}
		return rsMap;
	}
	
	/**
	 * 用户退出。
	 * <p>清空session，保证页面跳转到登录页面时session失效。</p>
	 * @param session
	 * @return
	 */
	public boolean logout( HttpSession session) {
		session.invalidate();
		return true;
	}

	/**
	 * 
	 * 查询用户具有的菜单。
	 * <p> </p>
	 * 
	 * @param map
	 * @return
	 */
	public List queryMenu(Map userMap) {
		List menuList=new ArrayList();
		menuList = resourceDao.queryMenu(userMap);
		List<Map<String, Object>> rootMenuList = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> k_v_map = new HashMap<String, Map<String, Object>>();
		for (int i = 0; i < menuList.size(); i++) {
			Map rotMap = (Map) menuList.get(i);
			String resourceId = (String) rotMap.get("ID");
			k_v_map.put(resourceId, rotMap);
			String parentId = (String) rotMap.get("PARENT_ID");
			if ("1".equals(parentId)) {
				rootMenuList.add(rotMap);
			}
		}
		for (int i = 0; i < menuList.size(); i++) {
			Map rotMap = (Map) menuList.get(i);
			String parentId = (String) rotMap.get("PARENT_ID");
			if (!"1".equals(parentId) && k_v_map.containsKey(parentId)) {
				Map tempMap = k_v_map.get(parentId);
				List<Map<String, Object>> childList = (List<Map<String, Object>>) tempMap.get("childList");
				if (childList == null) {
					childList = new ArrayList<Map<String, Object>>();
					tempMap.put("childList", childList);
				}
				childList.add(rotMap);
			}
		}
		return rootMenuList;
	}

}
