package com.dd.admin.member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dd.dto.Member;

public class AdminMemberPanel extends JPanel{
	JPanel p_form;
	JPanel p_button;
	JLabel la_idx, la_id, la_password, la_type, la_name, la_nickname, la_regdate, la_email;
	JTextField t_idx, t_id, t_type, t_name,t_nickname, t_regdate, t_email;
	JPasswordField t_password;
	JButton bt_update, bt_delete;
	Member member;
	AdminMember adminMember;
	
	public AdminMemberPanel(AdminMember adminMember) {
		this.adminMember=adminMember;
		p_form = new JPanel();
		p_button = new JPanel();
		la_idx = new JLabel("회원번호");
		t_idx = new JTextField(15);
		la_id = new JLabel("아이디");
		t_id = new JTextField(15);
		la_password = new JLabel("비밀번호");
		t_password = new JPasswordField(15);
		la_type = new JLabel("회원타입");
		t_type = new JTextField(15);
		la_name = new JLabel("이름");
		t_name = new JTextField(15);
		la_nickname = new JLabel("닉네임");
		t_nickname = new JTextField(15);
		la_regdate = new JLabel("등록날짜");
		t_regdate = new JTextField(15);
		la_email = new JLabel("이메일");
		t_email = new JTextField(15);
		bt_update= new JButton("수정");
		bt_delete = new JButton("삭제");
		
		t_idx.setHorizontalAlignment(JTextField.CENTER);
		t_idx.setEnabled(false);
		t_id.setHorizontalAlignment(JTextField.CENTER);
		t_id.setEnabled(false);
		t_password.setHorizontalAlignment(JTextField.CENTER);
		t_name.setHorizontalAlignment(JTextField.CENTER);
		t_name.setEnabled(false);
		t_nickname.setHorizontalAlignment(JTextField.CENTER);
		t_nickname.setEnabled(false);
		t_type.setHorizontalAlignment(JTextField.CENTER);
		t_type.setEnabled(false);
		t_regdate.setHorizontalAlignment(JTextField.CENTER);
		t_regdate.setEnabled(false);
		t_email.setHorizontalAlignment(JTextField.CENTER);
		p_form.setPreferredSize(new Dimension(200, 500));
		setLayout(new BorderLayout());
		
		p_form.add(la_idx);
		p_form.add(t_idx);
		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(la_password);
		p_form.add(t_password);
		p_form.add(la_type);
		p_form.add(t_type);
		p_form.add(la_name);
		p_form.add(t_name);
		p_form.add(la_nickname);
		p_form.add(t_nickname);
		p_form.add(la_regdate);
		p_form.add(t_regdate);
		p_form.add(la_email);
		p_form.add(t_email);
		p_button.add(bt_update);
		p_button.add(bt_delete);
		add(p_form);
		add(p_button, BorderLayout.SOUTH);
		
		bt_update.addActionListener((e)->{
			if(updateMember()<=0) {
				JOptionPane.showMessageDialog(this, "회원 수정 실패!");
			}else {
				JOptionPane.showMessageDialog(this, "회원 수정 성공");
				adminMember.getMemberList();
			}
		});
		
		bt_delete.addActionListener((e)->{
			if(deleteMember()<=0) {
				JOptionPane.showMessageDialog(this, "회원 수정 실패!");
			}else {
				JOptionPane.showMessageDialog(this, "회원 수정 성공");
				adminMember.getMemberList();
			}
		});
	}
	
	public void setMember(Member member) {
		this.member = member; 
	}
	
	public int updateMember() {
		PreparedStatement pstmt = null;
		String sql ="update member set password=?, email=? where member_idx=?";
		int result = 0;
		try {
			pstmt =adminMember.getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setString(1, t_password.getText());
			pstmt.setString(2, t_email.getText());
			pstmt.setInt(3, Integer.parseInt(t_idx.getText()));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			adminMember.getAdminmain().getDbManager().close(pstmt);
		}
		return result;
	}
	
	public int deleteMember() {
		PreparedStatement pstmt = null;
		String sql ="delete from member where member_idx=?";
		int result = 0;
		try {
			pstmt =adminMember.getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(t_idx.getText()));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			adminMember.getAdminmain().getDbManager().close(pstmt);
		}
		return result;
	}
	
	public void chageMember() {
		t_idx.setText(Integer.toString(member.getMember_idx()));
		t_id.setText(member.getMember_id());
		t_password.setText(member.getPassword());
		if(member.getMember_type_id()==1) {
			t_type.setText("관리자");
		}else {
			t_type.setText("일반");
		}
		t_name.setText(member.getName());
		t_nickname.setText(member.getNickname());
		t_regdate.setText(member.getRegdate());
		t_email.setText(member.getEmail());
	}
}
