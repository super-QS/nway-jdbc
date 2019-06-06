/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nway.spring.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.nway.spring.jdbc.bean.BeanHandler;
import com.nway.spring.jdbc.bean.BeanListHandler;
import com.nway.spring.jdbc.json.JsonHandler;
import com.nway.spring.jdbc.json.JsonHandlerNRC;
import com.nway.spring.jdbc.json.JsonListHandler;
import com.nway.spring.jdbc.json.JsonListHandlerNRC;

/**
 * ע�⣺
 * <p>
 * &nbsp;&nbsp;��ѯ��������ʱ��
 * <ul>
 * <li>queryForBean����null��</li>
 * <li>queryForBeanList����ֵsize() == 0��</li>
 * <li>queryForBeanPagination����ֵgetTotalCount() == 0��</li>
 * <li>queryForMapListPagination����ֵgetTotalCount() == 0��</li>
 * <li>queryForJson����"{}"</li>
 * <li>queryForJsonList����"[]"</li>
 * <li>testJsonPagination���ض�����totalCount == 0</li>
 * </ul>
 * 
 * @author zdtjss@163.com
 *
 * @since 2014-03-28
 */
public class SqlExecutor extends JdbcTemplate {

	private PaginationSupport paginationSupport;

	/**
	 * ���һ������ ) ��β�� order by ƥ������ <br>
	 */
	private static final Pattern SQL_ORDER_BY_PATTERN = Pattern
			.compile(".+\\p{Blank}+ORDER\\p{Blank}+BY[\\,\\p{Blank}\\w\\.]+");
	/**
	 * SQL �����topƥ��
	 */
	private static final Pattern SQL_TOP_PATTERN = Pattern.compile(".+TOP\\p{Blank}+\\d+\\p{Blank}+.+");

	public <T> T queryForBean(String sql, Class<T> type) throws DataAccessException {

		return super.query(sql, new BeanHandler<T>(type, sql));
	}
	
	public <T> T queryForBean(String sql, Class<T> type, Object... args) throws DataAccessException {
		
		return super.query(sql, new BeanHandler<T>(type, sql), args);
	}

	public <T> T queryForBean(String sql, Object[] args, Class<T> type) throws DataAccessException {

		return super.query(sql, args, new BeanHandler<T>(type, sql));
	}

	public <T> T queryForBean(String sql, Object[] args, int[] argTypes, Class<T> type)
			throws DataAccessException {

		return super.query(sql, args, argTypes, new BeanHandler<T>(type, sql));
	}

	public <T> List<T> queryForBeanList(String sql, Class<T> type) throws DataAccessException {

		return super.query(sql, new BeanListHandler<T>(type, sql));
	}
	
	public <T> List<T> queryForBeanList(String sql, Class<T> type, Object... args) throws DataAccessException {
		
		return super.query(sql, new BeanListHandler<T>(type, sql), args);
	}

	public <T> List<T> queryForBeanList(String sql, Object[] args, Class<T> type) throws DataAccessException {

		return super.query(sql, args, new BeanListHandler<T>(type, sql));
	}

	public <T> List<T> queryForBeanList(String sql, Object[] args, int[] argTypes, Class<T> type)
			throws DataAccessException {

		return super.query(sql, args, argTypes, new BeanListHandler<T>(type, sql));
	}
	
	public String queryForJson(String sql, Class<?> type) throws DataAccessException {

		return super.query(sql, new JsonHandler(type, sql));
	}
	
	public String queryForJson(String sql, Class<?> type,Object... args) throws DataAccessException {
		
		return super.query(sql, new JsonHandler(type, sql), args);
	}
	
	public String queryForJson(String sql,Object[] args) throws DataAccessException {
		
		return super.query(sql, args, new JsonHandlerNRC(sql));
	}
	
	public String queryForJson(String sql,Object[] args, Class<?> type) throws DataAccessException {
	    
	    return super.query(sql, args, new JsonHandler(type, sql));
	}
	
	public String queryForJson(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        
        return super.query(sql, args, argTypes, new JsonHandlerNRC(sql));
    }

	public String queryForJson(String sql, Object[] args, int[] argTypes, Class<?> type) throws DataAccessException {

		return super.query(sql, args, argTypes, new JsonHandler(type, sql));
	}
	
	public String queryForJsonList(String sql) throws DataAccessException {
		
		return super.query(sql, new JsonListHandlerNRC(sql));
	}
	
	public String queryForJsonList(String sql, Class<?> type) throws DataAccessException {
	    
	    return super.query(sql, new JsonListHandler(type, sql));
	}
	
	public String queryForJsonList(String sql, Object... args) throws DataAccessException {
		
		return super.query(sql, new JsonListHandlerNRC( sql), args);
	}
	
	public String queryForJsonList(String sql, Class<?> type, Object... args) throws DataAccessException {
	    
	    return super.query(sql, new JsonListHandler(type, sql), args);
	}
	
	public String queryForJsonList(String sql, Object[] args, Class<?> type) throws DataAccessException {
	    
	    return super.query(sql, args, new JsonListHandler(type, sql));
	}
	
	public String queryForJsonList(String sql, Object[] args, int[] argTypes) throws DataAccessException {
	    
	    return super.query(sql, args, argTypes, new JsonListHandlerNRC(sql));
	}
	
	public String queryForJsonList(String sql, Object[] args, int[] argTypes, Class<?> type) throws DataAccessException {
	    
	    return super.query(sql, args, argTypes, new JsonListHandler(type, sql));
	}
	
	public <T> Pagination<T> queryForBeanPagination(String sql, Object[] params, int page,
			int pageSize, Class<T> beanClass) throws DataAccessException {
		
		return queryForBeanPagination(sql, params, null, page, pageSize, beanClass);
	}

	public <T> Pagination<T> queryForBeanPagination(String sql, Object[] params,int[] argTypes, int page,
			int pageSize, Class<T> beanClass) throws DataAccessException {

		List<T> item = Collections.emptyList();

		String upperSql = sql.toUpperCase();

		String countSql = buildPaginationCountSql(upperSql);

		int totalCount = queryCount(countSql, params, argTypes);
		
		if (totalCount != 0) {

			String paginationSql = paginationSupport.buildPaginationSql(upperSql, page, pageSize);

			if (argTypes == null) {
				
				item = queryForBeanList(paginationSql, params, beanClass);
			} 
			else {
				
				item = queryForBeanList(paginationSql, params, argTypes, beanClass);
			}
		}

		return new Pagination<T>(item, totalCount, page, pageSize);
	}

	public Pagination<Map<String, Object>> queryForMapListPagination(String sql, Object[] params, int page,
			int pageSize) throws DataAccessException {

		return queryForMapListPagination(sql, params, null, page, pageSize);
	}
	
	/**
	 * 
	 * 
	 * @param sql
	 *            ��ѯ���ݵ�SQL
	 * @param params
	 *            SQL����
	 * @param page
	 *            ��ǰҳ��<b>����ʱ����ѯ���м�¼</b>
	 * @param pageSize
	 *            ÿҳ��ʾ��������<b>����ʱ����ѯ���м�¼</b>
	 * @return
	 * @throws DataAccessException
	 */
	public Pagination<Map<String, Object>> queryForMapListPagination(String sql, Object[] params, int[] argTypes,
			int page, int pageSize) throws DataAccessException {

		List<Map<String, Object>> item = Collections.emptyList();

		String upperCaseSql = sql.toUpperCase();

		String countSql = buildPaginationCountSql(upperCaseSql);

		int totalCount = queryCount(countSql, params, argTypes);

		if (totalCount != 0) {

			String paginationSql = paginationSupport.buildPaginationSql(upperCaseSql, page, pageSize);

			if (argTypes == null) {
				
				item = queryForList(paginationSql, params);
			} 
			else {
				item = queryForList(paginationSql, params, argTypes);
			}
		}

		return new Pagination<Map<String, Object>>(item, totalCount, page, pageSize);
	}

	public String queryForJsonPagination(String sql, Object[] params, int page, int pageSize)
			throws DataAccessException {
		
		return queryForJsonPagination(sql, params, null, page, pageSize, null);
	}
	
	public String queryForJsonPagination(String sql, Object[] params, int page, int pageSize, Class<?> beanClass)
	        throws DataAccessException {
	    
	    return queryForJsonPagination(sql, params, null, page, pageSize, beanClass);
	}
    
    public String queryForJsonPagination(String sql, Object[] params, int[] argTypes, int page, int pageSize)
            throws DataAccessException {
        
        return queryForJsonPagination(sql, params, argTypes, page, pageSize, null);
    }
    
	public String queryForJsonPagination(String sql, Object[] params, int[] argTypes, int page, int pageSize,
			Class<?> beanClass) throws DataAccessException {
		
		StringBuilder json = new StringBuilder("{");
		
		String countSql = buildPaginationCountSql(sql);

		int pageCount = 0;
		int totalCount = queryCount(countSql, params, argTypes);

		if (totalCount != 0) {

			String paginationSql = paginationSupport.buildPaginationSql(sql, page, pageSize);

			if (argTypes == null)
			{
			    if(beanClass == null) {
			        
			        json.append("\"pageData\":").append(queryForJsonList(paginationSql, params)).append(',');
			    }
			    else {
			        
			        json.append("\"pageData\":").append(queryForJsonList(paginationSql, params, beanClass)).append(',');
			    }
			}
			else {
			    if(beanClass == null) {
			        
			        json.append("\"pageData\":").append(queryForJsonList(paginationSql, params, argTypes)).append(',');
			    }
			    else {
			        
			        json.append("\"pageData\":").append(queryForJsonList(paginationSql, params, argTypes, beanClass)).append(',');
			    }
			}
			
			pageCount = totalCount / pageSize;
			
			if (totalCount % pageSize > 0) {
				
				pageCount++;
			}
		}
		
		json.append("\"totalCount\":").append(totalCount).append(",\"pageCount\":").append(pageCount).append(",\"page\":").append(page).append(",\"pageSize\":").append(pageSize).append('}');
		
		return json.toString();
	}
    
	public <T> T queryForObject(Class<T> requiredType, String sql, T defaultValue) throws DataAccessException {

    	T obj = null;

		try 
		{
			obj = super.queryForObject(sql, requiredType);
		}
		catch (EmptyResultDataAccessException e)
		{
			obj = defaultValue;
		}

		return obj;
	}
	
	public <T> T queryForObject(Class<T> requiredType, String sql, Object[] args, T defaultValue)
			throws DataAccessException {

		T obj = null;

		try 
		{
			obj = super.queryForObject(sql, args, requiredType);
		}
		catch (EmptyResultDataAccessException e)
		{
			obj = defaultValue;
		}

		return obj;
	}
	
	public <T> T queryForObject(Class<T> requiredType, String sql, Object[] args, int[] argTypes, T defaultValue)
			throws DataAccessException {

		T obj = null;

		try 
		{
			obj = super.queryForObject(sql, args, argTypes, requiredType);
		}
		catch (EmptyResultDataAccessException e)
		{
			obj = defaultValue;
		}

		return obj;
	}
	
	/**
	 *
	 * @param sql
	 *            ԭSQL
	 * @return
	 */
	private String buildPaginationCountSql(String sql) {
		
		StringBuilder countSql = new StringBuilder(sql);
		StringBuilder upperSql = new StringBuilder(sql.toUpperCase());

		if (SQL_ORDER_BY_PATTERN.matcher(upperSql).matches()) {
			
			upperSql.delete(upperSql.lastIndexOf(" ORDER "), upperSql.length());
			
			countSql.delete(upperSql.lastIndexOf(" ORDER "), upperSql.length());
		}
		
		int firstFromIndex = firstFromIndex(upperSql.toString(), 0);
		
		String selectedColumns = upperSql.substring(0, firstFromIndex);
		
		if (selectedColumns.indexOf(" DISTINCT ") == -1 && !SQL_TOP_PATTERN.matcher(selectedColumns).matches()) {

			upperSql.delete(0, firstFromIndex).insert(0, "SELECT COUNT(1)");
			
			countSql.delete(0, firstFromIndex).insert(0, "SELECT COUNT(1)");
		} 
		else {

			countSql.insert(0, "SELECT COUNT(1) FROM (").append(')');
		}
		
		return countSql.toString();
	}

	private void initPaginationSupport() {

		Connection conn = null;
		
		String databaseProductName = null;

		try {
			
			conn = getDataSource().getConnection();
					
			databaseProductName = conn.getMetaData().getDatabaseProductName().toUpperCase();
		} 
		catch (SQLException e) {
			
			throw new CannotGetJdbcConnectionException("�������ݿ�ʧ��", e);
		}
		finally {
			
			if(conn != null) {
				
				DataSourceUtils.releaseConnection(conn, getDataSource());
			}
		}

		if (databaseProductName.contains("ORACLE")) {
			
			this.paginationSupport = new OraclePaginationSupport();
		} 
		else if (databaseProductName.contains("MYSQL")
				|| databaseProductName.contains("MARIADB")) {
			
			this.paginationSupport = new MysqlPaginationSupport();
		} 
		else {
			
			throw new UnsupportedOperationException("�ݲ�֧�ֱ����ݿ�ķ�ҳ��������ʵ��com.nway.spring.jdbc.PaginationSupport�ӿڣ�ͨ������setPaginationSupport�������롣");
		}
	}
	
	private int queryCount(String countSql, Object[] params, int[] argTypes) {

		if (argTypes == null) {
			
			return query(countSql, params, new IntegerResultSetExtractor(countSql));
		} 
		else {
			
			return query(countSql, params, argTypes, new IntegerResultSetExtractor(countSql));
		}

	}


	@Override
	public void afterPropertiesSet() {
		
		super.afterPropertiesSet();

		if (getPaginationSupport() == null) {
			
			initPaginationSupport();
		}
	}
	
	public PaginationSupport getPaginationSupport() {

		return paginationSupport;
	}

	public void setPaginationSupport(PaginationSupport paginationSupport) {

		this.paginationSupport = paginationSupport;
	}

	private class IntegerResultSetExtractor implements ResultSetExtractor<Integer> {
		
		private String sql;
		
		IntegerResultSetExtractor(String sql) {
			
			this.sql = sql;
		}
		
		@Override
		public Integer extractData(ResultSet rs) {
			
			try {
				
				return rs.next() ? rs.getInt(1) : 0;
			} 
			catch (SQLException e) {
				
				throw new InvalidResultSetAccessException("��ȡ��������ʧ��", sql, e);
			}
		}
	}

	private int firstFromIndex(String sql, int startIndex) {

		int fromIndex = sql.indexOf("FROM", startIndex);

		char previousChar = sql.charAt(fromIndex - 1);

		char nextChar = sql.charAt(fromIndex + 4);

		if (!(previousChar == ' ' || previousChar == '*' || previousChar == '\t' || previousChar == '\n')
				&& !(nextChar == ' ' || nextChar == '(' || nextChar == '\t' || nextChar == '\n')) {

			fromIndex = firstFromIndex(sql, fromIndex + 4);
		}

		return fromIndex;
	}
	
}