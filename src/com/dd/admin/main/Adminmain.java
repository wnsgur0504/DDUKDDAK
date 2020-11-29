package com.dd.admin.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dd.admin.home.AdminHome;
import com.dd.admin.login.AdminLogin;
import com.dd.admin.member.AdminMember;
import com.dd.admin.recipe.AdminRecipe;
import com.dd.db.DBManager;

public class Adminmain extends JFrame{
	public static final int WIDTH = 1200;
	public static final int HEIGHT= 900;
	public static final int LOGIN= 0;
	public static final int MEMBER= 1;
	public static final int RECIPE= 2;
	JPanel p_navi;
	JPanel content; //페이지들이 나올 영역
	JPanel footer;
	JLabel la_foot;
	Page[] page = new Page[3];
	JButton bt_member, bt_recipe, bt_stat;
	DBManager dbManager;
	Connection con;
	
	public Adminmain() {
		super("뚝딱 | 관리자");
		content = new JPanel();
		footer = new JPanel();
		la_foot = new JLabel("DDukDDak All rights reserved");
		dbManager = new DBManager();
		if((con = dbManager.connect())==null) {
			JOptionPane.showMessageDialog(this, "데이터베이스와 연결할 수 없습니다!");
			System.exit(0);
		}
		p_navi = new JPanel();
		bt_member = new JButton("회원관리");
		bt_recipe = new JButton("레시피관리");
		bt_stat  = new JButton("통계");
		page[0] = new AdminLogin(this);
		page[1] = new AdminMember(this);
		page[2] = new AdminRecipe(this);
		showPage(LOGIN);
		
		content.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		p_navi.add(bt_member);
		p_navi.add(bt_recipe);
		p_navi.add(bt_stat);
		footer.add(la_foot);
		for(int i=0;i<page.length;i++) {
			content.add(page[i]);
		}
		add(p_navi, BorderLayout.NORTH);
		add(content);
		add(footer, BorderLayout.SOUTH);
		
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		
		pack();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.disConnect(con);
				System.exit(0);
			}
		});
		
		bt_member.addActionListener((e)->{
			showPage(MEMBER);
		});
		
		bt_recipe.addActionListener((e)->{
			showPage(RECIPE);
		});
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public DBManager getDbManager() {
		return dbManager;
	}
	
	public void showPage(int index) {
		for(int i=0;i<page.length;i++) {
			if(i==index) {
				page[i].setVisible(true);
			}else {
				page[i].setVisible(false);
			}
			
			if(index==LOGIN) {
				p_navi.setVisible(false);
			}else {
				p_navi.setVisible(true);
			}
		}
	}
	public static void main(String[] args) {
		new Adminmain();
	}
}
