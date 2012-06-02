package org.sharp.utils;

public class Option<T> extends Wrapper<T> {
	public Option(T v){
		set(v);
	}
	
	public boolean isNull(){
		return value == null;
	}
}
