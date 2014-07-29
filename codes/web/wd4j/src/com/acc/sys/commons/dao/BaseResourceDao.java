/**
 * 
 */
package com.acc.sys.commons.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import com.acc.framework.dao.BaseDao;
import com.acc.sys.commons.pojo.BaseResource;

/**
 * 资源的Dao。
 * <p> </p> 
 * @author 张琦
 * @version v1.0.1
 * <p>最后更新 by 张琦 @ 2012-12-13 </p>
 * @param <T>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
public interface BaseResourceDao extends BaseDao<BaseResource> {
	public List<BaseResource> queryMenu(Map param);
	public List<BaseResource> queryResource(Map param);
	public List<BaseResource> queryResourceAndMenu(Map param);
	public List queryDisplayOrderMax(Map param);
	public List queryDisplayOrderMin(Map param);
	public void insertRoleResource(Map param);
	public void changeOrder(Map param);
	public void changeOrderDesc(Map param);
	public List checkResName(String resName);
	public List checkResURL(String resURL);
	public List<BaseResource> selectResourceByRoleId(String roleId);
	public List<BaseResource> selectResourceByUserId(String userId);
	public void deleteRoleResourceByRoleId(String roleId);
	public void deleteRoleResourceByResourceId(String resourceId);
	public List<BaseResource> checkDispalyOrder(Map param);
	public List selectResIdByParent(String parentId);
	public List queryMenuByUser(Map userMap);
	public void collectByUser(@Param("param")Map map);
	public void delCollectByUser(@Param("param")Map map);
	public String getCollectCountByUser(@Param("param")Map map);
}
