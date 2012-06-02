package org.sharp.intf;

public interface Decorator<T> {
	T decorate(T obj);
}