package com.noisyle.tools.orahelper.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.noisyle.tools.orahelper.entity.ConnInfo;

public class JDBCUtil {
	
	public static boolean testConnection(ConnInfo info) throws Exception {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
			throw new Exception("驱动加载失败，原因：" + e.getMessage(), e);
		}
		boolean result = false;
		try {
			Connection con = DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassword());
			if(con!=null) result=true;
			con.close();
			con=null;
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
			throw new Exception("连接数据库失败，原因：" + e.getMessage(), e);
		}
		return result;
	}
	
	public static void createTablespace(String conn_alias, String ts_name) throws Exception {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
			throw new Exception("驱动加载失败，原因：" + e.getMessage(), e);
		}
		
		try {
			ConnInfo info = DBConfig.getConnInfoByAlias(conn_alias);
			Connection con = DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassword());
			String createTableSpace = "CREATE TABLESPACE "+ts_name+" LOGGING DATAFILE '"+ts_name+".DBF' SIZE 100M AUTOEXTEND ON NEXT 200M MAXSIZE 20480M EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO";
			System.out.println("创建表空间:"+createTableSpace);
			Statement stmt = con.createStatement();
			stmt.execute(createTableSpace);
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
			throw new Exception("创建表空间失败，原因：" + e.getMessage(), e);
		}
	}
	
	public static void createUser(String conn_alias, String username, String password, String tablespace) throws Exception {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
			throw new Exception("驱动加载失败，原因：" + e.getMessage(), e);
		}
		
		try {
			ConnInfo info = DBConfig.getConnInfoByAlias(conn_alias);
			Connection con = DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassword());
			String createUser = "CREATE USER "+username+" IDENTIFIED BY "+password+" DEFAULT TABLESPACE "+tablespace+" TEMPORARY TABLESPACE TEMP";
			
			String roles = "CONNECT,RESOURCE,exp_full_database,imp_full_database";
			String grant = "GRANT "+roles+" TO "+username;
			
			Statement stmt = con.createStatement();
			stmt.execute(createUser);
			stmt.execute(grant);
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
			throw new Exception("创建用户失败，原因：" + e.getMessage(), e);
		}
	}
	
	public static String exportDump(String conn_alias, String filePath) throws Exception {
		try {
			ConnInfo info = DBConfig.getConnInfoByAlias(conn_alias);
			String cmdStr = "exp "+info.getUsername()+"/"+info.getPassword()+"@//"+info.getHost()+":"+info.getPort()+"/"+ info.getSsid()+" file=\""+filePath+"\" ";
			System.out.println(cmdStr);
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmdStr);
			String line = null;
			StringBuffer buffer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
			// 读取ErrorStream很关键，这个解决了挂起的问题。
			while ((line = br.readLine()) != null) {
				System.err.println(line);
				buffer.append(line).append("<br />");
			}
			br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				buffer.append(line).append("<br />");
			}
			process.waitFor();
//			if (process.waitFor() != 0) {
//				throw new Exception("导出失败");
//			}
			return buffer.toString();

		} catch (Exception e) {
			System.err.println("SQLException: " + e);
			throw new Exception("导出失败，原因：" + e.getMessage(), e);
		}
	}
	
	public static String importDump(String conn_alias, String filePath, String username) throws Exception {
		try {
			ConnInfo info = DBConfig.getConnInfoByAlias(conn_alias);
			String cmdStr = "imp "+info.getUsername()+"/"+info.getPassword()+"@//"+info.getHost()+":"+info.getPort()+"/"+ info.getSsid()+" file=\""+filePath+"\" "
					+" fromuser="+username+" touser="+info.getUsername();
			System.out.println(cmdStr);
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmdStr);
			String line = null;
			StringBuffer buffer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
			// 读取ErrorStream很关键，这个解决了挂起的问题。
			while ((line = br.readLine()) != null) {
				System.err.println(line);
				buffer.append(line).append("<br />");
			}
			br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				buffer.append(line).append("<br />");
			}
			process.waitFor();
			return buffer.toString();
		} catch (Exception e) {
			System.err.println("SQLException: " + e);
			throw new Exception("导入失败，原因：" + e.getMessage(), e);
		}
	}
}
