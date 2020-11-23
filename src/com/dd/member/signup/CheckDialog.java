package com.dd.member.signup;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.dd.member.main.AppMain;

public class CheckDialog extends JDialog{
	JTextField t_input;
	JLabel la_check;
	JButton bt_check, bt_confirm,bt_cancle;
	AppMain appMain;
	SignUp signUp;
	boolean checked=false;
	//아이디와 닉네임을 체크할 수 있는 다이얼로그 
	
	public CheckDialog(SignUp signUp, JFrame frame, String title, int type) {
		super(frame, title);
		this.appMain = (AppMain)frame;
		this.signUp = signUp;
		t_input = new JTextField(20);
		bt_check = new JButton("중복확인");
		bt_confirm = new JButton("확인");
		bt_cancle = new JButton("취소");
		la_check = new JLabel();
		
		la_check.setPreferredSize(new Dimension(380, 30));
		la_check.setHorizontalAlignment(JLabel.CENTER);
		
		add(t_input);
		add(bt_check);
		add(la_check);
		add(bt_confirm);
		add(bt_cancle);
		
		if(type==1) { //id체크
			la_check.setText("아이디 중복확인 필요");
			bt_check.addActionListener((e)->{
				System.out.println("클릭햇니");
				if(t_input.getText().length()>4) {
					if(check(t_input.getText(), type)==1){
						la_check.setText("중복된 아이디입니다.");
						checked=false;
					}else {
						la_check.setText("사용가능한 아이디입니다.");
						checked=true;
					}
				}else {
					la_check.setText("5자 이상의 아이디를 입력해주세요!");
					checked=false;
				}
			});
		}else if(type==2){//닉네임체크
			la_check.setText("닉네임 중복확인 필요");
			bt_check.addActionListener((e)->{
				System.out.println("클릭햇니");
				if(t_input.getText().length()>1) {
					if(check(t_input.getText(), type)==1){
						la_check.setText("중복된 닉네임입니다.");
						checked=false;
					}else {
						la_check.setText("사용가능한 닉네임입니다.");
						checked=true;
					}
				}else {
					la_check.setText("2자 이상의 닉네임를 입력해주세요!");
					checked=false;
				}
			});

		}
		
		bt_confirm.addActionListener((e)->{
			if(type==1) {
				if(checked) {
					signUp.setCheckId(checked);
					signUp.setID(t_input.getText());
				}
			}else if(type==2) {
				if(checked) {
					signUp.setCheckNick(checked);
					signUp.setNick(t_input.getText());
				}
			}
			this.dispose();
		});
		
		bt_cancle.addActionListener((e)->{
			this.dispose();
		});
		setLayout(new FlowLayout());
		setSize(400, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	public int check(String check, int type) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		if(type==1) {
			sql="select count(*) as cnt from member where member_id=?";
		}else if(type==2) {
			sql="select count(*) as cnt from member where nickname=?";
		}
		try {
			pstmt = appMain.getConnetion().prepareStatement(sql);
			pstmt.setString(1, check);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.getDBManager().close(pstmt, rs);
		}
		return result;
	}
	
	
	
}
