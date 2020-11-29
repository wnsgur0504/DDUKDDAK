package com.dd.admin.main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Page extends JPanel{
	Adminmain adminmain;
	public Page(Adminmain adminmain) {
		this.adminmain = adminmain;
		this.setPreferredSize(new Dimension(adminmain.WIDTH, adminmain.HEIGHT-100));
	}
	
	public Adminmain getAdminmain() {
		return adminmain;
	}
}
