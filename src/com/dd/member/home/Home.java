package com.dd.member.home;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.dd.dto.RecipeBasic;
import com.dd.member.main.AppMain;
import com.dd.member.main.Page;
import com.dd.member.recipe.RecipeBasicPanel;

public class Home extends Page {
	JPanel p_navi;
	JPanel p_search;
	JPanel p_north;
	JPanel p_all;
	JPanel p_bar;
	JScrollPane scroll;
	JButton bt_search;
	JTextField t_search;
	JComboBox<String> cb;
	JButton bt_logout;
	JLabel la_logo;
	JButton bt_myPage;
	JButton bt_regist;
	JButton bt_bookMark;
	JButton bt_more;
	JButton bt_all;
	int startIdx;
	int endIdx;
	ArrayList<RecipeBasic> list = new ArrayList<RecipeBasic>();
	ArrayList<RecipeBasicPanel> panelList = new ArrayList<RecipeBasicPanel>();
	Thread thread;
	String keyword;

	public Home(AppMain appMain) {
		super(appMain);
		p_navi = new JPanel();
		p_bar = new JPanel();
		la_logo = new JLabel("뚝딱");
		bt_logout = new JButton("로그아웃");
		bt_myPage = new JButton("마이페이지");
		bt_bookMark = new JButton("즐겨찾기");
		bt_regist = new JButton("레시피 등록");
		
		p_search = new JPanel();
		cb = new JComboBox<String>();
		cb.addItem("요리이름");
		cb.addItem("작성자");
		cb.addItem("재료");
		t_search = new JTextField(21);
		bt_search = new JButton("검색");
		bt_all = new JButton("전체보기");
		p_north = new JPanel(); // navi와 search를 묶을 패널

		p_all = new JPanel();
		bt_more = new JButton("더보기");
		scroll = new JScrollPane(p_all);

		p_navi.setLayout(new BorderLayout());
		p_north.setLayout(new BorderLayout());
		t_search.setPreferredSize(new Dimension(100, 25));
		la_logo.setFont(new Font("궁서", Font.BOLD, 50));
		la_logo.setPreferredSize(new Dimension(AppMain.WIDTH, 100));
		la_logo.setHorizontalAlignment(JLabel.CENTER);
		scroll.setPreferredSize(new Dimension(AppMain.WIDTH - 70, AppMain.HEIGHT - 330));

		p_bar.add(bt_bookMark);
		p_bar.add(bt_regist);
		p_bar.add(bt_myPage);
		p_bar.add(bt_logout);
		p_navi.add(la_logo);
		p_navi.add(p_bar, BorderLayout.SOUTH);
		p_search.add(cb);
		p_search.add(t_search);
		p_search.add(bt_search);
		p_search.add(bt_all);
		p_north.add(p_navi);
		p_north.add(p_search, BorderLayout.SOUTH);

		add(p_north, BorderLayout.NORTH);
		add(scroll);
		add(bt_more, BorderLayout.SOUTH);

		setVisible(false);

		bt_bookMark.addActionListener((e)->{
			bt_more.setEnabled(false);
			thread = new Thread() {
				public void run() {
					init_bookMark();
				};
			};
			thread.start();
		});
		bt_logout.addActionListener((e) -> {
			appMain.showPage(AppMain.LOGIN);
		});

		bt_myPage.addActionListener((e) -> {
			appMain.showPage(AppMain.MYPAGE);
		});

		bt_search.addActionListener((e) -> {
			bt_more.setEnabled(true);
			thread = new Thread() {
				public void run() {
					bt_search.setEnabled(false);
					searchRecipe();
					createRecipe();
					bt_search.setEnabled(true);
				};
			};
			thread.start();
		});
		bt_more.addActionListener((e) -> {
			thread = new Thread() {
				public void run() {
					bt_more.setEnabled(false);
					keyword = t_search.getText();
					if (keyword.equals("")) {
						moreRecipe();
					} else {
						moreSearchRecipe();
					}
					createRecipe();
					scroll.updateUI();
					updateUI();
					bt_more.setEnabled(true);
				};
			};
			thread.start();
		});
		bt_all.addActionListener((e)->{
			bt_more.setEnabled(true);
			thread = new Thread() {
				public void run() {
					init();
				};
			};
			thread.start();
		});
		bt_regist.addActionListener((e)->{
			new RecipeRegistDialog(Home.this);
		});
	}
	
	public void init_bookMark() {
		p_all.removeAll();
		list.clear();
		panelList.clear();
		getBookMark();
		createRecipe();
	}
	
	public void getBookMark() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from recipe_basic where recipe_id in (select recipe_id from recipe_bookmark where member_idx=?)";

		try {
			pstmt = getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setInt(1, getAppMain().getMember().getMember_idx());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setImg_url(rs.getString("img_url"));
				list.add(basic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			getAppMain().getDBManager().close(pstmt, rs);
		}
		
		
	}
	
	public void init() {
		bt_all.setEnabled(false);
		startIdx = 0;
		endIdx = 0;
		p_all.removeAll();
		list.clear();
		panelList.clear();
		t_search.setText("");
		
		getRecipe();
		createRecipe();
		bt_all.setEnabled(true);
	}

	public void getRecipe() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ( SELECT A.*, ROWNUM AS RNUM FROM (SELECT* FROM recipe_basic ORDER BY recipe_id desc) A WHERE ROWNUM <= ?) WHERE RNUM > ?";
		startIdx = endIdx;
		endIdx = endIdx + 10;
		try {
			pstmt = getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setInt(1, endIdx);
			pstmt.setInt(2, startIdx);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setImg_url(rs.getString("img_url"));
				System.out.println(basic.getLevel_nm());
				list.add(basic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getAppMain().getDBManager().close(pstmt, rs);
		}
	}

	public void createRecipe() {
		p_all.setPreferredSize(new Dimension(p_all.getWidth(), 200 * list.size()));
		for (int i = startIdx; i < list.size(); i++) {
			RecipeBasicPanel rp = new RecipeBasicPanel(list.get(i));
			rp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					RecipeBasicPanel event = (RecipeBasicPanel) e.getSource();
					int index = panelList.indexOf(event);
					getAppMain().setSelectRecipe(list.get(index));
					System.out.println(list.get(index).getRecipe_nm_ko());
					getAppMain().showPage(AppMain.DETAILRECIPE);

				}
			});
			panelList.add(rp);
			p_all.add(rp);
			updateUI();

		}
	}

	public void moreSearchRecipe() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		startIdx = endIdx;
		endIdx = endIdx + 10;
		System.out.println("start:" + startIdx);
		System.out.println("end:" + endIdx);
		String sql = "SELECT * FROM ( SELECT A.*, ROWNUM AS RNUM FROM (SELECT* FROM recipe_basic where recipe_nm_ko like '%'||?||'%' ORDER BY recipe_id desc) A WHERE ROWNUM <= ?) WHERE RNUM > ?";
		try {
			pstmt = getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, endIdx);
			pstmt.setInt(3, startIdx);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setImg_url(rs.getString("img_url"));
//				System.out.println(basic.getLevel_nm());
				list.add(basic);
			}
			System.out.println(list.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getAppMain().getDBManager().close(pstmt, rs);
		}
	}

	public void moreRecipe() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		startIdx = endIdx;
		endIdx = endIdx + 10;
		System.out.println("start:" + startIdx);
		System.out.println("end:" + endIdx);
		String sql = "SELECT * FROM ( SELECT A.*, ROWNUM AS RNUM FROM (SELECT* FROM recipe_basic ORDER BY recipe_id desc) A WHERE ROWNUM <= ?) WHERE RNUM > ?";
		try {
			pstmt = getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setInt(2, startIdx);
			pstmt.setInt(1, endIdx);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setImg_url(rs.getString("img_url"));
//				System.out.println(basic.getLevel_nm());
				list.add(basic);
			}
			System.out.println(list.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getAppMain().getDBManager().close(pstmt, rs);
		}
	}

	public void searchRecipe() {
		keyword = t_search.getText();
		p_all.removeAll();
		list.clear();
		panelList.clear();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		startIdx = 0;
		endIdx = 10;
		String sql = "SELECT * FROM ( SELECT A.*, ROWNUM AS RNUM FROM (SELECT * FROM recipe_basic  where recipe_nm_ko like '%'||?||'%' ORDER BY recipe_id desc) A WHERE ROWNUM <= ?) WHERE RNUM > ?";

		try {
			pstmt = getAppMain().getConnetion().prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, endIdx);
			pstmt.setInt(3, startIdx);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecipeBasic basic = new RecipeBasic();
				basic.setRecipe_id(rs.getLong("recipe_id"));
				basic.setRecipe_nm_ko(rs.getString("recipe_nm_ko"));
				basic.setSumry(rs.getString("sumry"));
				basic.setCooking_time(rs.getString("cooking_time"));
				basic.setCalorie(rs.getString("calorie"));
				basic.setQnt(rs.getString("qnt"));
				basic.setImg_url(rs.getString("img_url"));
				System.out.println(basic.getRecipe_nm_ko());
				list.add(basic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAppMain().getDBManager().close(pstmt, rs);
		}
	}
}
