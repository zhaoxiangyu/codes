package com.acc.framework.util.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * 
 * 分页基础信息类。
 * <p>储存分页基础属性。</p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by 何晓超 @ 2012年12月12日 </p>
 * @since 
 * @param <Object> 实体类对象
 */
@DataTransferObject
public class PageBean <Object> implements Serializable  {

    private static final long serialVersionUID = 587754556498974978L;
    
    /** 每一页显示记录的条数，默认值为10 **/
    private int pageSize = 10;
    /** 总页数 **/
    private int totalPage;
    /** 总记录数 **/
    private int totalResult;
    /** 当前页码 **/
    private int pageNumber =1;
    /** 排序列 **/
    private String sortField;
    /** 显示顺序 **/
    private String showOrder;
    
    /** 返回结果集 **/
    private List<Object> resultList=new ArrayList<Object>();
    
    
	/**
	 * 取得pageSize(每页显示记录数)的值
	 * @return pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置pageSize(每页显示记录数)的值
	 * @param pageSize pageSize的值
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 取得totalPage(总页数)的值
	 * @return totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}
	
	/**
	 * 设置totalPage(总页数)的值
	 * @param totalPage totalPage的值
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	/**
	 * 取得totalResult(总记录数)的值
	 * @return totalResult
	 */
	public int getTotalResult() {
		return totalResult;
	}
	
	/**
	 * 设置totalResult(总记录数)的值
	 * @param totalResult totalResult的值
	 */
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	
	/**
	 * 取得pageNumber(当前页码)的值
	 * @return pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	
	/**
	 * 设置pageNumber(当前页码)的值
	 * @param pageNumber pageNumber的值
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
		
	/**
	 * 取得sortField(排序列)的值
	 * @return sortField
	 */
	public String getSortField() {
		return sortField;
	}
	
	/**
	 * 设置sortField(排序列)的值
	 * @param sortField sortField的值
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	/**
	 * 取得order(显示顺序)的值
	 * @return order
	 */
	public String getShowOrder() {
		return showOrder;
	}
	
	/**
	 * 设置order(显示顺序)的值
	 * @param order order的值
	 */
	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}
	
	/**
	 * 取得resultList(查询结果集)的值
	 * @return resultList
	 */
	public List<Object> getResultList() {
		return resultList;
	}
	
	/**
	 * 设置resultList(查询结果集)的值
	 * @param resultList resultList的值
	 */
	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}
}
