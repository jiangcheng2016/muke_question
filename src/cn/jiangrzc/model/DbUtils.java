package cn.jiangrzc.model;
/**
 * 数据访问工具类
 * 四个功能
 * 1 注册驱动
 * 2 获取连接
 * 3 释放资源
 * 4 执行命令(增删改，没有查询)
 * @author 
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtils {

	static Connection conn = null;		//声明Connection 实例化对象
	static PreparedStatement pstat = null;		//预编译sql
	static ResultSet rs = null;				//声明结果集
	static Statement stmt = null;			//声明Statement 实例化对象
	
	// 1创建连接池
	private static ComboPooledDataSource ds;
	static {
		try {
			ds = new ComboPooledDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 2获取连接
	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 3释放资源
	public static void closeAll(ResultSet rs, Statement stat, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 4执行命令
	/**
	 * 
	 * @param sql
	 *            执行的sql语句
	 * @param params
	 *            sql语句中的参数
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params) {
		
		try {
			conn = getConnection();
			pstat = conn.prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < params.length; i++) {
				pstat.setObject(i + 1, params[i]);
			}
			int r = pstat.executeUpdate();
			return r;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(null, pstat, conn);
		}
		return 0;
	}
	
	/**
	 * 5.执行无参数查询
	 * 	sql:查询sql语句
	 */
	public static ResultSet executeQuery(String sql,Object[] param) throws Exception {
		try {
			conn = getConnection();
			if (param != null && param.length > 0) {
				pstat = conn.prepareStatement(sql);		
				for(int i = 0;i < param.length;i ++) {	
					pstat.setString(i+1, param[i].toString());
				}
				System.out.println(pstat);
				rs = pstat.executeQuery();
 			}else {
 				stmt = conn.createStatement();
 				rs = stmt.executeQuery(sql); 
 			}
		} finally {
			
			//this.closeAll();
		} 		
		return rs;
	}
}
