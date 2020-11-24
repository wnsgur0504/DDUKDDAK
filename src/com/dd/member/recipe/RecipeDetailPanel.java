package com.dd.member.recipe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.dd.dto.RecipeDetail;
import com.dd.member.main.AppMain;
import com.dd.member.main.Page;

import commom.image.ImageUtil;

public class RecipeDetailPanel extends Page{
//	JLabel tmp;
	JPanel p_dc;
	JScrollPane scroll;
	JButton bt_back;
	long recipe_id;
	ArrayList<RecipeDetail> detailList = new ArrayList<RecipeDetail>();
	ArrayList<JLabel> dc = new ArrayList<JLabel>();
	
	public RecipeDetailPanel(AppMain appMain) {
		super(appMain);
		
		bt_back = new JButton("목록");
		
		
		
		add(bt_back);
		setVisible(false);
		bt_back.addActionListener((e)->{
			appMain.showPage(AppMain.HOME);
		});
	}
	
	public void setRecipe(long recipe_id) {
		this.recipe_id = recipe_id;
		
		getCotent();
		makeUpRecipe();
	}
	
	public void getCotent() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select cooking_no, cooking_dc, stre_step_image_url from recipe_detail where recipe_id=? order by cooking_no asc";
		detailList.clear();
		
		
		try {
			pstmt = getAppMain().getDBManager().connect().prepareStatement(sql);
			pstmt.setLong(1, recipe_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RecipeDetail rd = new RecipeDetail();
				rd.setCooking_no(rs.getLong("cooking_no"));
				rd.setCooking_dc(rs.getString("cooking_dc"));
				rd.setStre_step_image_url(rs.getString("stre_step_image_url"));
				detailList.add(rd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getAppMain().getDBManager().close(pstmt, rs);
		}
	}
	
	public void makeUpRecipe() {
		GridLayout layout = new GridLayout(2, 1);
		dc.clear();
		
		p_dc = new JPanel();
		scroll = new JScrollPane(p_dc);
		scroll.setPreferredSize(new Dimension(getAppMain().WIDTH-50, getAppMain().HEIGHT-400));
		add(scroll);
		
		for(int i=0;i<detailList.size();i++) {
			JPanel p_label = new JPanel();
			String img = detailList.get(i).getStre_step_image_url();
			System.out.println(img);
			if(img!=null) {
				Image image = ImageUtil.getCustomSize(ImageUtil.getImageFromURL(img), 350, 250);
				JPanel p_img = new JPanel() {
					public void paint(Graphics g) {
						g.drawImage(image, 0, 0, this);
					}
				};
				p_label.add(p_img);
				p_img.repaint();
			}else {
				URL url = this.getClass().getClassLoader().getResource("com/dd/res/dduk.PNG");
				System.out.println(url.getPath());
				Image image = ImageUtil.getCustomSize(ImageUtil.getImageFromURL(url.toString()), 350, 250);
				JPanel p_img = new JPanel() {
					public void paint(Graphics g) {
						g.drawImage(image, 0, 0, this);
					}
				};
				p_label.add(p_img);
			}
			
			p_label.setPreferredSize(new Dimension(500, 400));
			p_label.setLayout(layout);
			p_label.add(new JLabel((i+1)+". "+detailList.get(i).getCooking_dc(), JLabel.LEFT));
			p_dc.add(p_label);

		}
		p_dc.setPreferredSize(new Dimension(630, detailList.size()*400));
		
	}
}
