package com.acc.sys.commons.dao;

import java.util.List;
import java.util.Map;

import com.acc.framework.dao.BaseDao;
import com.acc.sys.commons.pojo.BaseOrganize;

/**
 * 
 * 组织机构Dao。
 * <p> </p> 
 * @author 张琦
 * @version v1.0.1
 * <p>最后更新 by 张琦 @ Dec 20, 2012 </p>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
public interface BaseOrganizeDao extends BaseDao<BaseOrganize> {

	public List<BaseOrganize> selectOrgList(Map param);
	public List queryDisplayOrderMax(Map param);
	public List queryDisplayOrderMin(Map param);
	public List checkOrgName(String orgName);
	public List checkOrgCode(String orgCode);
	public List<BaseOrganize> checkDispalyOrder(Map param);
	public List selectOrgIdByParent(String parentId);
	public List selectAllParentOrgCodeByOrgCode(String orgCode);
	public List selectOrgListQy(Map param);
}
