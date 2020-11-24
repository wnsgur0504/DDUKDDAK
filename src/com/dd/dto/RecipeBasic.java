package com.dd.dto;

public class RecipeBasic {
	private long recipe_id;
	private String recipe_nm_ko;
	private String sumry;
	private String cooking_time	;
	private String calorie;
	private String qnt;
	private String level_nm;
	private String img_url;
	public long getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(long recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getRecipe_nm_ko() {
		return recipe_nm_ko;
	}
	public void setRecipe_nm_ko(String recipe_nm_ko) {
		this.recipe_nm_ko = recipe_nm_ko;
	}
	public String getSumry() {
		return sumry;
	}
	public void setSumry(String sumry) {
		this.sumry = sumry;
	}
	public String getCooking_time() {
		return cooking_time;
	}
	public void setCooking_time(String cooking_time) {
		this.cooking_time = cooking_time;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public String getQnt() {
		return qnt;
	}
	public void setQnt(String qnt) {
		this.qnt = qnt;
	}
	public String getLevel_nm() {
		return level_nm;
	}
	public void setLevel_nm(String level_nm) {
		this.level_nm = level_nm;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
}
