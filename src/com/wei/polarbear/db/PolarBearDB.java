package com.wei.polarbear.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wei.polarbear.bean.OrdinaryUser;
import com.wei.polarbear.bean.User;
import com.wei.polarbear.tools.Tools;

public class PolarBearDB extends SqliteDB {

	public static final String DB_NAME = "PolarBear.db";
	
	public static final String ADMIN_TABLE = "adminAccount";
	public static final String USER_TABLE = "userAccount";
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String CREATE_TIME = "createTime";
	public static final String DESCR = "descr";
	public static final String ADMIN_ID = "adminID";
	
	public PolarBearDB() {
		super(DB_NAME);
	}

	@Override
	public void create(Statement statement) {
		
		String sql = "create table adminAccount ("
				+ "id integer primary key autoincrement,"
				+ "name varchar(100) not null,"
				+ "password varchar(100) not null,"
				+ "createTime varchar(50),"
				+ "descr text"
				+ ");";
		
		String sql2 = "create table userAccount ("
				+ "id integer primary key autoincrement,"
				+ "adminID integer,"
				+ "name varchar(100) not null,"
				+ "password varchar(100) not null,"
				+ "createTime varchar(50),"
				+ "descr text"
				+ ");";
		
		try {
			statement.execute(sql);
			statement.execute(sql2);
		} catch (SQLException e) {
			Tools.log.error("创建表失败", e);
		}
	}
	
	/**
	 * 插入一个用户信息到adminAccount表中
	 * @param user 用户信息
	 * @return 返回插入到数据库中id,>0为插入成功,<=0为插入失败
	 */
	public int insertAdminUser(User user) {
		
		if (user == null)	return -1;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "insert into adminAccount(name, password, createTime, descr)"
					+ "values(?, ?, ?, ?);";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getCreateTime());
			preparedStatement.setString(4, user.getDescr());
			
			return preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return -1;
	}
	
	/**
	 * 删除adminAccount表中指定的用户信息
	 * @param user 用户信息
	 * @return true:删除成功,false:删除失败
	 */
	public boolean deleteAdminUser(User user) {
		
		if (user == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "delete from adminAccount where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, user.getId());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * 更新adminAccount表中指定的用户信息
	 * @param user 用户信息
	 * @return true:更新成功,false:更新失败
	 */
	public boolean updateAdminUser(User user) {
		
		if (user == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "update adminAccount set password = ?, descr = ? where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getDescr());
			preparedStatement.setInt(3, user.getId());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * 查询adminAccount表指定的用户名的相应用户信息
	 * @param name 用户名
	 * @return 查询的用户信息
	 */
	public User queryAdminUser(String name) {
		
		if (name == null
				|| name.trim().length() <= 0) {
			return null;
		}
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select id, name, password, createTime, descr from adminAccount where name = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, name);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				User user = new User();
				
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setCreateTime(resultSet.getString("createTime"));
				user.setDescr(resultSet.getString("descr"));
				
				return user;
			}
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * 查询adminAccount表中所有用户信息
	 * @return List<User>所有用户信息
	 */
	public List<User> queryAdminUsers() {
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select id, name, password, createTime, descr from adminAccount;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			
			List<User> users = new ArrayList<User>();
			
			while (resultSet.next()) {
				User user = new User();
				
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setCreateTime(resultSet.getString("createTime"));
				user.setDescr(resultSet.getString("descr"));
				
				users.add(user);
			}
			
			return users;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return null;
	}
	
	
	/**
	 * 插入一个用户信息到userAccount表中
	 * @param user 用户信息
	 * @return true:插入成功,false:插入失败
	 */
	public boolean insertOrdinaryUser(OrdinaryUser user) {
		
		if (user == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "insert into userAccount(adminID, name, password, createTime, descr)"
					+ "values(?, ?, ?, ?, ?);";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, user.getAdminID());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getCreateTime());
			preparedStatement.setString(5, user.getDescr());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * 删除userAccount表中指定的用户信息
	 * @param user 用户信息
	 * @return true:删除成功,false:删除失败
	 */
	public boolean deleteOrdinaryUser(OrdinaryUser user) {
		
		if (user == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "delete from userAccount where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, user.getId());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * 更新userAccount表中指定的用户信息
	 * @param user 用户信息
	 * @return true:更新成功,false:更新失败
	 */
	public boolean updateOrdinaryUser(OrdinaryUser user) {
		
		if (user == null)	return false;
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "update userAccount set name = ?, password = ?, descr = ? where id = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getDescr());
			preparedStatement.setInt(4, user.getId());
			
			int reuslt = preparedStatement.executeUpdate();
			return reuslt > 0 ? true : false;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * 查询userAccount表指定的用户名的相应用户信息
	 * @param name 用户名
	 * @return 查询的用户信息
	 */
	public User queryOrdinaryUser(String name) {
		
		if (name == null
				|| name.trim().length() <= 0) {
			return null;
		}
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select id, adminID, name, password, createTime, descr from userAccount where name = ?;";
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, name);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				OrdinaryUser user = new OrdinaryUser();
				
				user.setId(resultSet.getInt("id"));
				user.setAdminID(resultSet.getInt("adminID"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setCreateTime(resultSet.getString("createTime"));
				user.setDescr(resultSet.getString("descr"));
				
				return user;
			}
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * 查询userAccount表中所有用户信息
	 * @return List<OrdinaryUser>所有用户信息
	 */
	public List<OrdinaryUser> queryOrdinaryUsers(User adminUser, String param, String[] paramValues) {
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		if (adminUser == null) return null;
		
		try {
			StringBuilder sql = new StringBuilder("select id, adminID, name, password, createTime, descr from userAccount where adminID = ?");
			if (param != null) sql.append(" " + param);
			sql.append(";");
			
			conn = getConnection();
			preparedStatement = conn.prepareStatement(sql.toString());
			
			preparedStatement.setInt(1, adminUser.getId());
			if (paramValues != null) {
				for (int i = 0; i < paramValues.length; i++) {
					preparedStatement.setString(i + 2, paramValues[i]);
				}
			}
			
			resultSet = preparedStatement.executeQuery();
			
			List<OrdinaryUser> users = new ArrayList<OrdinaryUser>();
			
			while (resultSet.next()) {
				OrdinaryUser user = new OrdinaryUser();
				
				user.setId(resultSet.getInt("id"));
				user.setAdminID(resultSet.getInt("adminID"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setCreateTime(resultSet.getString("createTime"));
				user.setDescr(resultSet.getString("descr"));
				
				users.add(user);
			}
			
			return users;
		} catch (ClassNotFoundException e) {
			Tools.log.error("ClassNotFoundException", e);
		} catch (SQLException e) {
			Tools.log.error("SQLException", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStatement);
			closeConnection(conn);
		}
		return null;
	}
}
