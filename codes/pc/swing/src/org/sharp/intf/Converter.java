package org.sharp.intf;

public interface Converter<U,V>{
	V to(U u);
}