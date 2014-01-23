package com.acc.sys.dictionary.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acc.framework.util.ConfigUtil;
import com.acc.framework.util.StringUtil;
import com.acc.framework.util.UUIDGeneratorUtil;
import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.dao.BaseDicTypeDao;
import com.acc.sys.commons.dao.BaseDicValueDao;
import com.acc.sys.commons.pojo.BaseDicType;
import com.acc.sys.commons.pojo.BaseDicValue;
import com.acc.sys.dictionary.service.IDictionaryService;
/**
 * 
 * 字典类型信息服务层,Service层接口实现类.
 * <p></p> 
 * @author 王虎
 * @version v1.0.0
 * <p>添加字典数据的操作 最后更新 by 张琦 @ 2012-12-19 </p>
 * @since 
 */
@SuppressWarnings("unchecked")
@Service(value="dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {

	@Autowired
	private BaseDicTypeDao dicTypeDao;
	@Autowired
	private BaseDicValueDao dicValueDao;
	/**
	 * 
	 * 字典类型查询。
	 * <p>通过参数集合查询字典类型列表</p>
	 * @param param    字典类型参数集合
	 * @return list    List<BaseDicType>
	 */
	public PageBean<BaseDicType> queryDicType(PageBean<BaseDicType> pageBean,Map<String, Object> param) {
		if(param.get("dicTypeName")!=null){
			param.put("dicTypeName1", param.get("dicTypeName").toString().replace("%", "/%"));
			}
		List resultList = dicTypeDao.queryByPage(pageBean, param);
		pageBean.setResultList(resultList);
		return pageBean;
	}
	/**
	 * 
	 * 字典类型名称是否存在。
	 * 
	 * @param    String 字典类型名称
	 * @return   boolean           
	 */
	public boolean checkDicTypeDicTypeName(String dicTypeName) {
		boolean isExist = true;
		List list = dicTypeDao.checkDicTypeName(dicTypeName);
		if(list.size()>0){
			isExist = false;
		}
			return isExist;
	}
	/**
	 * 
	 * 删除字典类型名称。
	 * 
	 * @param dicTypeId    String 
	 */
	public void deleteDicTypeById(String dicTypeId) {
		dicTypeDao.deleteByPrimaryKey(dicTypeId);
	}
	/**
	 * 
	 * 字典类型添加。
	 * @param baseDicType    BaseDicType
	 */
	public void insertDicTypeInfo(BaseDicType baseDicType) {
		baseDicType.setId(UUIDGeneratorUtil.getUUID());
		baseDicType.setCreateTime(new Timestamp(new java.util.Date().getTime()));
		baseDicType.setModifyTime(new Timestamp(new java.util.Date().getTime()));
		dicTypeDao.insert(baseDicType);
	}

	public void modifyDicTypeInfo(BaseDicType baseDicType) {
		baseDicType.setModifyTime(new Timestamp(new java.util.Date().getTime()));
		dicTypeDao.updateByPrimaryKey(baseDicType);
		
	}

	public BaseDicType selectBaseDicView(String dicTypeId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 
	 * 根据字典类型ID查询字典类型。
	 * <p></p>
	 * @param dicTypeId  
	 * @return dicType BaseDicType
	 */

	public BaseDicType toModifyPage(String dicTypeId) {
		BaseDicType dicType = (BaseDicType) dicTypeDao.selectByPrimaryKey(dicTypeId);
		return dicType;
	}
	
	public List queryDicValue(Map param) {
		// TODO Auto-generated method stub
		List dicValueList = dicValueDao.queryDicValueList(param);
		return dicValueList;
	}
	
	public String queryDicValueJson(Map param) {
		// TODO Auto-generated method stub
		List list = dicValueDao.queryDicValueList(param);
		String result="";
		if(list!=null && list.size()!=0){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = "[";
			for(int i=0;i<list.size();i++){
				Map dicValue = (Map)list.get(i);
//				result+="{id :\""+org.getId()+"\", pId :\""+org.getParentId()+"\", name:\""+org.getOrgName()+"\",parentName:\""+org.getParentName()+"\",orgCode:\""+org.getOrgCode()+"\",orgType:\""+org.getOrgType()+"\",orgIs:\""+org.getIsEffective()+"\",time1:\""+org.getCreateTime()+"\",time2:\""+org.getModifyTime()+"\",spell:\""+org.getChSpell()+"\",ord:\""+org.getDisplayOrder()+"\",isParent:true}";
				result+="{id :\""+dicValue.get("ID")+"\", pId :\""+dicValue.get("PARENT_ID")+"\", name:\""+dicValue.get("DIC_VALUE_NAME")+"\",parentName:\""+dicValue.get("PARENT_NAME")+"\",dicValueCode:\""+dicValue.get("DIC_VALUE_CODE")+"\",is_effective:\""+dicValue.get("IS_EFFECTIVE")+"\",creatTime:\""+(dicValue.get("CREATE_TIME")!=null?format.format(dicValue.get("CREATE_TIME")):"")+"\",lastTime:\""+(dicValue.get("MODIFY_TIME")!=null?format.format(dicValue.get("MODIFY_TIME")):"")+"\",ord:\""+dicValue.get("DISPLAY_ORDER")+"\",isParent:";
				if(Integer.valueOf(String.valueOf(dicValue.get("NUM")))>1){
					result+="true";
				}else{
					result+="false";
				}
				result+="}";
				result+=",";
			}
			result = result.substring(0, result.length()-1);
			result+="]";
		}
		return result;
	}
	
	public int insertDicValueInfo(BaseDicValue baseDicValue) {
		// TODO Auto-generated method stub
		baseDicValue.setId(UUIDGeneratorUtil.getUUID());
		baseDicValue.setCreateTime(new Timestamp(new java.util.Date().getTime()));
		baseDicValue.setModifyTime(new Timestamp(new java.util.Date().getTime()));
		baseDicValue.setChSpell(StringUtil.strTospell(baseDicValue.getDicValueName()));
		String display_order = queryDisplayOrder(baseDicValue.getParentId(),baseDicValue.getDicTypeId());
		BigDecimal B = new BigDecimal(display_order);
		baseDicValue.setDisplayOrder(B);
		if(baseDicValue.getParentId()==""||baseDicValue.getParentId()==null){
			baseDicValue.setParentId(0+"");
		}
		int result = dicValueDao.insert(baseDicValue);
		return result;
	}
	
	public String queryDisplayOrder(String parentId,String dicTypeId) {
		// TODO Auto-generated method stub
		Map paremeter = new HashMap();
		paremeter.put("parent_id", parentId);
		paremeter.put("dicTypeId", dicTypeId);
		List list = dicValueDao.queryDisplayOrderMax(paremeter);
		if (list.size() > 0) {
			return ((Map) list.get(0)).get("DISPLAY_ORDER").toString();
		}
		return null;
	}
	public int modifyDicValueInfo(BaseDicValue baseDicValue) {
		// TODO Auto-generated method stub
		baseDicValue.setModifyTime(new Timestamp(new java.util.Date().getTime()));
		baseDicValue.setChSpell(StringUtil.strTospell(baseDicValue.getDicValueName()));
		baseDicValue.setDisplayOrder(baseDicValue.getDisplayOrder());
		if(baseDicValue.getParentId()==null){
			baseDicValue.setParentId("1");
		}
		int result = dicValueDao.updateByPrimaryKey(baseDicValue);
		return result;
	}
	public int deleteDicValueById(String dicValueId) {
		// TODO Auto-generated method stub
		int result = dicValueDao.deleteByPrimaryKey(dicValueId);
		return result;
	}
	public boolean checkDicValueName(String dicName, String dicTypeId ,String level) {
		// TODO Auto-generated method stub
		boolean isExist = true;
		Map param = new HashMap();
		param.put("dicTypeId", dicTypeId);
		param.put("dicValueName", dicName);
		param.put("level", level);
		List list =dicValueDao.checkDicValueName(param);
		if(list.size()>0){
			isExist = false;
		}
		return isExist;
	}
	public boolean checkDicValueCode(String dicCode, String dicTypeId, String level) {
		// TODO Auto-generated method stub
		boolean isExist = true;
		Map param = new HashMap();
		param.put("dicTypeId", dicTypeId);
		param.put("dicValueCode", dicCode);
		param.put("level", level);
		List list =dicValueDao.checkDicValueCode(param);
		if(list.size()>0){
			isExist = false;
		}
		return isExist;
	}
	public boolean checkDicTypeDicTypeCode(String dicTypeCode) {
		boolean isExist = true;
		List list = dicTypeDao.checkDicTypeCode(dicTypeCode);
		if(list.size()>0){
			isExist = false;
		}
		return isExist;
	}
	public List selectDicValueList(Map param) {
		// TODO Auto-generated method stub
		return dicValueDao.queryDicValueList(param);
	}
	public int changeOrder(Map param) {
		// TODO Auto-generated method stub
		int result=1;
		String displayOrder=String.valueOf(param.get("display_order"));
		String editOrder=String.valueOf(param.get("edit_order"));
		String dicTypeId = String.valueOf(param.get("dicTypeId"));
		String parentId=String.valueOf(param.get("parent_id"));
		String newDisplayOrder = String.valueOf(Integer.valueOf(displayOrder)+Integer.valueOf(editOrder));
		Map params = new HashMap();
		params.put("display_order", newDisplayOrder);
		params.put("parent_id", parentId);
		params.put("dicTypeId", dicTypeId);
		List<BaseDicValue> listIsExtis = dicValueDao.checkDispalyOrder(params);
		List<BaseDicValue> listOldOrg = dicValueDao.checkDispalyOrder(param);
		String maxOrd =((Map)dicValueDao.queryDisplayOrderMax(param).get(0)).get("DISPLAY_ORDER").toString();
		String minOrd =((Map)dicValueDao.queryDisplayOrderMin(param).get(0)).get("DISPLAY_ORDER").toString();
		if(Integer.valueOf(editOrder)>0){
			if(Integer.valueOf(newDisplayOrder)<=Integer.valueOf(maxOrd)-1){
				while(listIsExtis.size()==0){
					newDisplayOrder = String.valueOf(Integer.valueOf(newDisplayOrder)+Integer.valueOf(editOrder));
					params.put("display_order", newDisplayOrder);
					listIsExtis = dicValueDao.checkDispalyOrder(params);
				}
				if(listIsExtis.size()>0){
					BaseDicValue baseDicValue = listIsExtis.get(0);
					baseDicValue.setDisplayOrder(new BigDecimal(displayOrder));
					dicValueDao.updateByPrimaryKey(baseDicValue);
					BaseDicValue oldDicValue=listOldOrg.get(0);
					oldDicValue.setDisplayOrder(new BigDecimal(newDisplayOrder));
					dicValueDao.updateByPrimaryKey(oldDicValue);
				}
			}
		}else{
			if(Integer.valueOf(newDisplayOrder)>Integer.valueOf(minOrd)-1){
				while(listIsExtis.size()==0){
					newDisplayOrder = String.valueOf(Integer.valueOf(newDisplayOrder)+Integer.valueOf(editOrder));
					params.put("display_order", newDisplayOrder);
					listIsExtis = dicValueDao.checkDispalyOrder(params);
				}
				if(listIsExtis.size()>0){
					BaseDicValue baseDicValue = listIsExtis.get(0);
					baseDicValue.setDisplayOrder(new BigDecimal(displayOrder));
					dicValueDao.updateByPrimaryKey(baseDicValue);
					BaseDicValue oldDicValue=listOldOrg.get(0);
					oldDicValue.setDisplayOrder(new BigDecimal(newDisplayOrder));
					dicValueDao.updateByPrimaryKey(oldDicValue);
				}
			}
		}
		return result;
	}
	public List selectDicIdByParent(String string) {
		
		return dicValueDao.selectDicIdByParent(string);
	}
	@Override
	public String selectXzqhJson(Map param) {
		// TODO Auto-generated method stub
		ConfigUtil configUtil=new ConfigUtil();
		String dicTypeId = configUtil.getString("dicTypeId");
		param.put("dicTypeId", dicTypeId);
		List list = dicValueDao.queryDicValueList(param);
		String result="";
		if(list!=null && list.size()!=0){
			result = "[";
			for(int i=0;i<list.size();i++){
				Map xzqh = (Map)list.get(i);
				result+="{id :\""+xzqh.get("ID")+"\", pId :\""+xzqh.get("PARENT_ID")+"\", name:\""+xzqh.get("DIC_VALUE_NAME")+"\",code:\""+xzqh.get("DIC_VALUE_CODE")+"\",ord:\""+xzqh.get("DISPLAY_ORDER")+"\",isParent:";
				if(Integer.valueOf(String.valueOf(xzqh.get("NUM")))>1){
					result+="true";
				}else{
					result+="false";
				}
				result+="}";
				result+=",";
			}
			result = result.substring(0, result.length()-1);
			result+="]";
		}
		return result;
	}
}
