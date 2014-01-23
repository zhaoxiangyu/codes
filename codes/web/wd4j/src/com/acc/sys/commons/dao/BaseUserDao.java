package com.acc.sys.commons.dao;

import com.acc.framework.dao.BaseDao;
import com.acc.sys.commons.pojo.BaseRoleUser;
import com.acc.sys.commons.pojo.BaseUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@SuppressWarnings("unchecked")
public interface BaseUserDao extends BaseDao<BaseUser>{
	public List<BaseUser> login(Map userMap);
	public List<BaseUser> checkUserAccount(String account);
	public void resetPassword(String userId);
	public void insertUserRole(BaseRoleUser user);
	public List<BaseRoleUser> selectAllRoleUser(@Param("userId")String userId);
	public void deleteUserRole(String userId);
	public void modifyUserpwd(BaseUser user);
	public List<BaseUser> selectByOrgCode(String orgCode);
	public List<BaseRoleUser> selectAllRoleByRoleUser();
	public Map selectRoleLevelByUserId(String userId);
}