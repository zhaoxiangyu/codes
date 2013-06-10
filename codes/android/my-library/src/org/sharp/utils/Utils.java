package org.sharp.utils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.sharp.intf.Decorator;
import org.sharp.intf.Logger;
import org.sharp.intf.Mapper;

public class Utils {
	private static Logger logger;
	
	public static void setLogger(Logger log){
		logger = log;
	}
	
	public static <U,V> V[] mapTo(U[] array, Mapper<U,V> mapper, Class<V> clazz){
		if(array != null){
			V[] da = (V[])Array.newInstance(clazz, array.length);
			for(int i=0;i<array.length;i++){
				V no = mapper.map(array[i]);
				Array.set(da, i, no);
			}
			return da;
		}else
			return null;
	}
	
	public static <T> void setListElement(List<T> list, int pos, T obj){
		if(pos < 0){
			return;
		}else if(pos > list.size() - 1){
			do	list.add(null);
			while(pos == list.size() - 1);
		}
		list.set(pos, obj);
	}

	public static <T> T[] decorate(T[] array, Decorator<T> decorator){
		if(array != null){
			T[] da = (T[])Array.newInstance(array.getClass().getComponentType(), array.length);
			for(int i=0;i<array.length;i++){
				T no = decorator.decorate(array[i]);
				Array.set(da, i, no);
			}
			return da;
		}else
			return null;
	}
	
	public static int uniqueInt(){
		uInt += 1;
		if(logger != null)
			logger.log("new id:"+uInt);
		return uInt;
	}
	
	public static String uniqueString(){
		return Integer.toString(uniqueInt());
	}
	
	public static String uniqueString(String prefix){
		if(prefix != null)
			return prefix + Integer.toString(uniqueInt());
		else
			return Integer.toString(uniqueInt());
	}
	
	private static int uInt = 0;
	
	public static boolean isSimpleType(Class clazz){
		if(Double.class.equals(clazz) || Double.TYPE.equals(clazz)
				|| Float.class.equals(clazz) || Float.TYPE.equals(clazz)
				|| Integer.class.equals(clazz) || Integer.TYPE.equals(clazz)
				|| Long.class.equals(clazz) || Long.TYPE.equals(clazz)
				|| Short.class.equals(clazz) || Short.TYPE.equals(clazz)
				|| Byte.class.equals(clazz) || Byte.TYPE.equals(clazz)
				|| Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz)
				|| Character.class.equals(clazz) || Character.TYPE.equals(clazz)
				|| String.class.equals(clazz)){
			return true;
		}else
			return false;
	}

	public static String invokeStaticMethod(Class clazz,String methodName){
		try {
			Method m = clazz.getMethod(methodName, new Class[0]);
			if(m!=null && Modifier.isStatic(m.getModifiers())){
				m.invoke(null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int maxDistance(int x, int right, int left) {
		//TODO HELONG
		return 0;
	}
	
}

interface FileHandler {
	void handle(File file);

	void handleDir(File dir);
}

class BaseFileHandler implements FileHandler {

	List<File> fl;
	int fc = 0;
	int dc = 0;

	public BaseFileHandler(List<File> fl) {
		this.fl = fl;
	}

	public void handle(File file) {
		fc++;
	}

	public void handleDir(File dir) {
		dc++;
	}

	public FileHandler summary(Map<String, Integer> map) {
		map.put("fc", new Integer(fc));
		map.put("dc", new Integer(dc));
		return this;
	}
	
}
