package com.acc.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * 文件路径处理类
 * <p></p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by 何晓超 @ 2012年12月7日15:48:59 </p>
 * @since 
 */
public class FileUtil {
	private static String temp="";//文件在项目中位置
	/** 初始化文件所在位置：WEB-INF下 **/
    static{
    	try {
			URL classUrl = FileUtil.class
            .getResource("");
			temp=classUrl.getPath();
			temp=temp.substring(0,temp.indexOf("classes"));
		} catch (Exception e) {
			temp="";
		}
    }
	/**
	 * 
	 *取得web应用下的WEB-INF路径。
	 * <p>方法详述（简单方法可不必详述）。</p>
	 * @return String WEB-INF路径
	 */
	public static String getWebinfoPath() {
		String filePath = FileUtil.class.getResource("").toString();
		int begin = filePath.indexOf(":/") + ":/".length();
		filePath = filePath.substring(begin, filePath.indexOf("/WEB-INF/")
				+ "/WEB-INF/".length());
		return filePath;
	}
	/**
	 * 
	 * 取得web应用的路径。
	 * <p>。</p>
	 * @return String 应用的路径
	 */
	public static String getWebPath() {
		String filePath = getWebinfoPath();
		filePath = filePath.substring(0, filePath.indexOf("WEB-INF/"));
		return filePath;
	}
	/**
	 * 
	 * 路径是否存在，存在返回true，否则返回false。
	 * <p></p> 
	 * @param filePath String 路径
	 * @return boolean
	 */
	public static boolean isExist(String filePath){
		File file =new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 根据文件路径创建该文件 如果路径已经创建，返回true，否则返回false。
	 * <p></p>
	 * @param filePath String 文件路径
	 * @return boolean
	 */
	public static boolean createNewFile(String filePath){
		boolean temp=true;
		int index =0;
		index=filePath.lastIndexOf("\\");
		if (index==-1) {
			index=filePath.lastIndexOf("/");
		}
		String path=filePath.substring(0,index);
		if (!isExist(path)) {
			createDir(path);
		}
		File file =new File(filePath);
		try {
			temp= file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	/**
	 * 
	 * 根据路径对象对应的File对象，生成该路径 如果路径已经创建，返回true。
	 * <p></p>
	 * @param file File 路径
	 * @return boolean
	 */
	public static boolean createDir(File file){
		return file.mkdirs();
	}
	/**
	 * 
	 * 根据路径名称字符串生成路径 如果路径已经存在，返回true。
	 * <p></p>
	 * @param dir String 路径
	 * @return boolean
	 */
	public static boolean createDir(String dir){
		File file =new File(dir);
		return file.mkdirs();
	}
	/**
	 * 
	 * 根据文件名称删除文件，如果文件不存在了，返回true。
	 * <p></p>
	 * @param fileName String 路径到文件
	 * @return boolean
	 */
	public static boolean deleteFile(String fileName){
		File file =new File (fileName);
		return file.delete();
	}
	/**
	 * 
	 * 根据目录路径删除目录，如果目录不存在了，返回true。
	 * <p></p>
	 * @param dir String 路径到文件夹
	 * @return boolean
	 */
	public static boolean deleteFolder(String dir){
		boolean temp =true;
		File file =new File (dir);
		File[] files =file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File fileA=files[i];
			if(fileA.isFile()){
				temp=deleteFile(fileA.getAbsolutePath());
				if (!temp) {
					break;
				}
			}else{
				temp=deleteFolder(fileA.getAbsolutePath());
				if (!temp) {
					break;
				}
			}
		}
		temp=file.delete();
		return temp;
	}
	/**
	 * 
	 * 把指定目录下的文件或者文件夹压缩成目标文件。
	 * <p></p>
	 * @param src String 要压缩的文件夹或文件入口
	 * @param des String 压缩完成后的文件出口
	 */
	public static void zipCompress(String src,String des){
		
		try {
			File srcFile =new File (src);
			File desFile =new File (des);
			FileOutputStream outputStream =new FileOutputStream(desFile);
			ZipOutputStream zipOutputStream=new ZipOutputStream(outputStream);
			
			zip(zipOutputStream, srcFile, src,src);
			zipOutputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 生成压缩文件。
	 * <p></p>
	 * @param outputStream ZipOutputStream  压缩文件输出流
	 * @param file         File             压缩目标路径
	 * @param base         String           压缩目标文件名
	 * @param src          String           压缩结果文件名
	 * @throws Exception
	 */
	private static void zip(ZipOutputStream outputStream ,File file,String base,String src)throws Exception{
		if (file.isDirectory()) {
			File [] files=file.listFiles();
			for (int i = 0; i < files.length; i++) {
				zip(outputStream, files[i], base+"/"+files[i].getName(),src);
			}
		}else {
			if (src.indexOf("/")!=-1&&src.lastIndexOf("/")!=src.length()) {
				src+="/";
			}
			if(src.indexOf("\\")!=-1&&src.lastIndexOf("\\")!=src.length()){
				src+="\\";
			}
			String srcStr="";
			if(src.indexOf("/")!=-1){
				srcStr=src.substring(0,src.lastIndexOf("/"));
				srcStr=srcStr.substring(0, srcStr.lastIndexOf("/")+1);
			}
			if (src.indexOf("\\")!=-1) {
				srcStr=src.substring(0,src.lastIndexOf("\\"));
				srcStr=srcStr.substring(0, srcStr.lastIndexOf("\\")+1);
			}
			String path=base.replace(srcStr, "");
			outputStream.putNextEntry(new ZipEntry(path));
			FileInputStream inputStream=new FileInputStream(file);
			int b;
			while ((b=inputStream.read())!=-1) {
				outputStream.write(b);
			}
			inputStream.close();
		}
	}
}