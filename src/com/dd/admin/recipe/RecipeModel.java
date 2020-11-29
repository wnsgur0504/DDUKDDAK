package com.dd.admin.recipe;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dd.dto.RecipeBasic;
import com.sun.prism.impl.Disposer.Record;

public class RecipeModel extends AbstractTableModel{
	
	ArrayList<RecipeBasic> recipeBasic = new ArrayList<RecipeBasic>();
	ArrayList<String> column = new ArrayList<String>();

	public RecipeModel(ArrayList<RecipeBasic> recipeBasic, ArrayList<String> column) {
		this.recipeBasic = recipeBasic;
		this.column = column;
	}

	@Override
	public int getRowCount() {
		return recipeBasic.size();
	}

	@Override
	public int getColumnCount() {
		return column.size();
	}
	
	@Override
	public String getColumnName(int index) {
		return column.get(index);
	}

	@Override
	public Object getValueAt(int row, int col) {
		RecipeBasic basic = recipeBasic.get(row);
		String obj=null;
		if(col==0) {
			obj = Long.toString(basic.getRecipe_id());
		}else if(col==1){
			obj = basic.getRecipe_nm_ko();
		}else if(col==2){
			obj = basic.getSumry();
		}else if(col==3){
			obj = basic.getCooking_time();
		}else if(col==4){
			obj = basic.getCalorie();
		}else if(col==5){
			obj = basic.getQnt();
		}else if(col==6){
			obj = basic.getLevel_nm();
		}else if(col==7){
			obj = basic.getImg_url();
		}
		
		return obj;
	}
	
	public RecipeBasic getSelectedRecipe(int row) {
		return recipeBasic.get(row);
	}
}
