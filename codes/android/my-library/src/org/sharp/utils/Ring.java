package org.sharp.utils;

import java.util.ArrayList;
import java.util.List;

public class Ring<T> {
	
	List<T> data;
	int startPos = 0;
	int loopCount = 0;
	int pos;
	boolean endOneLoop = false;
	
	EventListener eventListener;
	
	public static interface EventListener {
		void fullyVisited(int loopCount);
	}
	
	public Ring(){
		this.data = new ArrayList<T>();
	}
	
	public Ring(List<T> data){
		this.data = data;
	}
	
	public void addNewData(List<T> data){
		this.data.addAll(data);
	}
	
	public T next(){
		T ret = null;
		if(data != null){
			if(pos >= 0 && pos < data.size()){
				int np = pos + 1;
				if(np >= data.size()){
					np = 0;
				}
				if(np == startPos){
					loopCount++;
					endOneLoop = true;
					if(eventListener != null)
						eventListener.fullyVisited(loopCount);
				}else{
					endOneLoop = false;
				}
				ret = data.get(pos);
				pos = np;
			}else{
				ret = null;
			}
		}
		return ret;
	}
	
	public boolean loopEnded(){
		return endOneLoop;
	}
	
	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}
	
}
