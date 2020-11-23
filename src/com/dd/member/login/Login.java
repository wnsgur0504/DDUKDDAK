package com.dd.member.login;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dd.member.main.AppMain;
import com.dd.member.main.Page;

public class Login extends Page{
	JPanel p_input, p_button, p_id, p_pw;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login, bt_sign, bt_find;
	JLabel la_logo, la_id, la_pw;
	
	public Login(AppMain appMain) {
		super(appMain);
		t_id = new JTextField(15);
		t_pw = new JPasswordField(15);
		bt_login = new JButton("로그인");
		bt_find = new JButton("아이디/비밀번호찾기");
		bt_sign = new JButton("회원가입");
		p_input = new JPanel();
		p_button = new JPanel();
		p_id = new JPanel();
		p_pw = new JPanel();
		la_id = new JLabel("ID");
		la_pw = new JLabel("PW");
		la_logo = new JLabel("뚝딱");
		
		la_logo.setFont(new Font("궁서", Font.BOLD, 50));
		la_logo.setPreferredSize(new Dimension(600,300));
		la_id.setFont(new Font("궁서", Font.BOLD, 30));
		la_pw.setFont(new Font("궁서", Font.BOLD, 30));
		la_id.setPreferredSize(new Dimension(100, 30));
		la_pw.setPreferredSize(new Dimension(100, 30));
		la_logo.setHorizontalAlignment(JLabel.CENTER);
		t_id.setPreferredSize(new Dimension(500, 30));
		t_pw.setPreferredSize(new Dimension(500, 30));
		t_id.setFont(new Font("궁서", Font.BOLD, 20));
		t_pw.setFont(new Font("궁서", Font.BOLD, 20));
		p_id.setPreferredSize(new Dimension(500, 50));
		p_pw.setPreferredSize(new Dimension(500, 50));
		p_input.setPreferredSize(new Dimension(600, 300));
		
		
		p_id.add(la_id);
		p_id.add(t_id);
		p_pw.add(la_pw);
		p_pw.add(t_pw);
		p_input.add(p_id);
		p_input.add(p_pw);
		p_button.add(bt_login);
		p_button.add(bt_find);
		p_button.add(bt_sign);
		p_input.add(p_button);
		
		add(la_logo);
		add(p_input);
		setVisible(true);
		bt_login.addActionListener((e)->{
			if(login(t_id.getText(), t_pw.getText())==1) {
				appMain.showPage(AppMain.HOME);
			}else {
				JOptionPane.showMessageDialog(Login.this, "정보가 일치하지 않습니다!");
			}
		});
		
		bt_sign.addActionListener((e)->{
			appMain.showPage(AppMain.SIGN);
		});
	}
	
	public int login(String id, String pw) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select count(*) as cnt from member where member_id=? and password=?";
		int result=0;
		
		try {
			pstmt = getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	finally {
			getAppMain().getDBManager().close(pstmt, rs);
		}
		return result;
	}
}
