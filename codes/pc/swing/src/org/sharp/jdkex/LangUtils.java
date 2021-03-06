package org.sharp.jdkex;

import java.util.Comparator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LangUtils {

	public static StandardToStringStyle toStringStyle = new StandardToStringStyle();
	static {
		toStringStyle.setArrayContentDetail(true);
	}
	public static <toStringStyle> String reflectionToString(Object obj){
		return ToStringBuilder.reflectionToString(obj,toStringStyle);
	}

	public static boolean containsWord(String str1, String word) {
		return StringUtils.containsIgnoreCase(str1, StringUtils.chop(word));
	}

	public static String join(String[] strArray,String sepe){
		strArray = Utils.removeNull(strArray, String.class);
		return StringUtils.join(strArray, sepe);
	}
	
	public static String join(String[] strArray,String sepe,String pre,String af){
		String ret = join(strArray, sepe);
		return join(pre,ret,af);
	}
	
	public static String join(String pre, String str, String af){
		if(!StringUtils.isEmpty(str))
			return StringUtils.defaultIfEmpty(pre, "") + str + 
				StringUtils.defaultIfEmpty(af,"");
		else
			return "";
	}
	
	public static String[] split(String str,String sep){
		return StringUtils.split(str,sep);
	}

	public static String affix(String str, String af){
		return join("",str,af);
	}

	public static Comparator<Object> newComparator(final boolean sa){
		return new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				int ret = compareTo(o1,o2);
				ret = sa?ret:-ret;
				return ret;
				/*int ret = 0;
				if (o1 instanceof String) {
					ret = ((String) o1).compareTo((String) o2);
				} else if (o1 instanceof Number) {
					ret = (int) (((Double) o1) - ((Double) o2));
				}
				if (!sa)
					ret = -ret;
				return ret;*/
			}
		};
	}

	//TODO
	public static Comparator<Object> newComparator(final boolean sa, String properName){
		return new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				int ret = compareTo(o1,o2);
				ret = sa?ret:-ret;
				return ret;
			}
		};
	}

	
	public static <T> Comparator<T> reflectComparator(){
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				return CompareToBuilder.reflectionCompare(o1, o2);
			}
		};
	}

	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	public static String toString(Object object) {
		return ToStringBuilder.reflectionToString(object);
	}

	public static<T> boolean isEmpty(T[] array) {
		return ArrayUtils.isEmpty(array);
	}
	public static <T> boolean contains(T[] array, T s){
		return ArrayUtils.contains(array, s);
	}

	public static <T> T[] remove(T[] array, T... s) {
		return ArrayUtils.removeElements(array, s);
	
	}

	public static int compareTo(Object o1,Object o2){
		int ret = new CompareToBuilder().append(o1, o2).toComparison();
		return ret;
	}

	public static <T> T[] add(T[] array, int i, T o) {
		return ArrayUtils.add(array, i, o);
	}
	
	public static <T> T[] add(T[] array, T o) {
		return ArrayUtils.add(array, o);
	}
	
	public static <T> int length(T[] array){
		return ArrayUtils.getLength(array);
	}
	
	public static int length(String str){
		return StringUtils.length(str);
	}
	
	public static boolean equals(Object o1,Object o2){
		return o1 == null? o2==null :o1.equals(o2);
	}

	public static String capitalize(String str) {
		return StringUtils.capitalize(str);
	}

	public static String subStringBtw(String str, String open, String close) {
		return StringUtils.substringBetween(str, open, close);
	}

}
