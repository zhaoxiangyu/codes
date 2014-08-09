package org.sharpx.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class DsUtils {

	public static interface PropertyHandler {
		
		void property(Object obj,PropertyDescriptor p);

		<T> void simpleProperty(Object obj, String pName, T pValue, Class<T> pType);
	}

	public static Logger log = Logger.getLogger(DsUtils.class);

	static {
		try {
			initLog4j();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void initLog4j() throws IOException {
		String pattern = "%d [%t] %c %p - %m @ %l%n";
		Appender fileAppender = new FileAppender(new PatternLayout(pattern),
				"console.log");
		DsUtils.log.addAppender(fileAppender);
		ConsoleAppender consoleAppender = new ConsoleAppender(
				new PatternLayout(pattern), "System.out");
		DsUtils.log.addAppender(consoleAppender);
	
		TextAreaAppender taa = new TextAreaAppender();
		DsUtils.log.addAppender(taa);
		
		/*Logger.getLogger(BasicPlayer.class).addAppender(fileAppender);
		Logger.getLogger(BasicPlayer.class).addAppender(consoleAppender);
		Logger.getLogger(BasicPlayer.class).setLevel(Level.INFO);*/
	}

	public static void introspect(Object obj,PropertyHandler ph){
		BeanInfo info;
		try {
			info = (BeanInfo) Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor p = pds[i];
				final String pName = p.getName();
				Object value = PropertyUtils.getProperty(obj, p.getName());
				Class pt = p.getPropertyType();
				/*log.info("property:"
						+ (pt == null ? "indexed_property" : pt.getSimpleName())
						+ " " + pName);*/
				if (!"class".equalsIgnoreCase(pName) && p.getReadMethod() != null){
					if(isSimple(pt)){
						ph.simpleProperty(obj, pName, value, pt);
					}
					ph.property(obj, p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isSimple(Class clz){
		if(clz == null)
			return false;
		
		return clz.isPrimitive() || clz.getPackage().getName().startsWith("java.lang");
	}

	public static PropertyHandler htmlGenerator(final StringBuffer strBuf){
		return new PropertyHandler() {
			public void property(Object obj, PropertyDescriptor p) {
				try {
					Object value = PropertyUtils.getProperty(obj, p.getName());
					if(value ==null)
						return;
					Class<?> clazz = value.getClass();
					log.debug(p.getName()+":"+value);
					if(clazz.isArray() && value != null){
						int len = Array.getLength(value);
						strBuf.append("<div id='"+p.getName()+"'>");
						for(int i=0;i<len;i++){
							Object e = Array.get(value, i);
							if(isSimple(e.getClass())){
								strBuf.append("<span id='"+p.getName()+"["+i+"]'>");
								strBuf.append(e);
								strBuf.append("</span><br/>");
							}else{
								strBuf.append("<div id='"+p.getName()+"["+i+"]'>");
								DsUtils.introspect(e, this);
								strBuf.append("</div>");
							}
						}
						strBuf.append("</div>");
					}else if(!isSimple(clazz)){
						strBuf.append("<div id='"+p.getName()+"'>");
						DsUtils.introspect(value, this);
						strBuf.append("</div>");
					}else if(isSimple(clazz)){
						strBuf.append("<span id='"+p.getName()+"'>"+
								value+
								"</span><br/>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public <T> void simpleProperty(Object obj, String pName, T pValue, Class<T> pType) {
			}
		};
	}

	public static void sort(Object[] array){
		if(array!=null)
			Arrays.sort(array);
	}
	
	public static <T> T elementAt(T[] array, int i) {
		if(array == null)
			return null;
		if(i >= 0 && i< array.length)
			return array[i];
		else
			return null;
	}

	public static <T> T elementAt(T[] array, int i,T def) {
		if(array == null)
			return def;
		if(i >= 0 && i< array.length)
			return array[i];
		else{
			System.out.println("array index outofbound.");
			return def;
		}
	}

	public static <T> T newInstance(Class<T> clazz){
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static<T> void add(List<T> list, T t, boolean allowDuplicated) {
		if(allowDuplicated){
			list.add(t);
			return;
		}	
		if(!list.contains(t))
			list.add(t);
		else{
			list.remove(t);
			list.add(t);
		}
	}

	public static <T> T[] toArray(List<T> list, Class<T> clazz) {
		if(list ==null)
			return null;
		else
			return (T[])list.toArray((T[])Array.newInstance(clazz, 0));
	}
	
	public static <T> T[] removeNull(T[] array, Class<T> clazz) {
		if(array == null)
			return null;
		List<T> list = new ArrayList<T>();
		for (T t : array) {
			if(t!=null)
				list.add(t);
		}
		return DsUtils.toArray(list, clazz);
	}

	public static <T> void removeNull(List<T> list) {
		if(list == null)
			return;
		for (int i = 0;i<list.size();i++) {
			if(list.get(i) == null)
				list.remove(i);
		}
	}

	public static <T> T defaultIfNull(T value, T def){
		return value==null? def :value;	
	}
	
	public static <T> T[] append(T[] dest, T el){
		List<T> list = Arrays.asList(dest);
		list.add(el);
		return list.toArray(dest);
	}

	public static String toShortStr(Date date) {
		if(date == null)
			return "";
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}

	public static String toShortStr(long time) {
		return toShortStr(new Date(time));
	}

	public static <T> List<T> asList(T[] docs) {
		return Arrays.asList(docs);
	}

	public static<T> T elementAt(List<T> data, Integer index,T def) {
		if(data==null || index <0 || index >= data.size())
			return def;
		else
			return data.get(index);
		
	}

	public static<T> List<T> subList(List<T> data, int[] rows) {
		List<T> li = new ArrayList<T>();
		for (int i : rows) {
			li.add(elementAt(data,i,null));
		}
		removeNull(li);
		return li;
	}

	public static<T> boolean isEmpty(List<T> sal) {
		return sal == null && sal.size() <= 0;
	}

	public static<T> int at(T[] ar, T pos, int def) {
		if(ar == null )
			return def;
		for(int i=0;i<ar.length;i++){
			if(pos == ar[i])
				return i;
		}
		return def;
	}

	public static int nextInt(int size) {
		return (int) Math.round(Math.random()*size);
	}

	public static <T> boolean isNull(T sa) {
		return sa == null;
	}
	
}
