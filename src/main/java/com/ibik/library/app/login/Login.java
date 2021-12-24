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

import com.ibik.library.app.connection.ConnectionDB;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frameMain;
	private JTextField textUsername;
	private JPasswordField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		
		ConnectionDB conn =  new ConnectionDB();
		try {
			conn.connect();
			initialize();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
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
		
		JLabel labelIcon = new JLabel("");
		labelIcon.setIcon(new ImageIcon("D:\\Pemograman-Object-Oriented-PA\\A1\\app-library\\src\\main\\java\\com\\ibik\\library\\images\\logo-ibik-web.png"));
		labelIcon.setBounds(80, 11, 109, 150);
		frameMain.getContentPane().add(labelIcon);
		
		JLabel labelTitle = new JLabel("Sistem Informasi Perpustakaan");
		labelTitle.setForeground(Color.BLACK);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelTitle.setBounds(10, 163, 256, 31);
		frameMain.getContentPane().add(labelTitle);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Login to your account", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 227, 256, 159);
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
				
				new Login().validasi(username, password);
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
		
		JLabel labelCopyright = new JLabel("(c) 2021");
		labelCopyright.setBounds(10, 430, 46, 14);
		frameMain.getContentPane().add(labelCopyright);		
	}
	
	
public void validasi(String username, String password) {
	Login dataLogin = new Login();
	System.out.println("Username : "+username+"& Password : "+password);
	if(username.isEmpty() || password.isEmpty()) {
		System.out.println("Username & Password harus diisi");
		JOptionPane.showMessageDialog(null, "Username & Password harus diisi","Error",JOptionPane.ERROR_MESSAGE);
	}else if(username.length()>10) {
		System.out.println("Maximum 10 character untuk username");
		JOptionPane.showMessageDialog(null,"Maximum 10 character untuk username","Konfirmasi",JOptionPane.ERROR_MESSAGE);
	}else if(password.length()>6) {
		System.out.println("Maximum 6 character untuk Password");
		JOptionPane.showMessageDialog(null, "Maximum 6 character untuk Password", "Pilihan",JOptionPane.ERROR_MESSAGE);
	}else if(dataLogin.isNumber(username) == false) {
		System.out.println("Harap masukan angka.");
		JOptionPane.showMessageDialog(null, "Harus pake angka loch", "Salah tau", JOptionPane.ERROR_MESSAGE);
	}else if(username.equals("202310015") && password.equals("080302")) {
		System.out.println("Selamat Datang di Aplikasi");
		JOptionPane.showMessageDialog(null, "Selamat Datang di Aplikasi");
	}else {
		System.out.println("Data yang anda masukkan salah");
		JOptionPane.showMessageDialog(null, "Data yang anda masukkan salah");
	}
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