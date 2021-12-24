package com.ibik.library.app.login;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import com.ibik.library.app.connection.ConnectionDB;
import com.ibik.library.app.model.User;
import com.ibik.library.app.dashboard.Dashboard;



public class LoginController {

	private JFrame frameMain;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private static User user;
	
	public String getTextUsername() {
		return textUsername.getText();
	}

	public void setTextUsername(JTextField textUsername) {
		this.textUsername = textUsername;
	}

	public String getTextPassword() {
		return String.valueOf(textPassword.getPassword());
	}

	public void setTextPassword(JPasswordField textPassword) {
		this.textPassword = textPassword;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginController window = new LoginController();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginController() {
		//call the ConnectionDB class using error handling : try_catch
		ConnectionDB conn = new ConnectionDB(); //init object
		
		try {
			System.out.println("test");
			conn.connect();
			initialize();
		} catch (SQLException e) { 		//error handling for if connection is connected will show window dialog, the connection is failed
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		//end call
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameMain = new JFrame();
		frameMain.getContentPane().setForeground(SystemColor.textHighlight);
		frameMain.setTitle("Login");
		frameMain.setBounds(100, 100, 292, 494);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);
		
		JLabel labelTitle = new JLabel("Sistem Informasi Perpustakaan");
		labelTitle.setForeground(Color.BLACK);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelTitle.setBounds(10, 211, 256, 31);
		frameMain.getContentPane().add(labelTitle);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Login to your account", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 243, 256, 159);
		frameMain.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel labelUsername = new JLabel("Username");
		labelUsername.setBounds(10, 35, 70, 14);
		panel.add(labelUsername);
		
		textUsername = new JTextField();
		textUsername.setBounds(90, 32, 138, 20);
		panel.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel labelPassword = new JLabel("Password");
		labelPassword.setBounds(10, 74, 70, 14);
		panel.add(labelPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(90, 71, 138, 20);
		panel.add(textPassword);
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String username = textUsername.getText();
				String password = String.valueOf(textPassword.getPassword());
				
				MyResults result = new LoginController().ValidationForm(username, password);
				if (result.getResponse()) {
					frameMain.dispose();
					new Dashboard(user).setVisible(true);
				} else if (!result.getResponse()) {
					JOptionPane.showMessageDialog(null, result.getLabel(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setBounds(139, 114, 89, 23);
		panel.add(btnLogin);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frameMain.dispatchEvent();
				frameMain.dispose();
			}
		}
			);
		btnCancel.setBounds(36, 114, 89, 23);
		panel.add(btnCancel);
		
		JLabel labelCopyright = new JLabel("(c) 2021 Eki Virgiana");
		labelCopyright.setBounds(10, 430, 149, 14);
		frameMain.getContentPane().add(labelCopyright);		
		
		JLabel labelIcon = new JLabel("");
		labelIcon.setBackground(new Color(240, 240, 240));
		labelIcon.setIcon(new ImageIcon("C:\\PBO\\123.png"));
		labelIcon.setBounds(24, 11, 224, 216);
		frameMain.getContentPane().add(labelIcon);
	}
	
public class MyResults extends LoginController {
	private boolean Response;
	private String Label;
	public boolean getResponse() {
		return Response;
	}
	public void setResponse(boolean response) {
		Response = response;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
		
		
	}
}

public MyResults ValidationForm(String username, String password) {
	MyResults m = new MyResults();
	System.out.println("Username : "+username+"& Password : "+password);
	//condition for if username or password is empty
	if(username.isEmpty() || password.isEmpty()) {
		m.setResponse(false);
		m.setLabel("Fill both the username and password fields");
	}
	
	//condition for if username has length more than 10
	else if(username.length()>10) {
		m.setResponse(false);
		m.setLabel("Keyword must not exceed 10 characters");
	}
	
	//condition if password has length more than 6
	else if(password.length()>6) {
		m.setResponse(false);
		m.setLabel("Keyword must not exceed 6 characters");
	}
	//condition for if username or password has alphabet value => only number
	else if(username.matches("[a-z]*") || password.matches("[a-z]*")) {
		m.setResponse(false);
		m.setLabel("This entry can only contain numbers");
	}
	
	//condition if username and password is empty
	else {
	//sending username and password to DAO
		String NIK = username;
		user = new User();
		user.setNIK(NIK);
		user.setPassword(password);
		//end saving to class Users
		//sending class Users to DAO
		LoginDao loginDao = new LoginDao();
		try {
			User l = loginDao.checkLogin(user);
			System.out.println("NIK"+l.getNIK());
			System.out.println("Fullname"+l.getFullname());
			m.setResponse(true);
			m.setLabel("Welcome to this application");
		} catch (Exception e) {
			e.printStackTrace();
			m.setResponse(false);
			m.setLabel("You have entered an invalid username or password.");
		}
	}
	
	return m;
}
	
public boolean isNumber(String input) {
	
	try {
		Double x = Double.parseDouble(input);
		return true;
	} catch (Exception e) {
		return false;
	}
	
}	
}