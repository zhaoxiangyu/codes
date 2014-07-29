package com.acc.sys.commons.dao;

import java.util.List;
import java.util.Map;

import com.acc.framework.dao.BaseDao;
import com.acc.sys.commons.pojo.BaseDicType;

/**
 * 资源的Dao。
 * <p></p> 
 * @author 王虎
 * @version v1.0.1
 * <p>最后更新 by 王虎 @ 2012-12-17 </p>
 * @param <T>
 * @since v1.0.0
 */

public interface BaseDicTypeDao extends BaseDao<BaseDicType> {
	public List<BaseDicType> checkDicTypeName(String dicTypeName);
	public List<BaseDicType> checkDicTypeCode(String dicTypeCode);
	public List selectByIds(Map params);
}
