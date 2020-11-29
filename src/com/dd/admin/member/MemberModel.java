package com.dd.admin.member;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.dd.dto.Member;

public class MemberModel extends AbstractTableModel{
	ArrayList<Member> record = new ArrayList<Member>();
	ArrayList<String> column = new ArrayList<String>();
	
	public MemberModel(ArrayList<Member> record, ArrayList<String> column) {
		this.record = record;
		this.column = column;
	}
	
	public int getRowCount() {
		return record.size();
	}

	public int getColumnCount() {
		return column.size();
	}

	public String getColumnName(int col) {
		return this.column.get(col);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Member member = record.get(rowIndex);
		String obj=null;
		if(columnIndex==0) {
			obj = Integer.toString(member.getMember_idx());
		}else if(columnIndex==1) {
			obj = member.getMember_id();
		}else if(columnIndex==2) {
			obj = member.getPassword();
		}else if(columnIndex==3) {
			obj = member.getName();
			
		}else if(columnIndex==4) {
			obj = member.getNickname();
		}else if(columnIndex==5) {
			obj = member.getRegdate();		
		}else if(columnIndex==6) {
			obj = member.getEmail();
		}else if(columnIndex==7) {
			obj = member.getMember_type_id()==1?"관리자":"일반";		
		}
		
		return obj;
	}
	
	public Member getSelectedMember(int row) {
		return record.get(row);
	} 

}
