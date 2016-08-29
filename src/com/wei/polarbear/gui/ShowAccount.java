package com.wei.polarbear.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.wei.polarbear.bean.OrdinaryUser;
import com.wei.polarbear.tools.Tools;

public class ShowAccount extends JDialog implements ActionListener {

	private static final long serialVersionUID = 3744485384163494001L;
	
	public static final int WIDTH = 350;
	public static final int HEIGHT = 250;
	
	public ShowAccount(JFrame frame, OrdinaryUser ordinaryUser) {
		
		super(frame, true);
		
		setTitle("用户信息");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		setResizable(false);
		
		GuiTools.revisionSize(frame, this, WIDTH, HEIGHT);
		
		initPanel(ordinaryUser);
		
		pack();
	}
	
	private void initPanel(OrdinaryUser ordinaryUser) {
		
		setLayout(null);
		
		JTextArea textArea = new JTextArea(getShowInfo(ordinaryUser));
		textArea.setBackground(this.getBackground());
		textArea.setEditable(false);
		textArea.setBounds(20, 20, 305, 150);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(20, 20, 305, 150);
		
		add(scrollPane);
		
		JButton close = new JButton("确定");
		close.setActionCommand("Close");
		close.addActionListener(this);
		close.setBounds(143, 185, 65, 25);
		
		add(close);
	}
	
	private String getShowInfo(OrdinaryUser ordinaryUser) {
		
		if (ordinaryUser == null) {
			return "信息为空!异常情况...";
		}
		
		StringBuilder value = new StringBuilder();
		
		value.append("ID: " + ordinaryUser.getId() + "\n");
		value.append("AdminID: " + ordinaryUser.getAdminID() + "\n");
		value.append("用户名: " + ordinaryUser.getName() + "\n");
		value.append("密码: " + ordinaryUser.getPassword() + "\n");
		value.append("创建时间: " + ordinaryUser.getCreateTime() + "\n");
		value.append("描述: " + ordinaryUser.getDescr() + "\n");
		
		return value.toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
		if ("Close".equals(action)) {
			
			ShowAccount.this.dispose();
		}
	}
}
