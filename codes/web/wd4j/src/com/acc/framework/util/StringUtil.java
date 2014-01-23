package com.acc.framework.util;
/**
 * 
 * 字符串工具类。
 * <p></p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by 何晓超 @ 2012年12月7日15:29:11 </p>
 * @since 
 */
public class StringUtil {
	/** 常量,文字省略符号 **/
	public static final String CONTEXT_SYMBOL="...";
	/**
	 * 
	 * 根据标准长度截取字符串，并在后面加上省略符号。
	 * <p></p>
	 * @param resouce  String 截取字符串
	 * @param length   int    截取长度
	 * @return String
	 */
	public static String getSubstring(String resouce,int length){
		resouce=resouce.substring(0,length)+CONTEXT_SYMBOL;
		return resouce;
	}
	/**
	 * 
	 * 去除字符串中的全角半角空格。
	 * <p></p>
	 * @param  resouce String 要去除空格的字符串
	 * @return String
	 */
	public static String toTrimSpace(String resouce){
		if(resouce == null)
			return null;
		resouce = resouce.replaceAll("　| ", "");
		return resouce;
	}
	/**
	 * 
	 * 字符填充，字符串前填充指定字符。。
	 * <p></p>
	 * @param resouce String 填充字符串
	 * @param length  int    填充长度
	 * @param c       char   填充字符
	 * @return String
	 */
	public static String getStringBeforePadding(String resouce,int length,char c){
		int resLength=resouce.length();
		if (resLength<length) {
			String charStr="";
			for (int i = 0; i < length-resLength; i++) {
				charStr+=c;
			}
			resouce=charStr+resouce;
		}
		if (resLength>length) {
			resouce=resouce.substring(0,length);
		}
		return resouce;
	}
	/**
	 * 
	 * 字符填充，字符串后填充指定字符。。
	 * <p></p>
	 * @param resouce String 填充字符串
	 * @param length  int    填充长度
	 * @param c       char   填充字符
	 * @return String
	 */
	public static String getStringAfterPadding(String resouce,int length,char c){
		int resLength=resouce.length();
		if (resLength<length) {
			for (int i = 0; i < length-resLength; i++) {
				resouce+=c;
			}
		}
		if (resLength>length) {
			resouce=resouce.substring(0,length);
		}
		return resouce;
	}
	/**
	 * 
	 * 字符串首字母转换大写。
	 * <p></p>
	 * @param resouce String 需要转换大写首字母字符串
	 * @return String
	 */
	public static String toFirstUpper(String resouce){
		if (resouce!=null&&!resouce.equals("")) {
			String first=resouce.substring(0,1).toUpperCase();
			String other=resouce.substring(1,resouce.length());
			resouce=first+other;
		}
		return resouce;
	}
	/**
	 * 
	 * 字符串首字母转换小写。
	 * <p></p>
	 * @param resouce String 需要转换大写首字母字符串
	 * @return String
	 */
	public static String toFirstLower(String resouce){
		if (resouce!=null&&!resouce.equals("")) {
			String first=resouce.substring(0,1).toLowerCase();
			String other=resouce.substring(1,resouce.length());
			resouce=first+other;
		}
		return resouce;
	}
	/**
	 * 
	 * 是否为空字符，为空时返回true。
	 * <p></p>
	 * @param resouce String 验证字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String resouce){
		if (resouce==null||resouce.equals("")||toTrimSpace(resouce).equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 获取字符串每个汉字拼音首字母。
	 * <p>字符串为字母时返回原字符串</p>
	 * @param resouce String 
	 * @return String
	 */
	public static String strTospell(String resouce) {
		String str = resouce.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { //依次处理str中每个字符
		ch = str.charAt(i);
		temp = new char[] {ch};
		byte[] uniCode = new String(temp).getBytes();

		if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
		buffer.append(temp);
		} else {
		buffer.append(convert(uniCode));
		}
		}
		return buffer.toString();
	}
	/**
	 * 
	 * 获取一个汉字的拼音首字母。
	 * <p>GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码，例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43，0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’</p>
	 * @param bytes
	 * @return
	 */
	private static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
		bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
		if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
		result = firstLetter[i];
		break;
		}
		}
		return result;
	}
	/** 国标码和区位码转换常量 **/
	private static int GB_SP_DIFF = 160;
	/** 存放国标一级汉字不同读音的起始区位码 **/
	private static final int[] secPosValueList = {
	1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
	3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
	4390, 4558, 4684, 4925, 5249, 5600};

	/** 存放国标一级汉字不同读音的起始区位码对应读音 **/
	private static final char[] firstLetter = {
	'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
	'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
	't', 'w', 'x', 'y', 'z'};
	
}
