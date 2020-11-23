package com.dd.member.main;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dd.db.DBManager;
import com.dd.member.home.Home;
import com.dd.member.login.Login;
import com.dd.member.signup.SignUp;

public class AppMain extends JFrame{
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	public static final int LOGIN=0;
	public static final int HOME=1;
	public static final int SIGN=2;
	
	String pageName[] = {"Login", "HOME", "SignUp"};
	JButton bt_name[] = new JButton[pageName.length];
	Page page[] = new Page[pageName.length];
	JPanel p_content; //페이지가 붙일 패널
	JPanel user_container;//큰 틀
	Connection con;
	DBManager dm;
	
	public AppMain() {
		user_container = new JPanel();
		p_content = new JPanel();
		dm = new DBManager();
		
		//페이지 구성
		page[0] = new Login(this);
		page[1] = new Home(this);
		page[2] = new SignUp(this);

		user_container.setPreferredSize(new Dimension(WIDTH, HEIGHT-10));

		for(int i=0;i<page.length;i++) {
			p_content.add(page[i]);
//			page[i].setVisible(false);
		}
		
		user_container.add(p_content);
		add(user_container);
		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		con = dm.connect();
		if(con==null) {
			JOptionPane.showMessageDialog(this, "데이터베이스에 연결 실패!");
			System.exit(0);
		}else {
			this.setTitle("뚝딱에 오신걸 환영합니다!");
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dm.disConnect(con);
				System.exit(0);
			}
		});
	}
	
	public void showPage(int index) {
		for(int i=0;i<page.length;i++) {
			if(i==index) {
				page[i].setVisible(true);
			}else {
				page[i].setVisible(false);
			}
		}
	}
	
	public Connection getConnetion() {
		return con;
	}
	public DBManager getDBManager() {
		return dm;
	}
	public static void main(String[] args) {
		new AppMain();
	}

}
