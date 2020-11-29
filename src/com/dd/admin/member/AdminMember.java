package com.dd.admin.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.dd.admin.main.Adminmain;
import com.dd.admin.main.Page;
import com.dd.dto.Member;

public class AdminMember extends Page{
	JTable table;
	MemberModel model;
	JPanel p_west;
	JPanel p_center;
	JPanel p_south;
	JScrollPane scroll;
	AdminMemberPanel p_member;
	JPanel p_search;
	JTextField t_search;
	JButton bt_search;
	JComboBox<String> cb;
	
	public AdminMember(Adminmain adminmain) {
		super(adminmain);
		p_west = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		p_search = new JPanel();
		table=  new JTable();
		scroll = new JScrollPane(table);
		t_search = new JTextField(30);
		bt_search = new JButton("검색");
		cb = new JComboBox<String>();
		cb.addItem("이름");
		cb.addItem("닉네임");
		
		
		setLayout(new BorderLayout());
		scroll.setPreferredSize(new Dimension(Adminmain.WIDTH-570, Adminmain.HEIGHT-150));
		t_search.setPreferredSize(new Dimension(scroll.getWidth(), 30));
		p_center.setPreferredSize(new Dimension(Adminmain.WIDTH-600, Adminmain.HEIGHT-500));
//		p_west.setPreferredSize(new Dimension(200, Adminmain.HEIGHT-100));
//		p_south.setPreferredSize(new Dimension(Adminmain.WIDTH-200, 400));
//		p_west.setBackground(Color.BLACK);
		p_west.setLayout(new BorderLayout());
		getMemberList();
		
		p_search.add(cb);
		p_search.add(t_search);
		p_search.add(bt_search);
		p_west.add(p_search, BorderLayout.NORTH);
		p_west.add(scroll);
		p_center.add(p_south);
		add(p_west, BorderLayout.WEST);
		add(p_center);
//		add(p_south, BorderLayout.SOUTH);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedRow();
				Member member = model.getSelectedMember(row);
				p_south.removeAll();
				p_member = new AdminMemberPanel(AdminMember.this);
				p_member.setMember(member);
				p_member.chageMember();
				p_south.add(p_member);
				p_south.updateUI();
			}
		});
		
		bt_search.addActionListener((e)->{
			searchMember();
		});
	} 
	
	public void searchMember() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Member> memberList = new ArrayList<Member>();
		ArrayList<String> columnList = new  ArrayList<String>();
		
		if(cb.getSelectedItem().equals("이름")) {
			sql = "select member_idx, member_id, password, name, nickname, to_char(regdate, 'yyyy/mm/dd') as regdate, email, member_type_id from member where name like '%'||?||'%'";
		}else {
			sql = "select member_idx, member_id, password, name, nickname, to_char(regdate, 'yyyy/mm/dd') as regdate, email, member_type_id from member where nickname like '%'||?||'%'";
		}
		
		try {
			pstmt = getAdminmain().getConnection().prepareStatement(sql);
			pstmt.setString(1, t_search.getText());
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columnTotal = meta.getColumnCount();
			for(int i=1;i<=columnTotal;i++) {
				columnList.add(meta.getColumnName(i));
				System.out.println(meta.getColumnName(i));
			}
			
			while(rs.next()) {
				Member member = new Member();
				member.setMember_idx(rs.getInt("member_idx"));
				member.setMember_id(rs.getString("member_id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setNickname(rs.getString("nickname"));
				member.setRegdate(rs.getString("regdate"));
				member.setEmail(rs.getString("email"));
				member.setMember_type_id(rs.getInt("member_type_id"));
				memberList.add(member);
			}
			table.setModel(model = new MemberModel(memberList, columnList));
			table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAdminmain().getDbManager().close(pstmt, rs);
		}
		
	}
	public void getMemberList() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> memberList = new ArrayList<Member>();
		ArrayList<String> columnList = new  ArrayList<String>();
		String sql = "select member_idx, member_id, password, name, nickname, to_char(regdate, 'yyyy/mm/dd') as regdate, email, member_type_id from member";
		
		try {
			pstmt = getAdminmain().getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columnTotal = meta.getColumnCount();
			for(int i=1;i<=columnTotal;i++) {
				columnList.add(meta.getColumnName(i));
				System.out.println(meta.getColumnName(i));
			}
			
			while(rs.next()) {
				Member member = new Member();
				member.setMember_idx(rs.getInt("member_idx"));
				member.setMember_id(rs.getString("member_id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setNickname(rs.getString("nickname"));
				member.setRegdate(rs.getString("regdate"));
				member.setEmail(rs.getString("email"));
				member.setMember_type_id(rs.getInt("member_type_id"));
				memberList.add(member);
			}
			table.setModel(model = new MemberModel(memberList, columnList));
			table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAdminmain().getDbManager().close(pstmt, rs);
		}
		
		
		
	}
	
}
