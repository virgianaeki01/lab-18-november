package com.ibik.library.app.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	private Connection conn;
	
	public Connection connect() throws SQLException {
		String host = "localhost:3306";
		String dbName = "202310061_eki";
		String dbuser = "root";
		String dbpassword = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+dbName,
				dbuser, dbpassword);
		
		return conn;
	}
	
	public Connection close () throws SQLException {
		conn.close();
		return conn;
	}
}