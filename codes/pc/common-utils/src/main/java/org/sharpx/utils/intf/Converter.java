package org.sharpx.utils.intf;

public interface Converter<U,V>{
	V to(U u);
}