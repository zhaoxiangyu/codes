package org.sharp.utils;

import java.util.HashMap;
import java.util.Map;

public class DataUtils {
	
	private Map<String, Map<?,?>> dataMaps = new HashMap<String,Map<?,?>>();
	private static DataUtils mdu;
	
	public <O> O singleObject(Class<O> clazz, String id){
		return null;
	}
	
	private <K,V> Map<K,V> singleMap(String id){
		if(dataMaps.containsKey(id))
			return (Map<K,V>)dataMaps.get(id);
		else{
			Map<K,V> map = new HashMap<K,V>();
			dataMaps.put(id, map);
			return map;
		}
	}
	
	public <K,V> V mapValue(String id, K key, V defV){
		Map<K,V> map = singleMap(id);
		if(map.containsKey(key)){
			return map.get(key);
		}else{
			if(key != null && defV != null){
				map.put(key, defV);
			}
			return defV;
		}
	}
	
	public static DataUtils singleton(){
		if(mdu == null){
			mdu = new DataUtils();
		}
		return mdu;
	}
}
