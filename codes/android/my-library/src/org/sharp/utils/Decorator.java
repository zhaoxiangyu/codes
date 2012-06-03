package org.sharp.utils;

public class Decorator<T, D> extends Wrapper<T> {
	D adhesion;
	public Decorator(T v, D decorator){
		set(v);
		this.adhesion = decorator;
	}
	
	public D adhesion(){
		return adhesion;
	}
}
