package com.dd.admin.recipe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.dd.admin.main.Adminmain;
import com.dd.admin.main.Page;
import com.dd.dto.RecipeBasic;
import com.dd.member.recipe.RecipeBasicPanel;

public class AdminRecipe extends Page{
	JPanel p_west;
	JPanel p_search;
	JTable basicTable;
	JScrollPane scroll1;
	RecipeModel basicModel;
	RecipeBasicPanel basicPanel;
	JTextField t_search;
	JButton bt_search;
	AdminRecipePanel recipePanel;
	JPanel p_recipe;
	
	public AdminRecipe(Adminmain adminmain) {
		super(adminmain);
		p_west = new JPanel();
		p_search = new JPanel();
		basicTable = new JTable();
		scroll1 = new JScrollPane(basicTable);
		t_search = new JTextField(15);
		bt_search = new JButton("검색");
		p_recipe = new JPanel();
		
		scroll1.setPreferredSize(new Dimension(Adminmain.WIDTH-570, Adminmain.HEIGHT-150));
		p_west.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		
		getRecipeBasicList();
		
		p_search.add(t_search);
		p_search.add(bt_search);
		p_west.add(p_search, BorderLayout.NORTH);
		p_west.add(scroll1);
		add(p_west, BorderLayout.WEST);
		add(p_recipe);
		
		basicTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int index = basicTable.getSelectedRow();
				p_recipe.removeAll();
				recipePanel = new AdminRecipePanel(AdminRecipe.this);
				recipePanel.setRecipe(basicModel.getSelectedRecipe(index));
				recipePanel.changeRecipe();
				p_recipe.add(recipePanel);
				p_recipe.updateUI();
				
			}
		});
		
		bt_search.addActionListener((e)->{
			searchRecipe();
		});
	}
	
	public void searchRecipe() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from recipe_basic where recipe_nm_ko like '%'||?||'%'";
		ArrayList<RecipeBasic> record = new ArrayList<RecipeBasic>();
		ArrayList<String> column = new ArrayList<String>();
		try {
			pstmt = getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setString(1, t_search.getText());
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int length = meta.getColumnCount();
			for(int i=0;i<length;i++) {
				column.add(meta.getColumnName(i+1));
			}
			
			while(rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setLevel_nm(rs.getString("level_nm"));
				basic.setImg_url(rs.getString("img_url"));
				record.add(basic);
			}
			basicTable.setModel(basicModel = new RecipeModel(record, column));
			basicTable.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAdminmain().getDbManager().close(pstmt, rs);
		}
	}
	
	public void getRecipeBasicList() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from recipe_basic";
		ArrayList<RecipeBasic> record = new ArrayList<RecipeBasic>();
		ArrayList<String> column = new ArrayList<String>();
		try {
			pstmt = getAdminmain().getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int length = meta.getColumnCount();
			for(int i=0;i<length;i++) {
				column.add(meta.getColumnName(i+1));
			}
			
			while(rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setLevel_nm(rs.getString("level_nm"));
				basic.setImg_url(rs.getString("img_url"));
				record.add(basic);
			}
			basicTable.setModel(basicModel = new RecipeModel(record, column));
			basicTable.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAdminmain().getDbManager().close(pstmt, rs);
		}
	}
	
}
