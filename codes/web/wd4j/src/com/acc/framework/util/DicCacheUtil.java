package com.acc.framework.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 字典缓存相关工具类。
 * <p></p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-18 </p>
 * @since v1.0.0
 */
public class DicCacheUtil {
	
	static private Map<String, Map<String,Map<String, Object>>> enumAll = new HashMap<String, Map<String,Map<String, Object>>>();
	public static final String DIC_KEY_END = "_dic";
	private static String baseWebapplicationPath = "";
	static Logger logger = new Logger(DicCacheUtil.class);
	private static String ORDER_COLUMN_NAME = "DISPLAY_ORDER";

	/**
	 * 
	 * 初始化字典文件。
	 * <p></p>
	 * @param basePath 字典文件路径
	 * @param dictValueList 字典项列表
	 * @param orgList 组织机构列表
	 */
	public static void init(String basePath,List<Map<String,Object>> dictValueList, List<Map<String,Object>> orgList) {
		baseWebapplicationPath = basePath;
		enumAll.clear();
		
		Map<String,Map<String, Object>> sub_dict_value_map=null;
		if(dictValueList!=null){
			Map<String,Object> dict_value_map=null;
			for (int i = 0; i < dictValueList.size(); i++) {
				dict_value_map = dictValueList.get(i);
				if(dict_value_map == null){
					continue;
				}
				Object dicTypeCode = dict_value_map.get("DIC_TYPE_CODE");
				if(dicTypeCode == null){
					continue;
				}
				String DIC_TYPE_CODE = dicTypeCode.toString();
				/*String dicName = DIC_TYPE_CODE;
				String parentDicValueCode = (String)dict_value_map.get("PARENT_DIC_VALUE_CODE");
				if(parentDicValueCode != null && !"".equals(parentDicValueCode))
					dicName += "_"+parentDicValueCode;*/
				
				if (enumAll.containsKey(DIC_TYPE_CODE+DIC_KEY_END)) {
					sub_dict_value_map = enumAll.get(DIC_TYPE_CODE+DIC_KEY_END);
					Object value_code = dict_value_map.get("DIC_VALUE_CODE");
					if(value_code != null)
						sub_dict_value_map.put(value_code.toString(),dict_value_map);
				} else {
					sub_dict_value_map = new HashMap<String, Map<String,Object>>();
					Object value_code = dict_value_map.get("DIC_VALUE_CODE");
					if(value_code != null)
						sub_dict_value_map.put(value_code.toString(), dict_value_map);
					enumAll.put(DIC_TYPE_CODE+DIC_KEY_END, sub_dict_value_map);
				}
			}
		}
		
		clearDir(baseWebapplicationPath);
		for (Map.Entry<String, Map<String,Map<String, Object>>> entry : enumAll.entrySet()) {
			logger.debug("dict to xml:"+entry.getKey());
			InitBaseXMLdict(entry.getKey().replace(DIC_KEY_END, ""), entry.getValue(), "DIC_VALUE_CODE", "DIC_VALUE_NAME", "CH_SPELL", "CH_SPELL");	
		}

		if(orgList!=null){
			Map<String,Map<String, Object>> org_dic_list=convertListToMap_dict(orgList, "ORG_CODE");
			enumAll.put("organizes"+DIC_KEY_END,org_dic_list);
			InitBaseXMLdict("organizes", org_dic_list, "ORG_CODE", "ORG_NAME", "CH_SPELL", "CH_SPELL");
		}
	}
	
	private static void clearDir(String directory){
		File dir = new File(directory);
		if(dir.exists() && dir.isDirectory()){
			for(File file : dir.listFiles()){
				boolean suc = file.delete();
				if(!suc){
					logger.error("failed to delete file:"+file.getAbsolutePath());
				}
			}
		}else if(!dir.exists()){
			if(!dir.mkdirs()){
				logger.error("failed to create directory:"+directory);
			}
		}
	}
	
	private static void InitBaseXMLdict(String dict_name, Map<String,Map<String, Object>> dict_list, String col_code, String col_name, String col_full_spell, String col_short_spell) {
		if (dict_list != null ) { // 不管是否超过15项都弹出 && dict_list.size() > 15
			String endline = "\r\n";
			StringBuffer sb = new StringBuffer("<?xml version=\"1.0\"  encoding=\"utf-8\"?>");
			sb.append(endline);
			sb.append("<data>");
			sb.append(endline);
			Map<String, Object> value=null; 
			for (Map.Entry<String,Map<String, Object>> row_map : dict_list.entrySet()) {
				value=row_map.getValue();
				sb.append("	<row DIC_CODE=\"");
				sb.append(value.get(col_code));
				sb.append("\" DIC_TEXT=\"");
				sb.append(value.get(col_name));
				sb.append("");
				sb.append("\" DIC_SPELL=\"");				

				Object short_spell_obj = value.get(col_short_spell);
				String short_spell_cont = short_spell_obj == null ? "" : short_spell_obj.toString().toLowerCase();
				
				sb.append(short_spell_cont);
				sb.append("\" DIC_ASPELL=\"");
				sb.append(short_spell_cont);
				sb.append("\"/>");
				sb.append(endline);
			}
			
			sb.append("</data>");
			
			String file_path = baseWebapplicationPath + "\\dic_" + dict_name + ".xml";
			BufferedWriter bw = null;
			try {
				File file = new File(file_path);
				if (file.exists()) {
				}
				file.createNewFile();
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "utf-8"));
				bw.write(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if (bw != null)
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * 
	 * 返回所有 字典项。
	 * <p></p>
	 * @return 字典项集合
	 */
	public static Map<String, Map<String,Map<String, Object>>> getEnumAll() {
		return enumAll;
	}
	
	/**
	 * 
	 * 把Map对象转换为List对象。
	 * <p></p>
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> convertMapToList(Map<String, Map<String, Object>> map) {
		if(map == null){
			return new ArrayList<Map<String,Object>>();
		}
		Collection<Map<String, Object>> values = map.values();
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>(values);
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> dv1, Map<String, Object> dv2) {
				BigDecimal do1 = (BigDecimal)dv1.get(ORDER_COLUMN_NAME);
				BigDecimal do2 = (BigDecimal)dv2.get(ORDER_COLUMN_NAME);
				if(do1 == null && do2 == null){
					return 0;
				}else if(do1 != null && do2 != null){
					return do1.compareTo(do2);
				}else if(do1 == null && do2 != null){
					return -1;
				}else{
					return 1;
				}
			}
		});
		return list;
	}
	
	/**
	 * 把List对象转换为Map对象
	 * @param list
	 * @param keyColName, 不能有重复值的列名
	 * @return
	 */
	public static Map<Object, Map<String, Object>> convertListToMap(List<Map<String, Object>> list, String keyColName) {
		Map<Object, Map<String, Object>> result_map = new HashMap<Object, Map<String, Object>>();
		if (list != null)
			for(Map<String, Object> map : list) {
				if(map != null){
					Object key = map.get(keyColName);
					result_map.put(key, map);
				}
			}
		return result_map;
	}
	
	/**
	 * 把List对象转换为Map对象
	 * @param list
	 * @param keyColName, 不能有重复值的列名
	 * @return
	 */
	public static Map<String,Map<String, Object>> convertListToMap_dict(List<Map<String, Object>> list, String keyColName) {
		Map<String,Map<String, Object>> result_map = new HashMap<String,Map<String, Object>>();
		if (list != null)
			for(Map<String, Object> map : list) {
				if(map != null){
					Object obj = map.get(keyColName);
					if(obj != null)
						result_map.put(obj.toString(), map);
				}
			}
		return result_map;
	}
}
