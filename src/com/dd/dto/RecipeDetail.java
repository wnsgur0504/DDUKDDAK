package com.dd.dto;

public class RecipeDetail {
	private long recipe_id;
	private long cooking_no;
	private String cooking_dc;
	private String stre_step_image_url;
	public long getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(long recipe_id) {
		this.recipe_id = recipe_id;
	}
	public long getCooking_no() {
		return cooking_no;
	}
	public void setCooking_no(long cooking_no) {
		this.cooking_no = cooking_no;
	}
	public String getCooking_dc() {
		return cooking_dc;
	}
	public void setCooking_dc(String cooking_dc) {
		this.cooking_dc = cooking_dc;
	}
	public String getStre_step_image_url() {
		return stre_step_image_url;
	}
	public void setStre_step_image_url(String stre_step_image_url) {
		this.stre_step_image_url = stre_step_image_url;
	}
	
	
}
