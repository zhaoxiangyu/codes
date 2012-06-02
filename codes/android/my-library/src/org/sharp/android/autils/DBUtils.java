package org.sharp.android.autils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DBUtils {

	public static String columnDefinitionOf(Field f){
		String tn = "TEXT";
		Class type = f.getType();
		if("int".equalsIgnoreCase(type.getSimpleName())||"integer".equalsIgnoreCase(type.getSimpleName())||
				"long".equalsIgnoreCase(type.getSimpleName())){
			tn = "INTEGER";
		}
		return f.getName()+" "+tn;
	}

	public static ArrayList<String> fieldDefinitions(Class clazz) {
		Field[] fds = clazz.getDeclaredFields();
		ArrayList<String> ret = new ArrayList<String>();
		for (Field field : fds) {
			ret.add(columnDefinitionOf(field));
		}
		return ret;
	};
}
