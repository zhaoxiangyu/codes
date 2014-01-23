package com.acc.framework.util.page;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.acc.framework.constants.Constants;

@SuppressWarnings("unchecked")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {

	private static String pageSqlId = "";

	@SuppressWarnings("unchecked")
	public Object intercept(Invocation ivk) throws Throwable {

		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler,
					"delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate,
					"mappedStatement");
			if (mappedStatement.getId().matches(pageSqlId)) {
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject error");
				} else {
					Connection connection = (Connection) ivk.getArgs()[0];
					String sql = boundSql.getSql();
					//组合统计总数的sql
					String countSql = "select count(0) from (" + sql + ") myCount";
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
							boundSql.getParameterMappings(), parameterObject);
					setParameters(countStmt, mappedStatement, countBS, parameterObject);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();

					PageBean page = null;
					if (parameterObject instanceof PageBean) {
						page = (PageBean) parameterObject;
						page.setTotalResult(count);
						int pageSize = page.getPageSize();
						int total = (count % pageSize == 0)? (count / pageSize) : (count / pageSize + 1);
						page.setTotalPage(total);//设置总页数
						int pageNumber = page.getPageNumber();
						if(total == 0){//总页码为0时初始化当前页码为1
							page.setPageNumber(1);
						}
						else if(pageNumber > total){//判断输入的页码大于总数据页码时，设页码为total
							page.setPageNumber(total);
						}
					} else if (parameterObject instanceof Map) {
						Map<String, Object> map = (Map<String, Object>) parameterObject;
						page = (PageBean) map.get("page");
						if (page == null)
							page = new PageBean();
						page.setTotalResult(count);
						int pageSize = page.getPageSize();
						int total = (count % pageSize == 0)? (count / pageSize) : (count / pageSize + 1);
						page.setTotalPage(total);//设置总页数
						int pageNumber = page.getPageNumber();
						if(total == 0){//总页码为0时初始化当前页码为1
							page.setPageNumber(1);
						}
						else if(pageNumber > total){//判断输入的页码大于总数据页码时，设页码为total
							page.setPageNumber(total);
						}
					} else {
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "page");
						if (pageField != null) {
							page = (PageBean) ReflectHelper.getValueByFieldName(parameterObject, "page");
							if (page == null)
								page = new PageBean();
							page.setTotalResult(count);
							int pageSize = page.getPageSize();
							int total = (count % pageSize == 0)? (count / pageSize) : (count / pageSize + 1);
							page.setTotalPage(total);//设置总页数
							int pageNumber = page.getPageNumber();
							if(total == 0){//总页码为0时初始化当前页码为1
								page.setPageNumber(1);
							}
							else if(pageNumber > total){//判断输入的页码大于总数据页码时，设页码为total
								page.setPageNumber(total);
							}
							ReflectHelper.setValueByFieldName(parameterObject, "page", page);
						} else {
							throw new NoSuchFieldException(parameterObject.getClass().getName());
						}
					}
					String pageSql = generatePageSql(sql, page,mappedStatement.getConfiguration().getDatabaseId());
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
				}
			}
		}
		return ivk.proceed();
	}

	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(
									propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
								+ " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	/**
	 * 
	 * 生成分页查询SQL。
	 * <p>根据配置的查询SQL，按照不同的数据类型生成不同的分页查询的SQL。</p>
	 * @param sql
	 * @param page
	 * @param mappedStatement 
	 * @return
	 */
	private String generatePageSql(String sql, PageBean page, String dataBaseId) {
		String sqlForPage = sql;
		
		if (page != null && (dataBaseId != null || !dataBaseId.equals(""))) {
			StringBuffer pageSql = new StringBuffer();
			
			if (Constants.DB_ORACLE.equals(dataBaseId.toUpperCase())) {//oracle的分页处理
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(")  tmp_tb where ROWNUM<=");
				pageSql.append((page.getPageNumber()) * page.getPageSize());
				pageSql.append(") where row_id>");
				pageSql.append((page.getPageNumber() - 1) * page.getPageSize());
			}
			else if (Constants.DB_MYSQL.equals(dataBaseId.toUpperCase())) {//mysql的分页处理
				pageSql.append(sql);
				pageSql.append(" limit " + ((page.getPageNumber() - 1) * page.getPageSize()) + "," + page.getPageSize());
			}
			
			sqlForPage =  pageSql.toString();
		} 
		return sqlForPage;
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		
		pageSqlId = p.getProperty("pageSqlId");
		if (pageSqlId == null || pageSqlId.equals("")) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

}