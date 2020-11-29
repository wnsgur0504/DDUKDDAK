package com.dd.member.search;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dd.member.main.AppMain;

public class SearchAccount extends JDialog{
	JLabel la_id, la_pw;
	JTextField t_email, t_name;
	JTextField t_id;
	JButton bt_id, bt_pw;
	JPanel p_pw;
	AppMain appMain;
	
	public SearchAccount(JFrame frame, String title) {
		super(frame, title);
		appMain = (AppMain)frame;
		la_id = new JLabel("아이디찾기");
		t_email = new JTextField("이메일을 입력하세요.", 15);
		t_name = new JTextField("이름을 입력하세요.", 15);
		bt_id = new JButton("아이디찾기");
		la_pw = new JLabel("비밀번호찾기");
		t_id = new JTextField("아이디를 입력하세요", 15);
		bt_pw = new JButton("비밀번호찾기");
		p_pw = new JPanel();
		
		la_id.setPreferredSize(new Dimension(350, 50));
		la_id.setHorizontalAlignment(JLabel.LEFT);
		la_id.setFont(new Font("궁서", Font.BOLD, 30));
		t_email.setPreferredSize(new Dimension(350, 30));
		t_name.setPreferredSize(new Dimension(350, 30));
		t_id.setPreferredSize(new Dimension(350, 30));
		la_pw.setPreferredSize(new Dimension(350, 50));
		la_pw.setHorizontalAlignment(JLabel.LEFT);
		la_pw.setFont(new Font("궁서", Font.BOLD, 30));
		
		add(la_id);
		add(t_name);
		add(t_email);
		add(bt_id);
		add(la_pw);
		p_pw.add(t_id);
		p_pw.add(bt_pw);
		add(p_pw);
		
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(500, 250);
		
		bt_id.addActionListener((e)->{
			searchId();
		});
		
		bt_pw.addActionListener((e)->{
			searchPw();
		});
	}
	
	public void searchId() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder getId= new StringBuilder();
		String sql = "select member_id from member where name=? and email=?";
		try {
			pstmt = appMain.getDBManager().connect().prepareStatement(sql);
			pstmt.setString(1, t_name.getText());
			pstmt.setString(2, t_email.getText());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				getId.append(rs.getString("member_id"));
				int length = getId.length();
				for(int i=length/2;i<length;i++) {
					getId.setCharAt(i, '*');
				}
				JOptionPane.showMessageDialog(this, "찾으시는 아이디는 "+ getId+" 입니다.");
			}else {
				JOptionPane.showMessageDialog(this, "찾으시는 아이디가 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.getDBManager().close(pstmt, rs);
		}
	}
	
	public void searchPw() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder getPw= new StringBuilder();
		String sql = "select password from member where member_id=?";
		try {
			pstmt = appMain.getDBManager().connect().prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				getPw.append(rs.getString("password"));
				int length = getPw.length();
				for(int i=length/2;i<length;i++) {
					getPw.setCharAt(i, '*');
				}
				JOptionPane.showMessageDialog(this, "찾으시는 아이디의 비밀번호는 "+ getPw+" 입니다.");
			}else {
				JOptionPane.showMessageDialog(this, "찾으시는 아이디가 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.getDBManager().close(pstmt, rs);
		}
	}

}
