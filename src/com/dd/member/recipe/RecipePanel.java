package com.dd.member.recipe;

import com.dd.dto.RecipeBasic;
import com.dd.member.main.AppMain;
import com.dd.member.main.Page;

public class RecipePanel extends Page{
	RecipeBasicPanel rbp;
	RecipeDetailPanel drp;
	public RecipePanel(AppMain appMain) {
		super(appMain);
		setVisible(false);
	}
	
	public void createLabels(RecipeBasic recipeBasic) {
		if(rbp!=null) {
			remove(rbp);
			updateUI();
		}
		rbp=new RecipeBasicPanel(recipeBasic);
		add(rbp);
	}
	
	public void createRecipe() {
		if(drp!=null) {
			remove(drp);
			updateUI();
		}
		drp = new RecipeDetailPanel(this.getAppMain());
		add(drp);
	}
	
	public void createButton() {
		drp.createButton();
	}

}
