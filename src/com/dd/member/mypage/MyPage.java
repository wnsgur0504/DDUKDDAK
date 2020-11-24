package com.dd.member.mypage;

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
	
	public MyPage(AppMain appMain) {
		super(appMain);
//		this.member = appMain.getMember();
		la_logo = new JLabel("마이페이지");
		t_id = new JTextField(15);
		t_pw = new JPasswordField(15);
		t_name = new JTextField(15);
		t_nickname = new JTextField(15);
		t_regdate = new JTextField(15);
		
		add(t_id);
		add(t_pw);
		add(t_name);
		add(t_nickname);
		add(t_regdate);
		setVisible(false);
	}
	
	public void getMember() {
		member = getAppMain().getMember();
		t_id.setText(member.getMember_id());
		t_pw.setText(member.getPassword());
		t_name.setText(member.getName());
		t_nickname.setText(member.getNickname());
		t_regdate.setText(member.getRegdate());
	}
}
