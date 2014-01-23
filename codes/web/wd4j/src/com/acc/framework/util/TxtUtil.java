package com.acc.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/** 
 * txt文本格式处理类
 * <p></p> 
 * @author sheungxin
 * @version v1.0.0
 * <p>最后更新 by 王虎 @ 2012年12月6日15:00:04 </p>
 * @since 
 */
@SuppressWarnings("unchecked")
public class TxtUtil {
	private static Logger logger=Logger.getLogger(TxtUtil.class);
	private static final String WSTYLE1="PW";//10万数据5秒，超过10万速度明显减慢、内存溢出
	private static final String WSTYLE2="FOS";//10万数据8秒，超过10万同上
	private static final String LJF=",";//
	
	
	/**
	 * 向文件中行写入数据
	 * @param list 待写入数据
	 * @param cols_name 字段名称数组，根据字段在数组中位置决定字段希尔顺序
	 * @param col_name_cn 字段中文名称，以逗号拼接
	 * @param fileName 写入文件名称
	 */
	public static void createTxt(List<Map<String, Object>> list,String[] cols_name,String col_name_cn,String fileName){
		Object writer=null;
		try{
			if(list!=null&&list.size()>0){
				String line="";
				writer=getWriterByStyle(fileName,WSTYLE1);
				StringBuffer buffer=null;
				line=arrayToStr(cols_name,LJF);
				line=line.replaceFirst(LJF, "");
				writeOne(writer,"cloumn_name:"+line.substring(0,line.length()-1));
				writeOne(writer,"cloumn_name_cn:"+col_name_cn);
				for(Map<String, Object> map:list){
					buffer=new StringBuffer();
					for(String col_name:cols_name){
						buffer.append(map.get(col_name));
						buffer.append(LJF);
					}
					line=buffer.toString();
					writeOne(writer,line.substring(0,line.length()-1));
				}
			}
		}catch (Exception e) {e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			try {
				closeWriter(writer, WSTYLE1);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 读取txt文件 10万条5秒左右
	 * @param fileName 文件名称
	 * @return list
	 */
	public static List readTxt(String fileName){
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(new File(fileName)));
			String line=reader.readLine();
			List list=new ArrayList();
			while(line!=null){
				list.add(line);
				line=reader.readLine();
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage()); 
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				logger.error(e.getMessage()); 
			}
		}
		return null;
	}
	
	/**
	 * 向文件写入一行内容
	 * @param writer 执行写入的对象
	 * @param txt 写入内容
	 * @throws IOException 
	 */
	public static void writeOne(Object writer,String txt) throws IOException{
		if(writer instanceof FileOutputStream){
			((FileOutputStream)writer).write((txt+"\r\n").getBytes());
		}else if(writer instanceof PrintWriter){
			((PrintWriter)writer).println(txt);
		}
		
	}
	
	/**
	 * 根据写入方式创建写入执行对象
	 * @param fileName 文件名称
	 * @param style 写入方式松
	 * @return
	 * @throws IOException
	 */
	public static Object getWriterByStyle(String fileName,String style) throws IOException{
		Object obj=null;
		if(style!=null){
			if(style.equals(WSTYLE1)){
				obj=new PrintWriter(new FileWriter(fileName));
			}else if(style.equals(WSTYLE2)){
				obj=new FileOutputStream(new File(fileName), true);
			}
		}
		return obj;
	}
	
	/**
	 * 关闭输出流
	 * @param writer 执行写入的对象
	 * @param style 写入方式
	 * @throws IOException
	 */
	public static void closeWriter(Object writer,String style) throws IOException{
		if(style!=null){
			if(style.equals(WSTYLE1)){
				PrintWriter pw=((PrintWriter)writer);
				pw.flush();
				pw.close();
			}else if(style.equals(WSTYLE2)){
				FileOutputStream fos=((FileOutputStream)writer);
				fos.flush();
				fos.close();
			}
		}
	}
	
	public static String arrayToStr(String[] array,String regex){
		String result="";
		for(String str:array){
			result+=regex+str;
		}
		return result+regex;
	}
}
