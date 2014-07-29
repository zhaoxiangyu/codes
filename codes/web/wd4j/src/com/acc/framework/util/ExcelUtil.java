package com.acc.framework.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/** 
 * excel处理类
 * <p></p> 
 * @author 
 * @version v1.0.0
 * <p>最后更新 by 何龙 @ 2013年1月21日16:03:04 </p>
 * @since 
 */
public class ExcelUtil {
	private static Logger logger=Logger.getLogger(ExcelUtil.class);
	
	/**
	 * 生产excel文件
	 * 支持.xls（Office 2003及以前）和.xlsx（Office 2007）两种格式
	 * @param fileName 文件名称，扩展名必须是.xls或者.xlsx
	 * @param list 写入数据列表
	 * @param cols_name 字段名称
	 * @param cols_name_cn 字段中文名称，作为首行标题
	 */
	public static void writeExcel(String fileName,List<Map<String, Object>> list,String[] cols_name,String[] cols_name_cn){
		Workbook wb = null;
		FileOutputStream fileOut;
		try {
			boolean isXlsx = false;
			if(fileName.endsWith(".xls")){
				wb = new HSSFWorkbook();
			}else if(fileName.endsWith(".xlsx")){
				wb = new SXSSFWorkbook(100);
				isXlsx = true;
			}
			fileOut = new FileOutputStream(fileName);
		    Sheet sheet = wb.createSheet("Sheet1");
		    Row excelRow = sheet.createRow(0);
		    if(cols_name_cn!=null){
				for(int i=0;i<cols_name_cn.length;i++){
					excelRow.createCell(i).setCellValue(cols_name_cn[i]);
				}
			}
			if(list!=null&&list.size()>0){
				int row=1;
				String cellStr="";
				for(Map<String, Object> map:list){
					excelRow = sheet.createRow(row);
					for(int i=0;i<cols_name.length;i++){
						cellStr=map.get(cols_name[i])==null?"":map.get(cols_name[i]).toString();
						excelRow.createCell(i).setCellValue(cellStr);
					}
					row++;
				}
			}
		    wb.write(fileOut);
		    fileOut.close();
		    if(isXlsx)
		    	((SXSSFWorkbook)wb).dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取excel文件获取文件中数据
	 * 支持.xls（Office 2003及以前）和.xlsx（Office 2007）两种格式
	 * @param fileName 文件名称，扩展名必须是.xls或者.xlsx
	 * @param sheetName sheet名称 
	 * @return 数据列表
	 */
	public static List<Object[]> readExcel(String fileName,String sheetName){
		List<Object[]> list=new ArrayList<Object[]>();
	    FileInputStream fin = null;
	    Workbook workbook=null;
	    try {
	    	fin = new FileInputStream(fileName);
	    	workbook = WorkbookFactory.create(fin);
	    	Sheet sheet = null;
	    	if(sheetName!=null && !"".equals(sheetName))
	    		sheet =workbook.getSheet(sheetName);
	    	if(sheet == null)
	    		sheet = workbook.getSheetAt(0);
	    	
			for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
				Row row = sheet.getRow(i);
				int columns = row.getLastCellNum() - row.getFirstCellNum();
				Object[] row_data=new Object[columns];
				for(int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
					Cell cell = row.getCell(j);
					row_data[j]=cell.toString();
				}
				list.add(row_data);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
		    try {
		    	fin.close();
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return list;
	}

}
