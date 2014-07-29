package com.acc.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;


/** 
 * RTF操作类
 * <p></p> 
 * @author 来源于icard_xc
 * @version v1.0.0
 * <p>最后更新 by 王虎 @ 2012年12月6日15:00:04 </p>
 * @since 
 */
public class OperatorRTFUtil {

	static int inext=0;//用来判断中文 编码出现 第一次出现为0 第二次出现为1 add by wde
	
	/**
	 * 把字符串转化为RTF格式
	 * @param content 代转化字符串
	 * @return String
	 * @throws Exception
	 */
	public static String strToRtf(String content) throws Exception{     
	   char[] digital = "0123456789ABCDEF".toCharArray();
	        StringBuffer sb = new StringBuffer("");
	        content=new String(content.getBytes(),"GBK");
	        byte[] bs = content.getBytes("GBK"); 
	        int bit;       
	        for (int i = 0; i < bs.length; i++) {
	            bit = (bs[i] & 0x0f0) >> 4;   
	        /*2009-7-5 add by wde 增加中文支持
	         *思路：通过getBytes获取的中文的assii小于0，根据rtf中文的的编码
	         * 所以只需要在中文的2个编码 第一个编码前加    第二个编码后加
	         * 加了一个变量inext 用来判断中文的assii 前一个和后一个。
	         * 这样在rtf中文的乱码就可以解决了。
	         */ 
	            if(bs[i]>0){
	              sb.append("\\'"); 
	            }else{               
	               if(inext==0){
	                //通过写字板创建的rtf模板 add by wde
	                   //sb.append("\\lang2052\\f1"); 
	                //通过WPS2009创建的rtf模板 add by wde
	                // sb.append("\\lang1033 \\langnp1033 \\langfe2052 \\langfenp2052 \\cf1");
	                //通过MS word创建的rtf模板 add by wde
	                sb.append("\\loch\\af2\\hich\\af2\\dbch\\f31505");
	                sb.append("\\'"); 
	                   inext=1;
	              }else{
	               sb.append("\\'");                   
	              }
	            }        
	            sb.append(digital[bit]);
	            bit = bs[i] & 0x0f;
	            sb.append(digital[bit]);
	            if(bs[i]<0&&inext==1){
	            // 通过写字板创建的rtf模板 add by wde
	            //sb.append("\\lang1033\\f0");
	            // 通过WPS2009创建的rtf模板 add by wde
	            //sb.append(" \\lang1033\\langnp1033 \\langfe2052\\langfenp2052 \\cf1");
	            // 通过MS word创建的rtf模板 add by wde
	             sb.append("\\hich\\af2\\dbch\\af31505\\loch\\f2");
	            inext=0;
	            } 
	        }
	        return sb.toString();       
	}
	
	/**
	 * 以RTF格式替换字符串中部分内容
	 * @param content 待处理字符串
	 * @param markersign 被替换内容
	 * @param replacecontent 替换内容
	 * @return String
	 * @throws Exception
	 */
	public static String replaceRTF(String content,String markersign,String replacecontent) throws Exception{
		String rc = strToRtf(replacecontent);  
		String target = "";
		markersign="$"+markersign+"$";
		target = content.replace(markersign,rc);
		target = new String(target.getBytes("ISO-8859-1"),"GBK");
		return target;
	}
	
	/**
	 * 字节形式读取模板文件内容,将结果转为字符串
	 * @param sourname 模板文件
	 * @param data 代替换部分键值对
	 * @return String
	 * @throws Exception
	 */
	public String rgModel(String sourname, Map<String, Object> data) throws Exception {
		String sourcecontent = "";
		InputStream ins = null;
		try{
			ins = new FileInputStream(sourname);
			//byte[] b = new byte[1024];//对应在while中使用bytesRead = ins.read(b, 0, 1024); 
			//byte[] b = new byte[16384]; //对应在while中使用bytesRead = ins.read(b);
			byte[] b = new byte[1638400];//提高对于RTF文件的读取速度，特别是对于1M以上的文件      
			if (ins == null) {
				System.out.println("源模板文件不存在");
			}
			int bytesRead = 0;
			while (true) {
				//bytesRead = ins.read(b, 0, 1024); // return final read bytes counts
				bytesRead = ins.read(b,0,1638400);
				if(bytesRead == -1) {// end of InputStream
				System.out.println("读取模板文件结束");
				break;
			}
			sourcecontent += new String(b, 0, bytesRead); // convert to string using bytes
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		/* 修改变化部分 */
		String targetcontent = "";
		String oldText="";
		Object newValue;
				   /* 结果输出保存到文件 */
		try {
		Iterator<String> keys = data.keySet().iterator();
		int keysfirst=0;
		while (keys.hasNext())
		{
			oldText =keys.next();
			newValue = data.get(oldText);
			String newText = (String) newValue;
			inext=0;//add by wde 改为初始状态
			if(keysfirst==0){
				targetcontent = replaceRTF(sourcecontent,oldText,newText);
				keysfirst=1;
			}else{
				targetcontent = replaceRTF(targetcontent,oldText,newText);
				keysfirst=1;
			}
		}
		return targetcontent;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String rgModel_String(String sourname,
			Map<String, String> data) throws Exception {
		// TODO Auto-generated method stub
		/* 字节形式读取模板文件内容,将结果转为字符串 */
		String sourcecontent = "";
		InputStream ins = null;
		try {
			ins = new FileInputStream(sourname);
			//byte[] b = new byte[1024];//对应在while中使用bytesRead = ins.read(b, 0, 1024); 
			//byte[] b = new byte[16384]; //对应在while中使用bytesRead = ins.read(b);
			byte[] b = new byte[1638400];//提高对于RTF文件的读取速度，特别是对于1M以上的文件      
			if (ins == null) {
				System.out.println("源模板文件不存在");
			}
			int bytesRead = 0;
			while (true) {
				//bytesRead = ins.read(b, 0, 1024); // return final read bytes counts
				bytesRead = ins.read(b, 0, 1638400);
				if (bytesRead == -1) {// end of InputStream
					System.out.println("读取模板文件结束");
					break;
				}
				sourcecontent += new String(b, 0, bytesRead); // convert to string using bytes
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 修改变化部分 */
		String targetcontent = "";
		String oldText = "";
		Object newValue;
		/* 结果输出保存到文件 */
		try {
			Iterator keys = data.keySet().iterator();
			int keysfirst = 0;
			while (keys.hasNext()) {
				oldText = (String) keys.next();
				newValue = data.get(oldText);
				String newText = (String) newValue;
				if (keysfirst == 0) {
					targetcontent = replaceRTF(sourcecontent, oldText, newText);
					keysfirst = 1;
				} else {
					targetcontent = replaceRTF(targetcontent, oldText, newText);
					keysfirst = 1;
				}
			}

			if (targetcontent.equals("") || targetcontent == "") {
				return sourcecontent;
			} else {
				return targetcontent;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
}
