package org.sharp.vocreader.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.sharp.vocreader.beans.AudioInfoV2;

//private List<AudioInfo> infoList = new ArrayList<AudioInfo>();
//private List<AudioInfo> levelList = new ArrayList<AudioInfo>();
public class LevelsInfo {
	private Map<Integer,List<AudioInfoV2>> levelListMap = new HashMap<Integer, List<AudioInfoV2>>();
	
	List<AudioInfoV2> infoList(){
		return levelList(-1);
	}

	public List<AudioInfoV2> levelList(int level){
		if(levelListMap.containsKey(level)){
			return levelListMap.get(level);
		}else{
			List<AudioInfoV2> lList = new ArrayList<AudioInfoV2>();
			levelListMap.put(level, lList);
			return lList;
		}
	}
	public int levelWC(int level){
		if(levelListMap.containsKey(level)){
			return levelListMap.get(level).size();
		}else{
			return 0;
		}
	}
	public void add(AudioInfoV2 ai){
		levelList(ai.level).add(ai);
	}
	public void remove(AudioInfoV2 ai){
		levelList(ai.level).remove(ai);
	}
	public void clear(int start,int end){
		for(int i=start;i <= end;i++){
			levelList(i).clear();
		}
	}
	public String toString(int start,int end,String separator){
		Integer[] sa = new Integer[end-start+1];
		for(int i=start;i <= end;i++){
			sa[i]=levelList(i).size();
		}
		return StringUtils.join(sa, separator);
	}
}