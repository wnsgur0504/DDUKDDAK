package com.dd.member.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.dd.member.main.AppMain;
import com.dd.member.main.Page;

public class Home extends Page{
	JPanel p_navi;
	JPanel p_search;
	JPanel p_north;
	JPanel p_popular;
	JPanel p_all;
	JScrollPane scroll;
	JButton bt_search;
	JTextField t_search;
	JComboBox<String> cb;
	JButton bt_logout;
	JLabel la_logo;
	JButton bt_myPage;
	
	public Home(AppMain appMain) {
		super(appMain);
		p_navi = new JPanel();
		bt_logout = new JButton("로그아웃");
		la_logo = new JLabel("뚝딱");
		bt_myPage = new JButton("마이페이지");

		p_search = new JPanel();
		cb = new JComboBox<String>();
		cb.addItem("요리이름");
		cb.addItem("작성자");
		cb.addItem("재료");
		t_search = new JTextField(30);
		bt_search = new JButton("검색");
		p_north = new JPanel(); //navi와 search를 묶을 패널
		
		p_popular = new JPanel();
		p_all = new JPanel();
		scroll = new JScrollPane(p_all);
		
		p_north.setLayout(new GridLayout(2, 1));
//		p_navi.setLayout(new GridLayout(1,3));
		la_logo.setFont(new Font("궁서", Font.BOLD, 50));
		la_logo.setPreferredSize(new Dimension(300,100));
		la_logo.setHorizontalAlignment(JLabel.CENTER);
//		bt_logout.setPreferredSize(new Dimension(50, 50));
//		p_popular.setPreferredSize(new Dimension(AppMain.WIDTH-50, 200));
//		p_popular.setPreferredSize(new Dimension(AppMain.WIDTH-50, 200));
//		p_popular.setBackground(Color.RED);
		p_all.setBackground(Color.red);
		p_all.setLayout(new GridLayout(10, 1));

		for(int i=0;i<10;i++) {
			p_all.add(new RecipePanel());
		}
//		p_all.setPreferredSize(new Dimension(AppMain.WIDTH-50, AppMain.HEIGHT-100));
		scroll.setPreferredSize(new Dimension(AppMain.WIDTH-50, AppMain.HEIGHT-100));
		
		p_navi.add(bt_logout);
		p_navi.add(la_logo);
		p_navi.add(bt_myPage);		
		p_search.add(cb);
		p_search.add(t_search);
		p_search.add(bt_search);
		p_north.add(p_navi);
		p_north.add(p_search);
		
		add(p_north);
		add(scroll);
		
		setVisible(false);
	}
	
}
