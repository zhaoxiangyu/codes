package org.sharp.utils;

public class Wrapper<T> {
	T value;
	
	public Wrapper(){
		
	}
	
	public T value(){
		return value;
	}
	
	public void set(T value){
		this.value = value;
	}
}
