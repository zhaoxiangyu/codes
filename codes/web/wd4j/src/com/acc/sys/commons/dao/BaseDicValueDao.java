package com.acc.sys.commons.dao;

import java.util.List;
import java.util.Map;

import com.acc.framework.dao.BaseDao;
import com.acc.sys.commons.pojo.BaseDicValue;

/**
 * 
 * 字典数据Dao。
 * <p> </p> 
 * @author 张琦
 * @version v1.0.1
 * <p>最后更新 by 张琦 @ Dec 20, 2012 </p>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
public interface BaseDicValueDao extends BaseDao<BaseDicValue> {

	public List queryDicValueList(Map param);
	public List queryDisplayOrderMax(Map param);
	public List queryDisplayOrderMin(Map param);
	public List checkDicValueName(Map param);
	public List checkDicValueCode(Map param);
	public List<BaseDicValue> checkDispalyOrder(Map param);
	public List selectDicIdByParent(String string);
	public List selectByIds(Map params);
}
