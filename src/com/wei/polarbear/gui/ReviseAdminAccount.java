package com.wei.polarbear.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.wei.polarbear.bean.User;
import com.wei.polarbear.tools.Tools;

public class ReviseAdminAccount extends JDialog {

	private static final long serialVersionUID = 6109570022168274280L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 340;
	
	private JPasswordField originalPassword;
	private JPasswordField userPassword;
	private JPasswordField userPasswordTwo;
	private JTextArea userDescr;
	
	private JLabel hintLabel;
	
	private User original;
	private User adminUser;
	private boolean cancel;
	
	public ReviseAdminAccount(JFrame frame, User original) {
		
		super(frame, true);
		
		this.original = original;
		this.cancel = true;
		
		setTitle("修改管理员信息");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		setResizable(false);
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		
		GuiTools.revisionSize(frame, this, WIDTH, HEIGHT);
		
		initPanel();
		setContent();
		
		pack();
	}
	
	private void initPanel() {
		
		ActionEventHandle actionEventHandle = new ActionEventHandle();
		
		setLayout(null);
		
		add(newContentPanel());
		
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
	
	private void setContent() {
		
		if (original.getDescr() != null) {
			
			userDescr.setText(original.getDescr());
		}
	}

	private JPanel newContentPanel() {
		
		FocusEventHandle focusEventHandle = new FocusEventHandle();
		
		JPanel contentPanel = new JPanel(null);
		contentPanel.setBounds(10, 20, WIDTH - 26, HEIGHT - 85);
		contentPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "管理员-修改"));
		
		JLabel originalLable = new JLabel("原密码:");
		originalLable.setBounds(30, 35, 60, 25);
		contentPanel.add(originalLable);
		
		originalPassword = new JPasswordField();
		originalPassword.setBounds(100, 35, 240, 25);
		originalPassword.addFocusListener(focusEventHandle);
		contentPanel.add(originalPassword);
		
		JLabel userPasswordLable = new JLabel("新密码:");
		userPasswordLable.setBounds(30, 75, 60, 25);
		contentPanel.add(userPasswordLable);
		
		userPassword = new JPasswordField();
		userPassword.setBounds(100, 75, 240, 25);
		userPassword.addFocusListener(focusEventHandle);
		contentPanel.add(userPassword);
		
		JLabel userPasswordTwoLable = new JLabel("确认密码:");
		userPasswordTwoLable.setBounds(30, 115, 60, 25);
		contentPanel.add(userPasswordTwoLable);
		
		userPasswordTwo = new JPasswordField();
		userPasswordTwo.setBounds(100, 115, 240, 25);
		userPasswordTwo.addFocusListener(focusEventHandle);
		contentPanel.add(userPasswordTwo);
		
		JLabel descrLable = new JLabel("描述:");
		descrLable.setBounds(30, 155, 60, 25);
		contentPanel.add(descrLable);
		
		userDescr = new JTextArea("#随便说些感言呗!会更新到用户信息中...");
		userDescr.setBounds(100, 155, 240, 70);
		userDescr.addFocusListener(focusEventHandle);
		JScrollPane scrollPane = new JScrollPane(userDescr);
		scrollPane.setBounds(100, 155, 240, 70);
		contentPanel.add(scrollPane);
		
		return contentPanel;
	}
	
	/**
	 * 验证指定的字符串是否为null,是否小于最小长度,是否大于最大长度
	 * @param value 验证的字符串(默认去除前后空格)
	 * @param hintInfo 为null时提示的信息
	 * @param min 字符串最小长度(可等于最小长度)
	 * @param hintInfo1 小于最小长度时的提示字符串
	 * @param max 字符串最大长度(可等于最大长度)
	 * @param hintInfo2 大于最大长度时的提示字符串
	 * @return true:验证通过,false:验证失败
	 */
	private boolean verify(String value, String hintInfo, int min, String hintInfo1, int max, String hintInfo2) {
		
		if (value == null
				|| value.trim().length() <= 0) {
			hintLabel.setText(hintInfo);
			return false;
		}
		
		if (min > 0
				&& value.trim().length() < min) {
			hintLabel.setText(hintInfo1);
			return false;
		}
		
		if (max > 0
				&& value.trim().length() > max) {
			hintLabel.setText(hintInfo2);
			return false;
		}
		
		hintLabel.setText("");
		
		return true;
	}
	
	public User getAdminUser() {
		return adminUser;
	}

	public boolean isCancel() {
		return cancel;
	}

	private User getReviseUser() {
		
		String originalPassword = new String(this.originalPassword.getPassword());
		String password = new String(userPassword.getPassword());
		String passwordTwo = new String(userPasswordTwo.getPassword());
		String descr = this.userDescr.getText();
		
		if (!verify(originalPassword, "原密码不能为null!",
				6, "原密码长度不能小于6个字符!", 30, "原密码长度不能大于30个字符!")
				|| !verify(password, "新密码不能为null!", 
						6, "新密码长度不能小于6个字符!", 30, "新密码长度不能大于30个字符!")
				|| !verify(passwordTwo, "确认密码不能为null!", 0, null, 0, null)
				|| !verify(descr, "更新的用户描述不能为null!", 0, null, 0, null)) {
			return null;
		}
		
		if (!passwordTwo.equals(password)) {
			hintLabel.setText("确认密码与密码不一致!");
			return null;
		}
		
		if (!originalPassword.equals(original.getPassword())) {
			hintLabel.setText("原密码不正确,无法使用新密码!");
			return null;
		}
		
		User reviseUser = original.clone();
		reviseUser.setPassword(password);
		reviseUser.setDescr(descr);
		
		return reviseUser;
	}
	
	class ActionEventHandle implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String action = e.getActionCommand();
			
			if ("OK".equals(action)) {
				User adminUser = getReviseUser();
				if (adminUser != null) {
					ReviseAdminAccount.this.adminUser = adminUser;
					ReviseAdminAccount.this.cancel = false;
					ReviseAdminAccount.this.dispose();
				}
			} else if ("Cancel".equals(action)) {
				ReviseAdminAccount.this.dispose();
			}
		}
	}
	
	class FocusEventHandle extends FocusAdapter {

		@Override
		public void focusLost(FocusEvent e) {
			super.focusLost(e);
			
			Component component = e.getComponent();
			
			if (originalPassword.equals(component)) {
				String password = new String(originalPassword.getPassword());
				verify(password, "原密码不能为null!",
						6, "原密码长度不能小于6个字符!", 30, "原密码长度不能大于30个字符!");
			} else if (userPassword.equals(component)) {
				verify(new String(userPassword.getPassword()), "新密码不能为null!", 
						6, "新密码长度不能小于6个字符!", 30, "新密码长度不能大于30个字符!");
			} else if (userPasswordTwo.equals(component)) {
				String passwordTwo = new String(userPasswordTwo.getPassword());
				if (verify(passwordTwo, "确认密码不能为null!", 0, null, 0, null)) {
					String password = new String(userPassword.getPassword());
					if (!passwordTwo.equals(password)) {
						hintLabel.setText("确认密码与密码不一致!");
					}
				}
			} else if (userDescr.equals(component)) {
				verify(userDescr.getText(), "更新的用户描述不能为null!", 0, null, 0, null);
			}
		}
	}
}
