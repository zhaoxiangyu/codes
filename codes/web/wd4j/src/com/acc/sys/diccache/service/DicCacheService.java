package com.acc.sys.diccache.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * 字典缓存的服务接口。
 * <p></p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-18 </p>
 * @since v1.0.0
 */
@Component
public interface DicCacheService {

	/**
	 * 查询字典数据
	 * @param map	条件：DIC_TYPE_CODE
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> getDictionary(Map<String, Object> map);

	/**
	 * 字典
	 * @param map	数据字典表（BASE_VALUE）中basetype的值
	 * 				如：map.put("BASETYPE", 10);
	 * @return	List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> getOrganDict();

}