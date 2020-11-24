package com.dd.member.signup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dd.member.main.AppMain;
import com.dd.member.main.Page;

public class SignUp extends Page {
	protected JLabel la_signUp;
	protected JLabel la_id, la_pw, la_pw2, la_name, la_nickname;
	protected JTextField t_id, t_name, t_nickname;
	protected JPasswordField t_pw, t_pw2;
	protected JButton bt_idCheck, bt_nickCheck, bt_sign, bt_finish;
	protected JPanel p_form, p_button;
	protected boolean checkId = false;
	protected boolean checkNick = false;
	protected boolean checkPw = false;

	public SignUp(AppMain appMain) {
		super(appMain);
		la_signUp = new JLabel("회원가입");
		la_id = new JLabel("아이디");
		t_id = new JTextField(15);
		bt_idCheck = new JButton("아이디확인");
		la_pw = new JLabel("비밀번호");
		t_pw = new JPasswordField(25);
		la_pw2 = new JLabel("비밀번호확인");
		t_pw2 = new JPasswordField(25);
		la_name = new JLabel("이름");
		t_name = new JTextField(25);
		la_nickname = new JLabel("닉네임");
		t_nickname = new JTextField(15);
		bt_nickCheck = new JButton("닉네임확인");
		bt_sign = new JButton("가입");
		bt_finish = new JButton("취소");
		p_form = new JPanel();
		p_button = new JPanel();

		la_signUp.setFont(new Font("궁서", Font.BOLD, 50));
		la_signUp.setPreferredSize(new Dimension(300, 250));
		la_signUp.setHorizontalAlignment(JLabel.CENTER);
		p_form.setPreferredSize(new Dimension(480, 350));
		Dimension section = new Dimension(150, 30);
		Dimension d = new Dimension(480, 30);
		la_id.setPreferredSize(section);
		la_pw.setPreferredSize(section);
		la_pw2.setPreferredSize(section);
		la_name.setPreferredSize(section);
		la_nickname.setPreferredSize(section);
		t_id.setPreferredSize(d);
		t_id.setEnabled(false);
		t_pw.setPreferredSize(d);
		t_pw2.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_nickname.setPreferredSize(d);
		t_nickname.setEnabled(false);

		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(bt_idCheck);
		p_form.add(la_pw);
		p_form.add(t_pw);
		p_form.add(la_pw2);
		p_form.add(t_pw2);
		p_form.add(la_name);
		p_form.add(t_name);
		p_form.add(la_nickname);
		p_form.add(t_nickname);
		p_form.add(bt_nickCheck);
		p_button.add(bt_sign);
		p_button.add(bt_finish);

		add(la_signUp, BorderLayout.NORTH);
		add(p_form);
		add(p_button, BorderLayout.SOUTH);
		setVisible(false);

		bt_idCheck.addActionListener((e) -> {
			new CheckDialog(this, appMain, "아이디 중복확인", 1);
		});

		bt_nickCheck.addActionListener((e) -> {
			new CheckDialog(this, appMain, "닉네임 중복확인", 2);
		});

		bt_sign.addActionListener((e) -> {
			if (!(checkId && checkNick)) {
				JOptionPane.showMessageDialog(this, "중복확인 해라");
				return;
			} else if (!checkPw) {
				JOptionPane.showMessageDialog(this, "비밀번호확인해라");
				return;
			} else if (t_name.getText().length() <2) {
				JOptionPane.showMessageDialog(this, "이름입력해라");
				return;
			}else {
				if(signUp()==1) {
					JOptionPane.showMessageDialog(this, "가입성공");
					appMain.showPage(AppMain.LOGIN);
				}else {
					JOptionPane.showMessageDialog(this, "가입실패");
				}
			}

			System.out.println("아이디:" + checkId + "닉네임:" + checkNick);
		});
		t_pw2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (t_pw.getText().equals(t_pw2.getText())) {
					checkPw = true;
				} else {
					checkPw = false;
				}
			}
		});
		bt_finish.addActionListener((e)->{
			appMain.showPage(AppMain.LOGIN);
		});
	}

	public void setCheckId(boolean checkId) {
		this.checkId = checkId;
	}

	public void setCheckNick(boolean checkNick) {
		this.checkNick = checkNick;
	}

	public void setID(String id) {
		t_id.setText(id);
	}

	public void setNick(String nick) {
		t_nickname.setText(nick);
	}
	
	public int signUp() {
		PreparedStatement pstmt=null;
		int result=0;
		String sql = "insert into member(member_idx, member_id, password, name, nickname) values(seq_member.nextval, ?, ?, ?, ?)";
		try {
			pstmt=getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, t_pw.getText());
			pstmt.setString(3, t_name.getText());
			pstmt.setString(4, t_nickname.getText());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAppMain().getDBManager().close(pstmt);
		}
		return result;
	}

}
