package com.ibik.library.app.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ibik.library.app.connection.ConnectionDB;
import com.ibik.library.app.model.User;
public class LoginDao {
	
	private String sqlLogin = "SELECT * FROM User where NIK = ? AND PASSWORD = ?";
	
	public User checkLogin(User users) throws Exception {
		if (users == null) {
			return null;
		}
		
		ConnectionDB conn = new ConnectionDB();
		Connection c = conn.connect(); //open connection
		PreparedStatement pst = c.prepareStatement(sqlLogin);
		pst.setString(1, users.getNIK()); //entering parameter NIK to index 1
		pst.setString(2, users.getPassword()); //entering parameter Password to index 2
		ResultSet rs = pst.executeQuery(); //execute query base on var sqllogin
		if(!rs.next()) { //check if there is no result from database
			return null;
		}
		//if it has result from query it will put the result to the class encapsulation class Users
		User l = new User();
		l.setId(rs.getInt("ID"));
		l.setNIK(rs.getString("NIK"));
		l.setFullname(rs.getString("Fullname"));
		l.setPlaceBirth(rs.getString("PlaceBirth"));
		l.setBirthdate(rs.getString("Birthdate"));
		l.setGender(rs.getString("Gender"));
		l.setAddress(rs.getString("Address"));
		l.setEmail(rs.getString("Email"));
		l.setPassword(rs.getString("Password"));
		
		c.close(); //closing connection to db
		return l; //return class Login value
	}
}