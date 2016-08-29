package com.wei.polarbear.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.wei.polarbear.bean.User;
import com.wei.polarbear.interfaces.PolarBear;
import com.wei.polarbear.tools.Tools;

public class LoginAdminAccount extends JFrame {

	private static final long serialVersionUID = -1016199983621602843L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	
	private PolarBear polarBear;
	
	private JTextField userName;
	private JPasswordField userPassword;
	private JTextArea userDescr;
	
	private JLabel hintLabel;
	
	public LoginAdminAccount(PolarBear polarBear) {
		
		this.polarBear = polarBear;
		
		setTitle("管理员登录");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		GuiTools.revisionSize(null, this, WIDTH, HEIGHT);
		
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		
		initPanel();
		
		pack();
	}
	
	private void initPanel() {
		
		setLayout(null);
		
		add(newContentPanel());
		
		ActionEventHandle actionEventHandle = new ActionEventHandle();
		
		hintLabel = new JLabel();
		hintLabel.setBounds(WIDTH - 385, HEIGHT - 58, 200, 25);
		hintLabel.setForeground(Color.RED);
		add(hintLabel);
		
		JButton okButton = new JButton("确定");
		okButton.setActionCommand("OK");
		okButton.setBounds(WIDTH - 160, HEIGHT - 59, 65, 25);
		okButton.addActionListener(actionEventHandle);
		add(okButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.setActionCommand("Cancel");
		cancelButton.setBounds(WIDTH - 83, HEIGHT - 59, 65, 25);
		cancelButton.addActionListener(actionEventHandle);
		add(cancelButton);
	}
	
	private JPanel newContentPanel() {
		
		FocusEventHandle focusEventHandle = new FocusEventHandle();
		
		JPanel contentPanel = new JPanel(null);
		contentPanel.setBounds(10, 20, WIDTH - 26, HEIGHT - 85);
		contentPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "管理员-登录"));
		
		JLabel userNameLable = new JLabel("用户名:");
		userNameLable.setBounds(30, 35, 60, 25);
		contentPanel.add(userNameLable);
		
		userName = new JTextField();
		userName.setBounds(100, 35, 240, 25);
		userName.addFocusListener(focusEventHandle);
		contentPanel.add(userName);
		
		JLabel userPasswordLable = new JLabel("密码:");
		userPasswordLable.setBounds(30, 75, 60, 25);
		contentPanel.add(userPasswordLable);
		
		userPassword = new JPasswordField();
		userPassword.setBounds(100, 75, 240, 25);
		userPassword.addFocusListener(focusEventHandle);
		userPassword.addKeyListener(new KeyEventHandle());
		contentPanel.add(userPassword);
		
		JLabel descrLable = new JLabel("留言:");
		descrLable.setBounds(30, 115, 60, 25);
		contentPanel.add(descrLable);
		
		userDescr = new JTextArea("#或许是来打酱油的...");
		userDescr.setBounds(100, 115, 240, 70);
		JScrollPane scrollPane = new JScrollPane(userDescr);
		scrollPane.setBounds(100, 115, 240, 70);
		contentPanel.add(scrollPane);
		
		return contentPanel;
	}
	
	/**
	 * 验证指定的字符串是否为null,是否小于最小长度,是否大于最大长度
	 * @param value 验证的字符串(默认去除前后空格)
	 * @param hintInfo 为null时提示的信息
	 */
	private boolean verify(String value, String hintInfo) {
		
		if (value == null
				|| value.trim().length() <= 0) {
			hintLabel.setText(hintInfo);
			return false;
		}
		
		hintLabel.setText("");
		
		return true;
	}
	
	private User getLoginUser() {
		
		String userName = this.userName.getText();
		String password = new String(userPassword.getPassword());
		String userDescr = this.userDescr.getText();
		
		if (!verify(userName, "用户名能为null!")
				|| !verify(password, "密码能为null!")) {
			return null;
		}
		
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		user.setDescr(userDescr);
		
		return user;
	}
	
	private void login() {
		
		User user = getLoginUser();
		if (user != null) {
			if (polarBear.loginAdminUser(user)) {
				ManagerAccount managerAccount = new ManagerAccount(polarBear);
				managerAccount.setVisible(true);
				LoginAdminAccount.this.dispose();
				return ;
			}
			hintLabel.setText("名称或密码不正确!请重新输入...");
		}
	}

	class ActionEventHandle implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String action = e.getActionCommand();
			
			if ("OK".equals(action)) {
				LoginAdminAccount.this.login();
			} else if ("Cancel".equals(action)) {
				LoginAdminAccount.this.dispose();
			}
		}
	}
	
	class FocusEventHandle extends FocusAdapter {

		@Override
		public void focusLost(FocusEvent e) {
			super.focusLost(e);
			
			Component component = e.getComponent();
			
			if (userName.equals(component)) {
				verify(userName.getText(), "用户名能为null!");
			} else if (userPassword.equals(component)) {
				verify(new String(userPassword.getPassword()), "密码能为null!");
			}
		}
	}
	
	class KeyEventHandle extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				LoginAdminAccount.this.login();
			}
		}
	}
}
