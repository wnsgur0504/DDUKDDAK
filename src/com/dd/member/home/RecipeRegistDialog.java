package com.dd.member.home;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class RecipeRegistDialog extends JDialog {
	JLabel la_nm, la_sumry, la_cookingtime, la_calorie, la_qnt, la_level, la_sum_url, la_step1, la_step2, la_step3,
			la_step4, la_step5;
	JTextField t_nm, t_sumry, t_cookingtime, t_calorie, t_qnt, t_level, t_sum_url, t_step1, t_step2, t_step3, t_step4,
			t_step5;
	JTextField t_step1_url, t_step2_url, t_step3_url, t_step4_url, t_step5_url;
	JLabel la_step1_url, la_step2_url, la_step3_url, la_step4_url, la_step5_url;
	JPanel p_form, p_button;
	JPanel p_basic, p_detail;
	JButton bt_regist, bt_cancle;
	JScrollPane scroll;
	JTextField[] tList = new JTextField[5];
	JTextField[] urlList = new JTextField[5];
	Home home;
	public RecipeRegistDialog(Home home) {
		this.home = home;
		super.setTitle("레시피 등록");
		p_basic = new JPanel();
		p_detail = new JPanel();
		la_nm = new JLabel("레시피명");
		t_nm = new JTextField(15);
		la_sumry = new JLabel("설명");
		t_sumry = new JTextField(15);
		la_cookingtime = new JLabel("조리시간");
		t_cookingtime = new JTextField(15);
		la_calorie = new JLabel("칼로리");
		t_calorie = new JTextField(15);
		la_qnt = new JLabel("양");
		t_qnt = new JTextField(15);
		la_level = new JLabel("난이도");
		t_level = new JTextField(15);
		la_sum_url = new JLabel("썸네일 URL");
		t_sum_url = new JTextField(15);
		la_step1 = new JLabel("STEP 1");
		la_step1_url = new JLabel("첨부이미지");
		t_step1_url = new JTextField(5);
		t_step1 = new JTextField(15);
		la_step2 = new JLabel("STEP 2");
		la_step2_url = new JLabel("첨부이미지");
		t_step2_url = new JTextField(5);
		t_step2 = new JTextField(15);
		la_step3 = new JLabel("STEP 3");
		la_step3_url = new JLabel("첨부이미지");
		t_step3_url = new JTextField(5);
		t_step3 = new JTextField(15);
		la_step4 = new JLabel("STEP 4");
		la_step4_url = new JLabel("첨부이미지");
		t_step4_url = new JTextField(5);
		t_step4 = new JTextField(15);
		la_step5 = new JLabel("STEP 5");
		la_step5_url = new JLabel("첨부이미지");
		t_step5_url = new JTextField(5);
		t_step5 = new JTextField(15);
		p_form = new JPanel();
		p_button = new JPanel();
		bt_regist = new JButton("등록");
		bt_cancle = new JButton("취소");
		scroll = new JScrollPane(p_form);
		tList[0] = t_step1;
		tList[1] = t_step2;
		tList[2] = t_step3;
		tList[3] = t_step4;
		tList[4] = t_step5;
		urlList[0] = t_step1_url;
		urlList[1] = t_step2_url;
		urlList[2] = t_step3_url;
		urlList[3] = t_step4_url;
		urlList[4] = t_step5_url;

		la_step1.setHorizontalAlignment(JLabel.CENTER);
		la_step2.setHorizontalAlignment(JLabel.CENTER);
		la_step3.setHorizontalAlignment(JLabel.CENTER);
		la_step4.setHorizontalAlignment(JLabel.CENTER);
		la_step5.setHorizontalAlignment(JLabel.CENTER);
		la_step1.setPreferredSize(new Dimension(180, 30));
		la_step2.setPreferredSize(new Dimension(180, 30));
		la_step3.setPreferredSize(new Dimension(180, 30));
		la_step4.setPreferredSize(new Dimension(180, 30));
		la_step5.setPreferredSize(new Dimension(180, 30));
		p_basic.setPreferredSize(new Dimension(190, 600));
		p_detail.setPreferredSize(new Dimension(190, 600));
//		p_form.setPreferredSize(new Dimension(200, 650));
		scroll.setPreferredSize(new Dimension(200, 590));
		setLayout(new BorderLayout());
		
		p_basic.add(la_nm);
		p_basic.add(t_nm);
		p_basic.add(la_sumry);
		p_basic.add(t_sumry);
		p_basic.add(la_cookingtime);
		p_basic.add(t_cookingtime);
		p_basic.add(la_calorie);
		p_basic.add(t_calorie);
		p_basic.add(la_qnt);
		p_basic.add(t_qnt);
		p_basic.add(la_level);
		p_basic.add(t_level);
		p_basic.add(la_sum_url);
		p_basic.add(t_sum_url);
		p_detail.add(la_step1);
		p_detail.add(la_step1_url);
		p_detail.add(t_step1_url);
		p_detail.add(t_step1);
		p_detail.add(la_step2);
		p_detail.add(la_step2_url);
		p_detail.add(t_step2_url);
		p_detail.add(t_step2);
		p_detail.add(la_step3);
		p_detail.add(la_step3_url);
		p_detail.add(t_step3_url);
		p_detail.add(t_step3);
		p_detail.add(la_step4);
		p_detail.add(la_step4_url);
		p_detail.add(t_step4_url);
		p_detail.add(t_step4);
		p_detail.add(la_step5);
		p_detail.add(la_step5_url);
		p_detail.add(t_step5_url);
		p_detail.add(t_step5);
		p_form.add(p_basic);
		p_form.add(p_detail);
		p_button.add(bt_regist);
		p_button.add(bt_cancle);
		add(scroll);
		add(p_button, BorderLayout.SOUTH);
		
		
		setSize(500, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		
		bt_regist.addActionListener((e)->{
			insertRecipe();
		});
		
		bt_cancle.addActionListener((e)->{
			this.dispose();
		});
	}
	
	public void checkInvalid() {
		 		
	}
	
	public void insertRecipe() {
		PreparedStatement pstmt = null;
		int result=0;
		int index=1;
		int idx = home.getAppMain().getMember().getMember_idx();
		String sql = "insert into recipe_basic(recipe_id, recipe_nm_ko, sumry, cooking_time, calorie, qnt, level_nm, img_url, member_idx)";
		sql +=" values(seq_recipe.nextval, ?, ?, ?, ? ,? ,? ,? ,?)";
		try {
			pstmt = home.getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setString(1, t_nm.getText());
			pstmt.setString(2, t_sumry.getText());
			pstmt.setString(3, t_cookingtime.getText());
			pstmt.setString(4, t_calorie.getText());
			pstmt.setString(5, t_qnt.getText());
			pstmt.setString(6, t_level.getText());
			pstmt.setString(7, t_sum_url.getText());
			pstmt.setInt(8, idx);
			result = pstmt.executeUpdate();
			if(result==1) {
				System.out.println("성공");
			}else {
				System.out.println("실패");
			}
			
			for(int i=0;i<5;i++) {
				if(!tList[i].getText().equals("")) {
					sql = "insert into recipe_detail(recipe_detail, recipe_id, cooking_no, cooking_dc, stre_step_image_url) values(seq_recipe_detail.nextval, seq_recipe.currval, ?, ?, ?)";
					pstmt = home.getAppMain().getConnetion().prepareStatement(sql);
					pstmt.setInt(1, index);
					pstmt.setString(2, tList[i].getText());
					pstmt.setString(3, urlList[i].getText());
					int r = pstmt.executeUpdate();
					System.out.println(r);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			home.getAppMain().getDBManager().close(pstmt);
		}
	}
}
