package org.sharp.intf;

public interface Mapper<U,V>{
	V map(U obj);
}