package com.acc.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.acc.framework.util.page.PageBean;

@SuppressWarnings("unchecked")
public interface BaseDao <T>  {
	public List<T> queryByPage(@Param("page") PageBean pageBean,@Param("param")Map<String, Object> param);
	public List<T> selectAll();
	public T selectByPrimaryKey(String id);
	public int deleteByPrimaryKey(String id);
	public int insert(T obj);
	public int updateByPrimaryKey(T obj);
	public List<T> customSql(@Param("sql")String sql);
}
