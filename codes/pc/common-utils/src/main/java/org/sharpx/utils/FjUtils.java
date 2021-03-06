package org.sharpx.utils;

import java.io.File;
import java.util.Comparator;

import org.sharpx.utils.intf.Converter;
import org.sharpx.utils.intf.Equivalent;

import fj.F;
import fj.data.Array;

public class FjUtils {

	public static <T> String[] toStringArray(T[] array){
		Array<T> arra = Array.array(array);
		return arra.map(new F<T,String>(){
			@Override
			public String f(T t) {
				return t.toString();
			}}).array(String[].class);
	}
	
	public static <T extends Object> String[] classNames(T[] array){
		Array<T> arra = Array.array(array);
		return arra.map(new F<T,String>(){
			@Override
			public String f(T o) {
				return o.getClass().getName();
			}
		}).array(String[].class);
	}
	
	public static <U> U[] newInstances(String[] array,Class<U[]> clazz){
		Array<String> arra = Array.array(array);
		return arra.map(new F<String,U>(){
			@Override
			public U f(String cn) {
				try {
					return (U)Class.forName(cn).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}).array(clazz);
		/* new java.lang.reflect.Array().newInstance(clazz, 0).getClass());*/
	}
	
	public static <U,V> V[] convertTo(U[] array,Class<V[]> clazz,final Converter<U,V> c){
		Array<U> arra = Array.array(array);
		return arra.map(new F<U,V>(){
			@Override
			public V f(U cn) {
				return c.to(cn);
			}
		}).array(clazz);
	}

	public static<T> void sort(T[] array,Comparator<T> cp){
		Array<T> arra = Array.array(array);
		arra.toList().sort(null);
	}
	
	public static <T> String join(T[] array,Class<T> clazz, String sepe){
		return LangUtils.join(FjUtils.toStringArray(array), sepe);
	}
	
	public static<T> boolean contains(T[] arr,T o, Equivalent<T> eq){
		//TODO
		return false;
	}

	public static File[] toFiles(final String baseDir,String[] fps) {
		Array<String> arra = Array.array(fps);
		return arra.map(new F<String,File>(){
			@Override
			public File f(String fp) {
				try {
					return new File(baseDir,fp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}).array(File[].class);
	}
	
}
