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

public class CreateAdminAccount extends JFrame {

	private static final long serialVersionUID = -7246663854907748485L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 340;
	
	private PolarBear polarBear;
	
	private JTextField userName;
	private JPasswordField userPassword;
	private JPasswordField userPasswordTwo;
	private JTextArea userDescr;
	
	private JLabel hintLabel;
	
	public CreateAdminAccount(PolarBear polarBear) {
		
		this.polarBear = polarBear;
		
		setTitle("创建管理员");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		GuiTools.revisionSize(null, this, WIDTH, HEIGHT);
		
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		
		initPanel();
		
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

	private JPanel newContentPanel() {
		
		FocusEventHandle focusEventHandle = new FocusEventHandle();
		
		JPanel contentPanel = new JPanel(null);
		contentPanel.setBounds(10, 20, WIDTH - 26, HEIGHT - 85);
		contentPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "管理员-创建"));
		
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
		
		userDescr = new JTextArea("#随便说些感言呗...");
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
	
	private User getCreateUser() {
		
		String userName = this.userName.getText();
		String password = new String(userPassword.getPassword());
		String passwordTwo = new String(userPasswordTwo.getPassword());
		String descr = this.userDescr.getText();
		
		if (!verify(userName, "创建的用户名不能为null!",
				6, "用户名长度不能小于6个字符!", 30, "用户名长度不能大于30个字符!")
				|| !verify(password, "密码不能为null!", 
						6, "密码长度不能小于6个字符!", 30, "密码长度不能大于30个字符!")
				|| !verify(passwordTwo, "确认密码不能为null!", 0, null, 0, null)
				|| !verify(descr, "创建的用户描述不能为null!", 0, null, 0, null)) {
			return null;
		}
		
		if (!passwordTwo.equals(password)) {
			hintLabel.setText("确认密码与密码不一致!");
			return null;
		}
		
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		user.setDescr(descr);
		user.setCreateTime(Tools.DATA_FORMAT.format(System.currentTimeMillis()));
		
		return user;
	}
	
	class ActionEventHandle implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String action = e.getActionCommand();
			
			if ("OK".equals(action)) {
				User user = getCreateUser();
				if (user != null) {
					if (polarBear.createAdminUser(user)) {
						ManagerAccount managerAccount = new ManagerAccount(polarBear);
						managerAccount.setVisible(true);
						CreateAdminAccount.this.dispose();
						return ;
					}
					hintLabel.setText("创建管理员失败!");
				}
			} else if ("Cancel".equals(action)) {
				CreateAdminAccount.this.dispose();
			}
		}
	}
	
	class FocusEventHandle extends FocusAdapter {

		@Override
		public void focusLost(FocusEvent e) {
			super.focusLost(e);
			
			Component component = e.getComponent();
			
			if (userName.equals(component)) {
				verify(userName.getText(), "创建的用户名不能为null!",
						6, "用户名长度不能小于6个字符!", 30, "用户名长度不能大于30个字符!");
			} else if (userPassword.equals(component)) {
				verify(new String(userPassword.getPassword()), "密码不能为null!", 
						6, "密码长度不能小于6个字符!", 30, "密码长度不能大于30个字符!");
			} else if (userPasswordTwo.equals(component)) {
				String passwordTwo = new String(userPasswordTwo.getPassword());
				if (verify(passwordTwo, "确认密码不能为null!", 0, null, 0, null)) {
					String password = new String(userPassword.getPassword());
					if (!passwordTwo.equals(password)) {
						hintLabel.setText("确认密码与密码不一致!");
					}
				}
			} else if (userDescr.equals(component)) {
				verify(userDescr.getText(), "创建的用户描述不能为null!", 0, null, 0, null);
			}
		}
	}
}
