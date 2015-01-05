import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String args[]) {
//		String userName = "hy_dev";
//		String password = "password";
//		String url = "jdbc:oracle:thin:@10.24.16.72:1521:orcl";
		
		String con_username = "itms";
		String con_password = "password";
		String con_url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		
		String db_tablespace = "testts";
		String db_user = "testuser";
		String db_pw = "password";
		
		String createTableSpace = "CREATE TABLESPACE "+db_tablespace+" LOGGING DATAFILE '"+db_tablespace+".DBF' SIZE 100M AUTOEXTEND ON NEXT 200M MAXSIZE 20480M EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO";
		String createUser = "CREATE USER "+db_user+" IDENTIFIED BY "+db_pw+" DEFAULT TABLESPACE "+db_tablespace+" TEMPORARY TABLESPACE TEMP";
		String grant1 = "GRANT CONNECT TO "+db_user;
		String grant2 = "GRANT RESOURCE TO "+db_user;
		
		String createTable = "create table aaatest ( id number, name varchar2(100) )";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
		}

		try {
			Connection con = DriverManager.getConnection(con_url, con_username, con_password);
			Statement stmt = con.createStatement();
			System.out.println("create TableSpace");
			stmt.executeUpdate(createTableSpace);
			System.out.println("create User");
			stmt.executeUpdate(createUser);
			System.out.println("grant");
			stmt.executeUpdate(grant1);
			stmt.executeUpdate(grant2);

			stmt.close();
			con.close();
			
			con = DriverManager.getConnection(con_url, con_username, con_password);
			stmt = con.createStatement();
			stmt.executeUpdate(createTable);
			stmt.close();
			con.close();
			
			System.out.println("operation complete");
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
		}
	}
}
