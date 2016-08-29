package com.wei.polarbear.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class PolarBearMenu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 8733691563697940118L;

	private com.wei.polarbear.interfaces.PolarBearMenu polarBearMenu;
	
	public PolarBearMenu(com.wei.polarbear.interfaces.PolarBearMenu polarBearMenu) {
		
		this.polarBearMenu = polarBearMenu;
		
		initMenuBar();
	}
	
	private void initMenuBar() {
		
		JMenu menu = new JMenu("菜单(M)");
		menu.setMnemonic(KeyEvent.VK_M);
		add(menu);
		
		JMenuItem updatePassword = new JMenuItem("修改密码");
		updatePassword.setActionCommand("UpdatePassword");
		menu.add(updatePassword);
		updatePassword.addActionListener(this);
		
		menu.addSeparator();
		
		JMenuItem quit = new JMenuItem("退出");
		quit.setActionCommand("Quit");
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		menu.add(quit);
		quit.addActionListener(this);
		
		//JMenu function = new JMenu("功能(F)");
		//function.setMnemonic(KeyEvent.VK_F);
		//add(function);
		
		JMenu settings = new JMenu("设置(S)");
		settings.setMnemonic(KeyEvent.VK_S);
		add(settings);
		
		JMenuItem themeSettings = new JMenuItem("界面设置");
		themeSettings.setActionCommand("ThemeSettings");
		themeSettings.addActionListener(this);
		settings.add(themeSettings);
		
		JMenu help = new JMenu("帮助(H)");
		help.setMnemonic(KeyEvent.VK_H);
		add(help);
		
		JMenuItem about = new JMenuItem("关于");
		about.setActionCommand("About");
		about.addActionListener(this);
		help.add(about);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
		if ("UpdatePassword".equals(action)) {
			polarBearMenu.updateAdminPassword();
		} else if ("Quit".equals(action)) {
			polarBearMenu.exitApplication();
		} else if ("About".equals(action)) {
			polarBearMenu.aboutApplication();
		} else if ("ThemeSettings".equals(action)) {
			polarBearMenu.themeSettings();
		}
	}
}
