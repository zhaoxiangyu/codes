package com.acc.sys.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.pojo.BaseRole;
import com.acc.sys.commons.pojo.BaseRoleUser;
import com.acc.sys.commons.pojo.BaseUser;

/**
 * 用户信息服务层,Service层接口.
 * <p>
 * </p>
 * 
 * @author 何晓超
 * @version v1.0.0
 *          <p>
 * 最后更新 by 何晓超 @ 2012年12月4日15:15:22
 *          </p>
 * @since
 */
@Component
public interface IUserService {

	/**
	 * 用户查询。
	 * <p>
	 * 通过参数集合查询用户列表
	 * </p>
	 * 
	 * @param param 用户参数集合
	 * @return list List<User>
	 */
	public PageBean<BaseUser> queryUsers(PageBean<BaseUser> pageBean, Map<String, Object> param);

	/**
	 * 删除用户信息。
	 * <p>
	 * 根据用户ID删除该用户。
	 * </p>
	 * 
	 * @param userId String
	 */
	public void deleteById(String userId);

	/**
	 * 添加用户信息。
	 * <p>
	 * </p>
	 * 
	 * @param user User
	 */
	public void insertUserInfo(BaseUser user);

	/**
	 * 根据用户ID查询用户。
	 * <p>
	 * </p>
	 * 
	 * @param userId String
	 * @return user User
	 */
	public BaseUser toModifyPage(String userId);

	/**
	 * 修改用户信息。
	 * <p> 。
	 * </p>
	 * 
	 * @param user User
	 */
	public void modifyUserInfo(BaseUser user);

	/**
	 * 查询用户相信信息。
	 * <p>
	 * </p>
	 * 
	 * @param userId String
	 * @return BaseUser
	 */
	public BaseUser selectUserView(String userId);

	/**
	 * 验证用户是否存在。
	 * <p>
	 * 存在返回true。
	 * </p>
	 * 
	 * @param param account String
	 * @return boolean
	 */
	public boolean checkUserAccount(String account);

	/**
	 * 
	 * 修改密码为111111。
	 * <p>。</p>
	 * @param userId String 用户ID
	 * @return void
	 */
	public void resetPassword(String userId);

	/**
	 * 
	 * 查询所有的角色。
	 * <p></p>
	 * @return
	 */
	public List<BaseRole> selectAllRole();

	/**
	 * 
	 * 查询所有的用户角色。
	 * <p></p>
	 * @return
	 */
	public List<BaseRoleUser> selectAllRoleUser(String userId);

	/**
	 * 
	 * 验证旧密码是否正确。
	 * <p>正确返回true。</p>
	 * @param userId        String
	 * @param validateValue String
	 * @return boolean
	 */
	public boolean checkPassword(String userId, String validateValue);

	/**
	 * 
	 * 修改密码。
	 * <p></p>
	 * @param user BaseUser
	 * @return void
	 */
	public void modifyUserpwd(BaseUser user);

}
