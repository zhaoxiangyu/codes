package com.acc.sys.commons.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.acc.framework.dao.BaseDao;
import com.acc.sys.commons.pojo.BaseRole;
import com.acc.sys.commons.pojo.BaseRoleResource;

@SuppressWarnings("unchecked")
public interface BaseRoleDao extends BaseDao<BaseRole> {
	public List<BaseRole> checkRoleName(@Param("roleName") String roleName);
	public List<BaseRole> selectAllByRoleId(@Param("rolesId") String rolesId);
	public List selectAllParentId(String id);
	public void insertRoleResource(BaseRoleResource roleResource);
	public void deleteResourceByRoleId(String id);


}
