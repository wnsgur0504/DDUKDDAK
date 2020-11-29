package com.dd.admin.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dd.admin.main.Adminmain;
import com.dd.admin.main.Page;
import com.dd.dto.Member;

public class AdminLogin extends Page{
	JPanel content;
	JPanel p_north;
	JPanel p_center;
	JPanel p_left;
	JPanel p_right;
	JPanel p_id, p_pw;
	JButton bt_login;
	JLabel la_logo, la_logo2;
	JLabel la_id, la_pw;
	JTextField t_id;
	JPasswordField  t_pw;
	Member admin;
	
	public AdminLogin(Adminmain adminmain) {
		super(adminmain);
		content = new JPanel(new GridLayout(2, 1));
		p_north = new JPanel();
		p_center = new JPanel(new GridLayout(1, 2));
		p_left = new JPanel();
		p_id = new JPanel();
		p_pw = new JPanel();
		bt_login = new JButton("로그인");
		la_logo = new JLabel("뚝딱");
		la_logo2 = new JLabel("(관리자)");
		la_id = new JLabel("아이디");
		la_pw = new JLabel("비밀번호");
		t_id = new JTextField(10);
		t_pw = new JPasswordField(10);
		
		la_logo.setFont(new Font("궁서", Font.BOLD, 100));
		la_logo2.setFont(new Font("궁서", Font.BOLD, 20));
		content.setPreferredSize(new Dimension(600, 400));
		p_center.setLayout(new FlowLayout());
		
		p_id.add(la_id);
		p_id.add(t_id);
		p_pw.add(la_pw);
		p_pw.add(t_pw);
		p_left.add(p_id);
		p_left.add(p_pw);
		p_north.add(la_logo);
		p_north.add(la_logo2);
		p_north.add(la_logo);
		p_center.add(p_left);
		p_center.add(bt_login);
		content.add(p_north);
		content.add(p_center);
		add(content);
		
		
		bt_login.addActionListener((e)->{
			login();
		});
	}
	
	public void login() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member result=null;
		
		String sql = "select * from member where member_id=? and password=? and member_type_id=1";
		try {
			pstmt = getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, t_pw.getText());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("1");
				result = new Member();
				result.setMember_idx(rs.getInt("member_idx"));
				result.setMember_id(rs.getString("member_id"));
				result.setPassword(rs.getString("password"));
				result.setName(rs.getString("name"));
				result.setNickname(rs.getString("nickname"));
				result.setRegdate(rs.getString("regdate"));
				result.setEmail(rs.getString("email"));
				System.out.println(rs.getString("regdate"));
			}
			admin = result;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAdminmain().getDbManager().close(pstmt, rs);
		}
		if(admin==null) {
			JOptionPane.showMessageDialog(this, "로그인 실패");
		}else {
			JOptionPane.showMessageDialog(this, "로그인 성공");
			getAdminmain().showPage(Adminmain.MEMBER);
		}
	}
	
}
