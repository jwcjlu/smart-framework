package org.smart4j.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.PropsUtil;

/**
 * 数据库操作助手类
 * 
 * @author jinwei
 *
 */
public final class DatabaseHelper {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	private static ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	private static final BasicDataSource DATA_SOURCE;
	static {
		Properties prop = PropsUtil.loadProps("config.properties");
		DRIVER = prop.getProperty("jdbc.driver");
		URL = prop.getProperty("jdbc.url");
		USERNAME = prop.getProperty("jdbc.username");
		PASSWORD = prop.getProperty("jdbc.password");
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setUsername(USERNAME);
		DATA_SOURCE.setPassword(PASSWORD);
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("can not load jdbc driver", e);
		}
	}

	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null) {
			try {
				conn = DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				logger.error("get connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}

	/*
	 * public static void closeConnection(Connection conn){ if(conn!=null) { try
	 * { conn.close(); } catch (SQLException e) { logger.error(
	 * "close connection failure", e); } }
	 * 
	 * }
	 */
	/*
	 * public static void closeConnection(){ Connection conn=getConnection();
	 * if(conn!=null) { try { conn.close(); } catch (SQLException e) {
	 * logger.error("close connection failure", e); throw new
	 * RuntimeException(e); }finally { CONNECTION_HOLDER.remove(); } }
	 * 
	 * }
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
		List<T> entityList = null;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
			} catch (SQLException e) {
				logger.error("query entity list failure", e);
			} /*
				 * finally { closeConnection(); }
				 */
		}
		return entityList;
	}

	public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
		T entity = null;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
			} catch (SQLException e) {
				logger.error("query entity  failure", e);
			} /*
				 * finally { closeConnection(); }
				 */
		}
		return entity;
	}

	public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
		List<Map<String, Object>> map = null;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				map = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
			} catch (SQLException e) {
				logger.error(" execute query  failure", e);
			} /*
				 * finally { closeConnection(); }
				 */
		}
		return map;
	}

	public static int executeUpdate(String sql, Object... params) {
		int rows = 0;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				rows = QUERY_RUNNER.update(conn, sql, new MapListHandler(), params);
			} catch (SQLException e) {
				logger.error(" execute update  failure", e);
			} /*
				 * finally { closeConnection(); }
				 */
		}
		return rows;
	}

	/**
	 * 插入记录
	 * 
	 * @param object
	 * @param fieldMap
	 * @return
	 */

	public static <T> boolean insertEntity(Class<T> object, Map<String, Object> fieldMap) {
		if (CollectionUtil.isEmpty(fieldMap)) {
			return false;
		}
		String sql = "INSERT INTO " + getTableName(object);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for (String key : fieldMap.keySet()) {
			columns.append(key).append(",");
			values.append("?,");
		}
		columns.replace(columns.lastIndexOf(","), columns.length(), ")");
		values = values.replace(values.lastIndexOf(","), values.length(), ")");
		sql += columns + " VALUES " + values;
		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 更新记录
	 * 
	 * @param object
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> object, long id, Map<String, Object> fieldMap) {
		if (CollectionUtil.isEmpty(fieldMap)) {
			return false;
		}
		String sql = "UPDATE " + getTableName(object);
		StringBuilder columns = new StringBuilder(" SET");
		for (String key : fieldMap.keySet()) {
			columns.append(key).append("=?,");
		}
		sql += columns.substring(0, columns.lastIndexOf(",")) + "WHERE id=?";
		Collection<Object> paramList = fieldMap.values();
		paramList.add(id);
		Object[] params = paramList.toArray();
		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 删除记录 根据主键
	 * 
	 * @param object
	 * @param id
	 * @return
	 */

	public static <T> boolean deleteEntity(Class<T> object, long id) {
		String sql = "DELETE FROM " + getTableName(object) + " WHERE id=?";
		return executeUpdate(sql, id) == 1;
	}

	/**
	 * 根据类名得到表明
	 * 
	 * @param entityClass
	 * @return
	 */
	private static String getTableName(Class<?> entityClass) {
		return entityClass.getSimpleName();

	}

	/**
	 * 开启事务
	 */
	public static void beginTransation() {
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("connection auto commit set false   failure", e);
			throw new RuntimeException(e);
		} finally {
			CONNECTION_HOLDER.set(conn);
		}
	}

	/**
	 * 提交事务
	 */
	public static void commitTransation() {
		Connection conn = getConnection();
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(" commit transation   failure", e);
			throw new RuntimeException(e);
		} finally {
			CONNECTION_HOLDER.remove();
		}

	}

	/**
	 * 回滚事务
	 */

	public static void rollbackTransation() {
		Connection conn = getConnection();
		try {
			conn.rollback();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(" rollback transation   failure", e);
			throw new RuntimeException(e);
		} finally {
			CONNECTION_HOLDER.remove();
		}

	}

}
