package org.sharpx.utils.jdkex.intf;

public interface Converter<U,V>{
	V to(U u);
}