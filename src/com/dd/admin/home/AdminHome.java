package com.dd.admin.home;

import java.awt.Color;

import javax.swing.JPanel;

import com.dd.admin.main.Adminmain;
import com.dd.admin.main.Page;

public class AdminHome extends Page{
	JPanel p_navi, p_content;
	
	public AdminHome(Adminmain adminmain) {
		super(adminmain);
		p_navi = new JPanel();
		p_content = new JPanel();
	}
	
}
