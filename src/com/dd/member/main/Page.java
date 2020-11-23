package com.dd.member.main;

import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JPanel;

public class Page extends JPanel{
	AppMain appMain;
	
	public Page(AppMain appMain) {
		this.appMain = appMain;
		this.setPreferredSize(new Dimension(appMain.WIDTH, appMain.HEIGHT-100));
	}
	
	public AppMain getAppMain() {
		return appMain;
	}
}
