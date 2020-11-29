package com.dd.member.mypage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dd.dto.Member;
import com.dd.member.main.AppMain;
import com.dd.member.main.Page;

public class MyPage extends Page{
	Member member;
	JLabel la_logo;
	JTextField t_id;
	JPasswordField t_pw, t_pw2;
	JTextField t_name;
	JTextField t_nickname;
	JTextField t_regdate;
	JTextField t_email;
	JButton bt_back;
	
	public MyPage(AppMain appMain) {
		super(appMain);
//		this.member = appMain.getMember();
		la_logo = new JLabel("마이페이지");
		t_id = new JTextField(15);
		t_pw = new JPasswordField(15);
		t_name = new JTextField(15);
		t_nickname = new JTextField(15);
		t_regdate = new JTextField(15);
		t_email = new JTextField(15);
		bt_back = new JButton("목록");
		
		add(t_id);
		add(t_pw);
		add(t_name);
		add(t_nickname);
		add(t_regdate);
		add(t_email);
		add(bt_back);
		setVisible(false);
		
		bt_back.addActionListener((e)->{
			appMain.showPage(AppMain.HOME);
		});
	}
	
	public void getMember() {
		member = getAppMain().getMember();
		t_id.setText(member.getMember_id());
		t_pw.setText(member.getPassword());
		t_name.setText(member.getName());
		t_nickname.setText(member.getNickname());
		t_regdate.setText(member.getRegdate());
		t_email.setText(member.getEmail());
	}
}
