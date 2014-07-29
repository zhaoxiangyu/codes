package com.acc.framework.util;

import java.util.Calendar;

/** 
 * 身份证号码处理类
 * <p></p> 
 * @author 来源于qbpt
 * @version v1.0.0
 * <p>最后更新 by 王虎 @ 2012年12月6日15:00:04 </p>
 * @since 
 */
@SuppressWarnings("unchecked")
public class IDCardUtil {
	/**
	 * 把15位身份证号码转化为18位身份证号码
	 * @param idCardNo15 待转换的 15 位身份证号码
	 * @return String 转化后18位身份证号码
	 */
	public static String from15to18(String idCardNo15) {
		idCardNo15 = idCardNo15.trim();
		String centuryStr = "19";//19xx 年用 19，20xx 年用 20
		if (!(isIdCardNo(idCardNo15) && idCardNo15.length() == 15))
			return "";

		int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2, 1 };

		// 通过加入世纪码, 变成 17 为的新号码本体.
		String newNoBody = idCardNo15.substring(0, 6) + centuryStr
				+ idCardNo15.substring(6);

		// 下面算最后一位校验码
		int checkSum = 0;
		for (int i = 0; i < 17; i++) {
			int ai = Integer.parseInt("" + newNoBody.charAt(i)); // 位于 i 位置的数值
			checkSum = checkSum + ai * weight[i];
		}

		int checkNum = checkSum % 11;
		String checkChar = null;

		switch (checkNum) {
		case 0:
			checkChar = "1";
			break;
		case 1:
			checkChar = "0";
			break;
		case 2:
			checkChar = "X";
			break;
		default:
			checkChar = "" + (12 - checkNum);
		}
		return newNoBody + checkChar;
	}

	/**
	 * 把18位身份证号码转化为15位身份证号码
	 * @param idCardNo18 待转换的 18 位身份证号码
	 * @return String 转化后15位身份证号码
	 */
	public static String from18to15(String idCardNo18) {
		idCardNo18 = idCardNo18.trim();
		if (!(isIdCardNo(idCardNo18) && idCardNo18.length() == 18))
			return "";
		return idCardNo18.substring(0, 6) + idCardNo18.substring(8, 17);
	}

	/**
	 * 判断给定的字符串是不是符合身份证号的要求
	 * 
	 * @param str 待校验在字符串
	 * @return boolean true：符合身份证号的要求
	 */
	public static boolean isIdCardNo(String str) {
		if (str == null)
			return false;
		str = str.trim();
		int len = str.length();
		if (len != 15 && len != 18)
			return false;

		for (int i = 0; i < len; i++) {
			String charstr = "" + str.charAt(i);
			try {
				Integer.parseInt(charstr);
			} catch (NumberFormatException e) {
				if (i == 17) {
					if (!charstr.equalsIgnoreCase("x"))
						return false;
				} else
					return false;
			}
		}
		return true;
	}

	/**
	 * 从身份证里得到年龄
	 * @param idnum 身份证号码
	 * @return int idnum长度不满足15或18位返回0
	 */
	public static int getAge(String idnum) {
		idnum = idnum.trim();
		Calendar cl = Calendar.getInstance();
		if (idnum != null && idnum.length() == 18) {
			int year = cl.get(Calendar.YEAR);
			int age = year - Integer.parseInt(idnum.substring(6, 10));
			return age;
		}
		if (idnum != null && idnum.length() == 15) {
			int year = cl.get(Calendar.YEAR);
			int age = year - Integer.parseInt("19" + idnum.substring(6, 8));
			return age;
		}
		return 0;
	}
	
	/**
	 * 从身份证里得到年龄
	 * @param idnum 身份证号码
	 * @return int idnum长度不满足15或18位返回0
	 */
	public static String getBirthday(String idnum) {
		idnum = idnum.trim();
		Calendar cl = Calendar.getInstance();
		if (idnum != null && idnum.length() == 18) {
			String year = idnum.substring(6, 10);
			String month = idnum.substring(10, 12);
			String day = idnum.substring(12, 14);
			return year+"-"+month+"-"+day;
		}
		if (idnum != null && idnum.length() == 15) {
			String year =  "19" + idnum.substring(6, 8);
			String month =  "19" + idnum.substring(8, 10);
			String day =  "19" + idnum.substring(10, 12);
			return year+"-"+month+"-"+day;
		}
		return "";
	}
	
	/**
	 * 从身份证里得到性别，偶数女性，奇数男性
	 * @param idnum 身份证号码
	 * @return int idnum长度不满足15或18位返回0
	 */
	public static String getSex(String idnum) {
		idnum = idnum.trim();
		if (idnum != null && idnum.length() == 18 ) {
			String str = idnum.substring(15, 17);
			int intSex = Integer.valueOf(str);
			if(intSex%2 == 0){
				return "女";
			}
			if(intSex%2 == 1){
				return "男";
			}
		}
		if (idnum != null && idnum.length() == 15) {
			char str = idnum.charAt(14);
			if(str%2 == 0){
				return "女";
			}
			if(str%2 == 1){
				return "男";
			}
		}
		
		return "请输入正确身份证号码";
	}


}

