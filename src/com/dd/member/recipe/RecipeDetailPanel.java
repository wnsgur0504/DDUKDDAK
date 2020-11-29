package com.dd.member.recipe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.dd.dto.Member;
import com.dd.dto.RecipeBasic;
import com.dd.dto.RecipeDetail;
import com.dd.member.main.AppMain;

import commom.image.ImageUtil;

public class RecipeDetailPanel extends JPanel {
//	JLabel tmp;
	JPanel p_dc;
	JScrollPane scroll;
	JButton bt_back;
	JButton bt_bookMark;
	JButton bt_unBookMark;
	RecipeBasic recipeBasic;
	ArrayList<RecipeDetail> detailList = new ArrayList<RecipeDetail>();
	ArrayList<JLabel> dc = new ArrayList<JLabel>();
	Thread thread;
	Thread btThread;
	AppMain appMain;
	Member member;

	public RecipeDetailPanel(AppMain appMain) {
		this.appMain = appMain;
		p_dc = new JPanel();
		scroll = new JScrollPane(p_dc);
		bt_back = new JButton("목록");
		bt_bookMark = new JButton("즐겨찾기 추가"); // 나중에 아이콘으로 바꿀 수도 있음
		bt_unBookMark = new JButton("즐겨찾기 해제");

		setPreferredSize(new Dimension(appMain.WIDTH, appMain.HEIGHT));
		bt_back.setPreferredSize(new Dimension(120, 50));
		bt_bookMark.setPreferredSize(new Dimension(120, 50));
		bt_unBookMark.setPreferredSize(new Dimension(120, 50));
		scroll.setPreferredSize(new Dimension(appMain.WIDTH - 100, appMain.HEIGHT - 400));

		add(scroll);
		add(bt_back);
		add(bt_bookMark);
		add(bt_unBookMark);

		bt_back.addActionListener((e) -> {
			appMain.showPage(AppMain.HOME);
		});
		bt_bookMark.addActionListener((e) -> {
			bookMark(1);
		});
		bt_unBookMark.addActionListener((e) -> {
			bookMark(2);
		});
		setRecipe(appMain.getSelectRecipe());
	}
	
	public void createButton() {
		member = appMain.getMember();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) as cnt from recipe_bookmark where recipe_id=? and member_idx=?";
		int result = 0;
		
		try {
			pstmt = appMain.getConnetion().prepareStatement(sql);
			pstmt.setLong(1, recipeBasic.getRecipe_id());
			pstmt.setInt(2, member.getMember_idx());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getInt("cnt");
			}
			System.out.println(result);
			if(result==0) {
				bt_bookMark.setVisible(true);
				bt_unBookMark.setVisible(false);
			}else if(result==1) {
				bt_bookMark.setVisible(false);
				bt_unBookMark.setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void bookMark(int type) {
		PreparedStatement pstmt = null;
		String sql="";
		if(type==1) {
			sql = "insert into recipe_bookmark(recipe_bookmark_id, recipe_id, member_idx) values(seq_recipe_bookmark.nextval, ?, ?)";
		}else if(type==2){
			sql ="delete from recipe_bookmark where recipe_id=? and member_idx=?";
		}
		int result =0;
		
		try {
			pstmt = appMain.getConnetion().prepareStatement(sql);
			pstmt.setLong(1, recipeBasic.getRecipe_id());
			pstmt.setInt(2, member.getMember_idx());
			result = pstmt.executeUpdate();
			if(result==1) {
				if(type==1) {
					bt_bookMark.setVisible(false);
					bt_unBookMark.setVisible(true);
				}else {
					bt_bookMark.setVisible(true);
					bt_unBookMark.setVisible(false);
				}
			}else {
				System.out.println(" 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.getDBManager().close(pstmt);
		}
	}
	

	public void setRecipe(RecipeBasic recipeBasic) {
		this.recipeBasic = recipeBasic;
		thread = new Thread() {
			public void run() {
				getCotent();
				makeUpRecipe();
				RecipeDetailPanel.this.updateUI();
			};
		};
		thread.start();
	}

	public void getCotent() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select cooking_no, cooking_dc, stre_step_image_url from recipe_detail where recipe_id=? order by cooking_no asc";
		detailList.clear();

		try {
			pstmt = appMain.getDBManager().connect().prepareStatement(sql);
			pstmt.setLong(1, recipeBasic.getRecipe_id());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeDetail rd = new RecipeDetail();
				rd.setCooking_no(rs.getLong("cooking_no"));
				rd.setCooking_dc(rs.getString("cooking_dc"));
				rd.setStre_step_image_url(rs.getString("stre_step_image_url"));
				detailList.add(rd);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			appMain.getDBManager().close(pstmt, rs);
		}
	}

	public void makeUpRecipe() {
		GridLayout layout = new GridLayout(2, 1);
		dc.clear();

		for (int i = 0; i < detailList.size(); i++) {
			JPanel p_label = new JPanel();
			JLabel la_name = new JLabel();
			String img = detailList.get(i).getStre_step_image_url();
			System.out.println(img);
			if (img != null) {
				Image image = ImageUtil.getCustomSize(ImageUtil.getImageFromURL(img), 350, 250);
				JPanel p_img = new JPanel() {
					public void paint(Graphics g) {
						g.drawImage(image, 0, 0, this);
					}
				};
				p_label.add(p_img);
				p_img.repaint();
			} else {
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
			JTextField tmp = new JTextField((i + 1) + ". " + detailList.get(i).getCooking_dc(), JLabel.LEFT);
			tmp.setEditable(false);
			p_label.add(tmp);
			p_dc.add(p_label);

		}
		p_dc.setPreferredSize(new Dimension(630, detailList.size() * 400));

	}
}
