package com.acc.sys.diccache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acc.framework.util.Logger;
import com.acc.sys.diccache.service.DicCacheService;

/**
 * 
 * 字典缓存的实现类
 * <p></p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-18 </p>
 * @since v1.0.0
 */
@Component("dicCacheService")
public class DicCacheServiceImpl implements DicCacheService {

	/**
	 * 日志信息
	 */
	Logger log = new Logger(DicCacheServiceImpl.class);
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@SuppressWarnings("unchecked")
	/**
	 * 取得字典项的列表
	 */
	public List<Map<String,Object>> getDictionary(Map<String,Object> map){
		SqlSession sqlSession = getSqlSession();
		try{
			if (sqlSession != null) {
				return sqlSession.selectList("com.acc.sys.commons.dao.BaseDicValueDao.getDictionary",map);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(sqlSession != null)
				sqlSession.close();
		}
		return new ArrayList<Map<String,Object>>();
	}

	
	@SuppressWarnings("unchecked")
	/**
	 * 取得组织机构的列表
	 */
	public List<Map<String,Object>> getOrganDict(){
		List<Map<String,Object>> list = null;
		SqlSession sqlSession = getSqlSession();
		try {
			if (sqlSession != null) {
				list = sqlSession.selectList("com.acc.sys.commons.dao.BaseOrganizeDao.getOrganDict");
			} else {
				list = new ArrayList<Map<String,Object>>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(sqlSession != null)
				sqlSession.close();
		}
		return list;
	}
	
	
	public SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
}
