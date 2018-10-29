package pre.jdbc.study;

import java.sql.*;

public class TestJDBC {
	public static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String URL="jdbc:sqlserver://localhost:3443;DatabaseName=Data";
	public static String Name="sa";
	public static String Password="123";
	private static Connection con=null;
	
	public static Connection getConnection(){
		try {
			//加载驱动程序
			Class.forName(driver);
			//建立连接
			con=DriverManager.getConnection(URL,Name,Password);
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
