package com.noisyle.tools.orahelper.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	public static boolean testConnection(String url, String username, String password) throws Exception {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
			throw new Exception("驱动加载失败，原因：" + e.getMessage(), e);
		}
		boolean result = false;
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			if(con!=null) result=true;
			con.close();
			con=null;
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
			throw new Exception("连接数据库失败，原因：" + e.getMessage(), e);
		}
		return result;
	}
	
	public static void createTablespace(String ts_name) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
		}
		
		try {
			Connection con = DriverManager.getConnection(Config.con_url, Config.con_username, Config.con_password);
			String createTableSpace = "CREATE TABLESPACE "+ts_name+" LOGGING DATAFILE '"+ts_name+".DBF' SIZE 100M AUTOEXTEND ON NEXT 200M MAXSIZE 20480M EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO";

			Statement stmt = con.createStatement();
			stmt.executeUpdate(createTableSpace);

			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
		}
	}
}
