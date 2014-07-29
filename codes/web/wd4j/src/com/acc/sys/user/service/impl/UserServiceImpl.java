package com.acc.sys.user.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acc.framework.util.Md5Util;
import com.acc.framework.util.StringUtil;
import com.acc.framework.util.UUIDGeneratorUtil;
import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.dao.BaseRoleDao;
import com.acc.sys.commons.dao.BaseUserDao;
import com.acc.sys.commons.pojo.BaseRole;
import com.acc.sys.commons.pojo.BaseRoleUser;
import com.acc.sys.commons.pojo.BaseUser;
import com.acc.sys.user.service.IUserService;
/**
 * 
 * 用户信息服务层,Service层接口实现类.
 * <p> </p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by 何晓超 @ 2012年12月4日15:15:22 </p>
 * @since 
 */
@SuppressWarnings("unchecked")
@Component
public class UserServiceImpl implements IUserService {
	@Autowired
	private BaseUserDao userDao;
	@Autowired
	private BaseRoleDao roleDao;
	
	/**
	 * 
	 * 用户查询。
	 * <p>通过参数集合查询用户列表</p>
	 * @param param    用户参数集合
	 * @return list    List<User>
	 */
	public PageBean<BaseUser> queryUsers(PageBean<BaseUser> pageBean,Map<String, Object> param) {
		if(param.get("userAccount")!=null){
		param.put("userAccount1", param.get("userAccount").toString().replace("%", "/%"));
		}
		if(param.get("userName")!=null){
		param.put("userName1", param.get("userName").toString().replace("%", "/%"));
		}
		if(param.get("userIdentity")!=null){
		param.put("userIdentity1", param.get("userIdentity").toString().replace("%", "/%"));
		}
		pageBean.setResultList(userDao.queryByPage(pageBean,param));
		return pageBean;
	}
	
	/**
	 * 
	 * 删除用户信息。
	 * <p>根据用户ID删除该用户。</p>
	 * @param userId String
	 */
	public void deleteById(String userId) {
		userDao.deleteByPrimaryKey(userId);
		//删除用户角色关系
		userDao.deleteUserRole(userId);
	}

	/**
	 * 
	 * 添加用户信息。
	 * <p></p>
	 * @param user User
	 */
	public void insertUserInfo(BaseUser user) {
		user.setId(UUIDGeneratorUtil.getUUID());
		user.setCreateTime(new Timestamp(new java.util.Date().getTime()));
		user.setModifyTime(new Timestamp(new java.util.Date().getTime()));
		user.setChSpell(StringUtil.strTospell(user.getUserName()));
		userDao.insert(user);
		//给用户附加角色
		insertUserRole(user);
	}
	/**
	 * 
	 * 给用户附加角色。
	 * <p></p>
	 * @param user BaseUser
	 */
	private void insertUserRole(BaseUser user){
		if (user.getRolesId()!=null) {
		for (int i = 0; i < user.getRolesId().length; i++) {
			BaseRoleUser roleUser=new BaseRoleUser();
			roleUser.setId(UUIDGeneratorUtil.getUUID());
			roleUser.setRoleId(user.getRolesId()[i]);
			roleUser.setUserId(user.getId());
			userDao.insertUserRole(roleUser);
		}
		}
	}
	/**
	 * 
	 * 根据用户ID查询用户。
	 * <p></p>
	 * @param userId  
	 * @return user User
	 */
	public BaseUser toModifyPage(String userId) {
		BaseUser user =(BaseUser)userDao.selectByPrimaryKey(userId);
		return user;
	}

	/**
	 * 
	 * 修改用户信息。
	 * <p>。</p>
	 * @param user User
	 */
	public void modifyUserInfo(BaseUser user) {
		user.setModifyTime(new Timestamp(new java.util.Date().getTime()));
		user.setChSpell(StringUtil.strTospell(user.getUserName()));
		userDao.updateByPrimaryKey(user);
		//删除用户角色
		userDao.deleteUserRole(user.getId());
		insertUserRole(user);
	}
	/**
	 * 
	 * 查询用户详细信息。
	 * <p>。</p>
	 * @param user User
	 */
	public BaseUser selectUserView(String userId) {
		BaseUser user =(BaseUser)userDao.selectByPrimaryKey(userId);
		return user;
	}
	
	
	/**
	 * 验证用户是否存在。
	 * <p>存在返回true。</p>
	 * 
	 * @param param account String
	 * @return boolean
	 */
	public boolean checkUserAccount(String account) {
		boolean isExist = true;
		List list = (List)userDao.checkUserAccount(account);
		if(list.size()>0){
			//表示没有被使用
			isExist = false;
		}
		return isExist;
	}

	/**
	 * 
	 * 修改密码为111111。
	 * <p>。</p>
	 * @param userId String 用户ID
	 * @return void
	 */
	public void resetPassword(String userId) {
		userDao.resetPassword(userId);
	}
	/**
	 * 
	 * 查询所有角色
	 * <p>。</p>
	 * @param userId String 用户ID
	 * @return List<BaseRole>
	 */
	public List<BaseRole> selectAllRole() {
		List <BaseRole> roleList =roleDao.selectAll();
		return roleList;
	}
	
	/**
	 * 
	 * 查询用户角色关系。
	 * <p>。</p>
	 * @param userId String 用户ID
	 * @return List<BaseRoleUser>
	 */
	public List<BaseRoleUser> selectAllRoleUser(String userId) {
		List <BaseRoleUser> roleUserList=userDao.selectAllRoleUser(userId);
		return roleUserList;
	}

	/**
	 * 
	 * 验证输入密码是否正确。
	 * <p>正确返回true</p>
	 * @param userId String 用户ID
	 * @return boolean
	 */
	public boolean checkPassword(String userId, String validateValue) {
		BaseUser user =userDao.selectByPrimaryKey(userId);
		boolean isExist=false;
		if (user.getUserPassword().equals(Md5Util.getMD5ofStr(validateValue))) {
			isExist=true;
		}
		return isExist;
	}

	/**
	 * 
	 * 修改密码。
	 * <p></p>
	 * @param user BaseUser
	 * @return void
	 */
	public void modifyUserpwd(BaseUser user) {
		user.setUserPassword(Md5Util.getMD5ofStr(user.getUserPassword()));
		userDao.modifyUserpwd(user);
	}

}
