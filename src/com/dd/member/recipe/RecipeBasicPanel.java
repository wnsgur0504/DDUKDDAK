package com.dd.member.recipe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.dd.dto.RecipeBasic;

import commom.image.ImageUtil;

public class RecipeBasicPanel extends JPanel {
	JPanel p_img;
	JPanel content;
	JPanel p_sumry;
	JLabel la_recipe_nm, la_sumry, la_qnt, la_level, la_calorie, la_cookingtime;
	RecipeBasic recipe;
	Thread imgThread;
	Image image;
	URL url = this.getClass().getClassLoader().getResource("com/dd/res/dduk.PNG");
	public RecipeBasicPanel(RecipeBasic recipe) {
		if(recipe.getImg_url()==null) {
			
			image = ImageUtil.getCustomSize(ImageUtil.getImageFromURL(url.toString()), 100, 85);
		}else {
			image = ImageUtil.getCustomSize(ImageUtil.getImageFromURL(recipe.getImg_url()), 100, 85);
		}

		p_img = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
			}
		};

		this.recipe = recipe;
		content = new JPanel();
		p_sumry = new JPanel();
		la_recipe_nm = new JLabel(recipe.getRecipe_nm_ko());
		la_sumry = new JLabel(recipe.getSumry());
		la_qnt = new JLabel(recipe.getQnt());
		la_calorie = new JLabel(recipe.getCalorie());
		la_cookingtime = new JLabel("조리시간 : " + recipe.getCooking_time());

		la_recipe_nm.setHorizontalAlignment(JLabel.CENTER);
		la_recipe_nm.setFont(new Font("궁서", Font.BOLD, 15));
		la_sumry.setHorizontalAlignment(JLabel.CENTER);
		la_qnt.setHorizontalAlignment(JLabel.CENTER);
		la_cookingtime.setHorizontalAlignment(JLabel.CENTER);
		la_calorie.setHorizontalAlignment(JLabel.CENTER);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(550, 200));
		p_img.setPreferredSize(new Dimension(100, 85));
		p_sumry.setLayout(new GridLayout(5, 1));
		this.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 5)));

		p_sumry.add(la_sumry);
		p_sumry.add(la_qnt);
		p_sumry.add(la_calorie);
		p_sumry.add(la_cookingtime);
		content.add(la_recipe_nm);
		add(p_img, BorderLayout.WEST);
		add(content, BorderLayout.NORTH);
		add(p_sumry);

		if (recipe.getLevel_nm() != null) {
			la_level = new JLabel(recipe.getLevel_nm());
			p_sumry.add(la_level);
			la_level.setHorizontalAlignment(JLabel.CENTER);
		}

		setPreferredSize(new Dimension(520, 200));
		setVisible(true);
	}
}
