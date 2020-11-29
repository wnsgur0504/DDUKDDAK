package com.dd.admin.recipe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dd.dto.RecipeBasic;

public class AdminRecipePanel extends JPanel{
	JTextField t_recipe_id, t_recipe_nm, t_recipe_sumry, t_cookingtime, t_calorie, t_qnt, t_level, t_img_url;
	JLabel la_recipe_id, la_recipe_nm, la_recipe_sumry, la_cookingtime, la_calorie, la_qnt, la_level, la_img_url;
	JButton bt_update, bt_delete; 
	JPanel p_form, p_button;
	AdminRecipe adminRecipe;
	RecipeBasic recipeBasic;
	
	public AdminRecipePanel(AdminRecipe adminRecipe) {
		this.adminRecipe = adminRecipe;
		la_recipe_id = new JLabel("레시피번호");
		t_recipe_id = new  JTextField(15);
		la_recipe_nm = new JLabel("레시피이름");
		t_recipe_nm= new  JTextField(15);
		la_recipe_sumry = new JLabel("레시피설명");
		t_recipe_sumry = new  JTextField(15);
		la_cookingtime = new JLabel("요리시간");
		t_cookingtime = new  JTextField(15);
		la_calorie = new JLabel("칼로리");
		t_calorie = new  JTextField(15);
		la_qnt = new JLabel("  양  ");
		t_qnt = new  JTextField(15);
		la_level = new JLabel("난이도");
		t_level = new  JTextField(15);
		la_img_url = new JLabel("썸네일 주소");
		t_img_url = new  JTextField(15);
		bt_update = new JButton("수정");
		bt_delete = new JButton("삭제");
		p_form = new JPanel(); 
		p_button = new JPanel(); 
		
		p_form.setPreferredSize(new Dimension(200, 500));
		setLayout(new BorderLayout());
		
		p_form.add(la_recipe_id);
		p_form.add(t_recipe_id);
		p_form.add(la_recipe_nm);
		p_form.add(t_recipe_nm);
		p_form.add(la_recipe_sumry);
		p_form.add(t_recipe_sumry);
		p_form.add(la_cookingtime);
		p_form.add(t_cookingtime);
		p_form.add(la_calorie);
		p_form.add(t_calorie);
		p_form.add(la_qnt);
		p_form.add(t_qnt);
		p_form.add(la_level);
		p_form.add(t_level);
		p_form.add(la_img_url);
		p_form.add(t_img_url);
		p_button.add(bt_update);
		p_button.add(bt_delete);
		add(p_form);
		add(p_button, BorderLayout.SOUTH);
		
		bt_update.addActionListener((e)->{
			updateRecipe();
			adminRecipe.getRecipeBasicList();
		});
		bt_delete.addActionListener((e)->{
			deleteRecipe();
			adminRecipe.getRecipeBasicList();
		});
	}
	
	public void updateRecipe() {
		PreparedStatement pstmt = null;
		int result=0;
		String sql = "update recipe_basic set recipe_nm_ko=?, sumry=?, cooking_time=?, calorie=?, qnt=?, level_nm=?, img_url=? where recipe_id=?";
		
		try {
			pstmt = adminRecipe.getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setString(1, t_recipe_nm.getText());
			pstmt.setString(2, t_recipe_sumry.getText());
			pstmt.setString(3, t_cookingtime.getText());
			pstmt.setString(4, t_calorie.getText());
			pstmt.setString(5, t_qnt.getText());
			pstmt.setString(6, t_level.getText());
			pstmt.setString(7, t_img_url.getText());
			pstmt.setInt(8, Integer.parseInt(t_recipe_id.getText()));
			result = pstmt.executeUpdate();
			if(result==1) {
				JOptionPane.showMessageDialog(this, "수정 성공");
			}else {
				JOptionPane.showMessageDialog(this, "수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			adminRecipe.getAdminmain().getDbManager().close(pstmt);
		}
	}
	
	public void deleteRecipe() {
		PreparedStatement pstmt=null;
		int result=0;
		String sql = "delete from recipe_basic where recipe_id=?";
		
		try {
			pstmt = adminRecipe.getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(t_recipe_id.getText()));
			result = pstmt.executeUpdate();
			if(result==1) {
				JOptionPane.showMessageDialog(this, "삭제 성공");
			}else {
				JOptionPane.showMessageDialog(this, "삭제 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			adminRecipe.getAdminmain().getDbManager().close(pstmt);
		}
		
	}
	
	public void setRecipe(RecipeBasic recipeBasic) {
		this.recipeBasic = recipeBasic;
		System.out.println(recipeBasic.getRecipe_nm_ko());
	}
	
	public void changeRecipe() {
		t_recipe_id.setText(Long.toString(recipeBasic.getRecipe_id()));
		t_recipe_nm.setText(recipeBasic.getRecipe_nm_ko());
		t_recipe_sumry.setText(recipeBasic.getSumry());
		t_cookingtime.setText(recipeBasic.getCooking_time());
		t_calorie.setText(recipeBasic.getCalorie());
		t_qnt.setText(recipeBasic.getQnt());
		t_level.setText(recipeBasic.getLevel_nm());
		t_img_url.setText(recipeBasic.getImg_url());
	}
}
