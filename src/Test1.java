import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test1 {
	public static void main(String args[]) {
//		String userName = "hy_dev";
//		String password = "password";
//		String url = "jdbc:oracle:thin:@10.24.16.72:1521:orcl";
		
		String con_username = "itms";
		String con_password = "password";
		String con_url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		
		String db_tablespace = "testts";
		String db_user = "testuser";
		
		String dropTableSpace = "drop TABLESPACE "+db_tablespace;
		String dropUser = "drop USER "+db_user;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e);
		}

		try {
			Connection con = DriverManager.getConnection(con_url, con_username, con_password);
			Statement stmt = con.createStatement();
			stmt.executeUpdate(dropUser);
			stmt.executeUpdate(dropTableSpace);

			stmt.close();
			con.close();
			
			System.out.println("operation complete");
		} catch (SQLException e) {
			System.err.println("SQLException: " + e);
		}
	}
}
