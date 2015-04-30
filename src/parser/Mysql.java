package parser;

import java.sql.Connection;
import java.sql.DriverManager;



public class Mysql {
	private static Connection con;

	public Mysql(){
		con=null;
	}

	public static void establishConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IR_INDEX", "xuke", "qpmz12450"); //Connect SQL
			System.out.println("Connection Established!");
		} catch (Exception e) {
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}
	}
	public static Connection getConnection(){
		return con;
	}
}

