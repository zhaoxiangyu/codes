package com.acc.sys.dictionary.service;

import java.util.List;
import java.util.Map;


import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.pojo.BaseDicType;
import com.acc.sys.commons.pojo.BaseDicValue;

/**
 * 字典类型服务层,Service层接口.
 * <p>
 * </p>
 * 
 * @author 王虎
 * @version v1.0.0
 *          <p>
 * 最后更新 by 王虎 @ 2012-12-17
 *          </p>
 * @since
 */
@SuppressWarnings("unchecked")
public interface IDictionaryService {

	/**
	 * 字典类型查询。
	 * <p>
	 * 通过参数集合查询字典类型列表
	 * </p>
	 * 
	 * @param param 字典类型参数集合
	 * @return list List<User>
	 */
	public PageBean<BaseDicType> queryDicType(PageBean<BaseDicType> pageBean, Map<String, Object> param);

	/**
	 * 删除字典类型。
	 * <p>
	 * 根据用户ID删除该字典类型。
	 * </p>
	 * 
	 * @param dicTypeId String
	 */
	public void deleteDicTypeById(String dicTypeId);

	/**
	 * 添加字典类型。
	 * <p>
	 * </p>
	 * 
	 * @param baseDicType BaseDicType
	 */
	public void insertDicTypeInfo(BaseDicType baseDicType);

	/**
	 * 根据用户ID查询字典类型。
	 * <p>
	 * </p>
	 * 
	 * @param dicTypeId String
	 * @return baseDicType BaseDicType
	 */
	public BaseDicType toModifyPage(String dicTypeId);

	/**
	 * 修改字典类型。
	 * <p> 。
	 * </p>
	 * 
	 * @param baseDicType BaseDicType
	 */
	public void modifyDicTypeInfo(BaseDicType baseDicType);

	/**
	 * 查询字典类型详细信息。
	 * <p>
	 * </p>
	 * 
	 * @param dicTypeId String
	 * @return BaseDicType
	 */
	public BaseDicType selectBaseDicView(String dicTypeId);

	/**
	 * 验证字典类型名称是否存在。
	 * <p>
	 * 存在返回true。
	 * </p>
	 * 
	 * @param param dicTypeName String
	 * @return boolean
	 */
	public boolean checkDicTypeDicTypeName(String dicTypeName);
	/**
	 * 验证字典类型编码是否存在。
	 * <p>
	 * 存在返回true。
	 * </p>
	 * 
	 * @param param dicTypeName String
	 * @return boolean
	 */
	public boolean checkDicTypeDicTypeCode(String dicTypeCode);
	
	/**
	 * 查询字典值详细信息。
	 * <p>
	 * </p>
	 * 
	 * @param dicTypeId String
	 * @return BaseDicType
	 */
	public List queryDicValue(Map param);
	
	/**
	 * 
	 * 查询字典值详细信息。
	 * <p></p>
	 * @param param
	 * @return Json
	 */
	public String queryDicValueJson(Map param);
	/**
	 * 添加字典值信息。
	 * <p>
	 * </p>
	 * 
	 * @param baseDicType BaseDicType
	 */
	public int insertDicValueInfo(BaseDicValue baseDicValue);
	
	/**
	 * 查询字典值默认排序。
	 * <p>
	 * </p>
	 * 
	 * @param dicTypeId String
	 * @return BaseDicType
	 */
	public String queryDisplayOrder(String parentId,String dicTypeId);
	/**
	 * 修改字典值信息。
	 * <p> 。
	 * </p>
	 * 
	 * @param baseDicType BaseDicType
	 */
	public int modifyDicValueInfo(BaseDicValue baseDicValue);
	/**
	 * 删除字典值信息。
	 * <p> 。
	 * </p>
	 * 
	 * @param dicValueId String
	 */
	public int deleteDicValueById(String dicValueId);
	/**
	 * 验证字典类值名称是否存在。
	 * <p>
	 * </p>
	 * 
	 * @param 
	 * @return boolean
	 */
	public boolean checkDicValueName(String dicName ,String dicTypeId,String level);
	/**
	 * 验证字典类值编码是否存在。
	 * <p>
	 * </p>
	 * 
	 * @param 
	 * @return boolean
	 */
	public boolean checkDicValueCode(String dicCode,String dicTypeId,String level);
	
	public List selectDicValueList(Map param);
	/**
	 * 修改显示顺序。
	 * <p>
	 * </p>
	 * 
	 * @param Map 
	 * @return int
	 */
	public int changeOrder(Map param);

	/**
	 * 根据父节点查找所有子节点的ID。
	 * <p>
	 * </p>
	 * 
	 * @param String
	 * @return List
	 */
	public List selectDicIdByParent(String string);
	
	public String selectXzqhJson(Map param);

}
