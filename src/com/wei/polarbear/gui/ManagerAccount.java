package com.wei.polarbear.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.wei.polarbear.PolarBearMain;
import com.wei.polarbear.bean.OrdinaryUser;
import com.wei.polarbear.interfaces.PolarBear;
import com.wei.polarbear.interfaces.PolarBearMenu;
import com.wei.polarbear.tools.Tools;

public class ManagerAccount extends JFrame implements PolarBearMenu, ActionListener {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;
	
	private PolarBear polarBear;
	
	private ComboBoxModel<String> scopeModel;
	private JTextField searchText;
	
	private DefaultTableModel accountTableModel;
	private JTable accountTable;
	private JTextArea expandAccountInfo;
	
	private JButton createAccount;
	private JButton reviseAccount;
	private JButton deleteAccount;
	private JButton copyAccountInfo;
	private JButton showAccountInfo;
	private JButton importAccount;
	private JButton exportAccount;
	
	private List<OrdinaryUser> ordinaryUsers;

	public ManagerAccount(PolarBear polarBear) {
		this.polarBear = polarBear;
		
		setTitle("AccountManager" + PolarBearMain.VERSION + "  -  jingcai.wei");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		GuiTools.revisionSize(null, this, WIDTH, HEIGHT);
		
		setIconImage(Tools.getImage(new File("./res/icon.png")));
		
		initPanel();
		
		pack();
	}
	
	private void initPanel() {
		
		com.wei.polarbear.gui.PolarBearMenu polarBearMenu = new com.wei.polarbear.gui.PolarBearMenu(this);
		setJMenuBar(polarBearMenu);
		
		add(newSearchPanel(), BorderLayout.NORTH);
		
		add(newInfoPanel(), BorderLayout.CENTER);
		
		add(newFunPanel(), BorderLayout.EAST);
	}
	
	private JPanel newSearchPanel() {
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 2, 2));
		
		searchPanel.add(new JLabel("范围: "));
		
		scopeModel = new DefaultComboBoxModel<String>(new String[]{"所有", "名称", "描述"});
		JComboBox<String> scope = new JComboBox<String>(scopeModel);
		searchPanel.add(scope);
		
		searchPanel.add(Box.createHorizontalStrut(30));
		
		searchPanel.add(new JLabel("关键字: "));
		
		searchText = new JTextField(20);
		searchPanel.add(searchText);
		
		searchPanel.add(Box.createHorizontalStrut(5));
		
		JButton searchButton = new JButton("搜索");
		searchButton.setActionCommand("Search");
		searchButton.addActionListener(this);
		searchPanel.add(searchButton);
		
		return searchPanel;
	}
	
	private JPanel newInfoPanel() {
		
		JPanel infoPanel = new JPanel(new BorderLayout(0, 5));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 0));
		
		accountTableModel = new AccountTableModel();
		accountTableModel.addColumn("ID");
		accountTableModel.addColumn("用户名");
		accountTableModel.addColumn("密码");
		accountTableModel.addColumn("描述");
		accountTableModel.addColumn("创建时间");
		
		accountTable = new JTable(accountTableModel);
		accountTable.addMouseListener(new AccountMouseListener());
		//accountTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jScrollPane = new JScrollPane(accountTable);
		
		infoPanel.add(jScrollPane, BorderLayout.CENTER);
		
		expandAccountInfo = new JTextArea("\n\n\n\n\n");
		expandAccountInfo.setEditable(false);
		expandAccountInfo.setBackground(this.getBackground());
		JScrollPane expandScrollPane = new JScrollPane(expandAccountInfo);
		
		infoPanel.add(expandScrollPane, BorderLayout.SOUTH);
		
		return infoPanel;
	}
	
	private JPanel newFunPanel() {
		
		JPanel funPanel = new JPanel();
		funPanel.setLayout(new BoxLayout(funPanel, BoxLayout.Y_AXIS));
		funPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 5));
		
		funPanel.add(Box.createVerticalStrut(25));
		
		createAccount = new JButton("创建用户");
		createAccount.setPreferredSize(new Dimension(80, 25));
		createAccount.addActionListener(this);
		createAccount.setActionCommand("CreateAccount");
		funPanel.add(createAccount);
		funPanel.add(Box.createVerticalStrut(20));
		
		reviseAccount = new JButton("修改用户");
		reviseAccount.setPreferredSize(new Dimension(80, 25));
		reviseAccount.addActionListener(this);
		reviseAccount.setActionCommand("ReviseAccount");
		reviseAccount.setEnabled(false);
		funPanel.add(reviseAccount);
		funPanel.add(Box.createVerticalStrut(5));
		
		deleteAccount = new JButton("删除用户");
		deleteAccount.setPreferredSize(new Dimension(80, 25));
		deleteAccount.addActionListener(this);
		deleteAccount.setActionCommand("DeleteAccount");
		deleteAccount.setEnabled(false);
		funPanel.add(deleteAccount);
		funPanel.add(Box.createVerticalStrut(20));
		
		copyAccountInfo = new JButton("复制信息");
		copyAccountInfo.setPreferredSize(new Dimension(80, 25));
		copyAccountInfo.addActionListener(this);
		copyAccountInfo.setActionCommand("CopyAccountInfo");
		copyAccountInfo.setEnabled(false);
		funPanel.add(copyAccountInfo);
		funPanel.add(Box.createVerticalStrut(5));
		
		showAccountInfo = new JButton("显示信息");
		showAccountInfo.setPreferredSize(new Dimension(80, 25));
		showAccountInfo.addActionListener(this);
		showAccountInfo.setActionCommand("ShowAccountInfo");
		showAccountInfo.setEnabled(false);
		funPanel.add(showAccountInfo);
		funPanel.add(Box.createVerticalStrut(20));
		
		importAccount = new JButton("导入用户");
		importAccount.setPreferredSize(new Dimension(80, 25));
		importAccount.addActionListener(this);
		importAccount.setActionCommand("ImportAccount");
		importAccount.setEnabled(false);
		funPanel.add(importAccount);
		funPanel.add(Box.createVerticalStrut(5));
				
		
		exportAccount = new JButton("导出用户");
		exportAccount.setPreferredSize(new Dimension(80, 25));
		exportAccount.addActionListener(this);
		exportAccount.setActionCommand("ExportAccount");
		exportAccount.setEnabled(false);
		funPanel.add(exportAccount);
		funPanel.add(Box.createVerticalStrut(50));
		
		JButton exitApplication = new JButton("退出程序");
		exitApplication.setPreferredSize(new Dimension(80, 25));
		exitApplication.addActionListener(this);
		exitApplication.setActionCommand("ExitApplication");
		funPanel.add(exitApplication);
		
		return funPanel;
	}
	
	private void updateInfo(int index, OrdinaryUser ordinaryUser) {
		
		if (index >= 0 && ordinaryUser != null) {
			
			ordinaryUsers.set(index, ordinaryUser);
			
			updateRowContent(index, ordinaryUser);
			
			checkAccountTable(index);
		}
	}
	
	private void setContentInfo(List<OrdinaryUser> ordinaryUsers) {
		
		if (ordinaryUsers == null || ordinaryUsers.isEmpty()) {
			JOptionPane.showConfirmDialog(this, "没有搜索到相关用户信息!", "提示!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		
		cleanAllContent();
		expandAccountInfo.setText("");
		
		this.ordinaryUsers = ordinaryUsers;
		
		for (int i = 0; i < ordinaryUsers.size(); i++) {
			addRowContent(ordinaryUsers.get(i));
		}
	}
	
	private void addRowContent(OrdinaryUser ordinaryUser) {
		
		if (ordinaryUser == null)	return ;
		
		String[] values = {Integer.toString(ordinaryUser.getId()),
				Tools.concealBack(ordinaryUser.getName(), 4),
				Tools.concealFirst(ordinaryUser.getPassword(), 20),
				ordinaryUser.getDescr(), ordinaryUser.getCreateTime()};
		
		accountTableModel.addRow(values);
	}
	
	private void updateRowContent(int row, OrdinaryUser ordinaryUser) {
		
		if (row < 0 || row > accountTableModel.getRowCount()
				|| ordinaryUser == null) {
			return ;
		}
		
		String[] values = {Integer.toString(ordinaryUser.getId()),
				Tools.concealBack(ordinaryUser.getName(), 4), 
				Tools.concealFirst(ordinaryUser.getPassword(), 20) ,
				ordinaryUser.getDescr(), ordinaryUser.getCreateTime()};
		
		for (int i = 0; i < values.length; i++) {
			accountTableModel.setValueAt(values[i], row, i);
		}
	}
	
	private void cleanAllContent() {
		
		int rowCount = accountTableModel.getRowCount();
		
		for (int i = 0; i < rowCount; i++) {
			accountTableModel.removeRow(0);
		}
	}
	
	private void setButtonEnabled(boolean enabled) {
		
		reviseAccount.setEnabled(enabled);
		deleteAccount.setEnabled(enabled);
		
		copyAccountInfo.setEnabled(enabled);
		showAccountInfo.setEnabled(enabled);
		
//		importAccount.setEnabled(enabled);
//		exportAccount.setEnabled(enabled);
	}
	
	private void checkAccountTable(int nextSelect) {
		
		int selectRow = accountTable.getSelectedRow();
		
		if (selectRow != -1
				&& accountTable.isRowSelected(selectRow)) {
			setExpandAccountInfo(selectRow);
			setButtonEnabled(true);
			return ;
		}
		
		if (nextSelect >= 0 
				&& nextSelect < accountTable.getRowCount()) {
			if (!accountTable.isRowSelected(nextSelect)) {
				accountTable.setRowSelectionInterval(nextSelect, nextSelect);
			}
			setExpandAccountInfo(nextSelect);
			setButtonEnabled(true);
			return ;
		}
		
		setExpandAccountInfo(-1);
		setButtonEnabled(false);
	}
	
	private void setExpandAccountInfo(int index) {
		
		if (index < 0 || ordinaryUsers == null 
				|| ordinaryUsers.isEmpty()
				|| index >= ordinaryUsers.size()) {
			expandAccountInfo.setText("");
			return ;
		}
		
		OrdinaryUser ordinaryUser = ordinaryUsers.get(index);
		
		StringBuilder info = new StringBuilder();
		info.append("ID : " + ordinaryUser.getId() + "\n");
		info.append("用户名 : " + Tools.concealBack(ordinaryUser.getName(), 4) + "\n");
		info.append("密码 : " + Tools.concealFirst(ordinaryUser.getPassword(), 20) + "\n");
		info.append("描述 : " + ordinaryUser.getDescr() + "\n");
		info.append("创建时间: " + ordinaryUser.getCreateTime());
		
		expandAccountInfo.setText(info.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
		checkAccountTable(-1);
		
		if ("Search".equals(action)) {
			String selectItem = (String)scopeModel.getSelectedItem();
			setContentInfo(polarBear.search(selectItem, searchText.getText()));
		} else if ("CreateAccount".equals(action)) {
			CreateAccount createAccount = new CreateAccount(this);
			createAccount.setVisible(true);
			if (!createAccount.isCancel()) {
				if (polarBear.createOrdinaryUser(createAccount.getOrdinaryUser())) {
					JOptionPane.showConfirmDialog(this, "创建用户成功了!", "提示!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					return ;
				}
				JOptionPane.showConfirmDialog(this, "创建用户出错了,详情请查看日志!", "错误!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		} else if ("ReviseAccount".equals(action)) {
			int selectRow = accountTable.getSelectedRow();
			if (selectRow != -1) {
				try {
					OrdinaryUser reviseUser = ordinaryUsers.get(selectRow);
					ReviseAccount reviseAccount = new ReviseAccount(this, polarBear.decryption(reviseUser));
					reviseAccount.setVisible(true);
					if (!reviseAccount.isCancel()) {
						OrdinaryUser ordinaryUser = reviseAccount.getOrdinaryUser();
						if (polarBear.updateOrdinaryUser(ordinaryUser)) {
							updateInfo(selectRow, polarBear.encrypt(ordinaryUser));
							JOptionPane.showConfirmDialog(this, "修改用户信息成功了!", "提示!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
							return ;
						}
						JOptionPane.showConfirmDialog(this, "修改用户信息出错了,详情请查看日志!", "错误!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					Tools.log.error("Exception!", ex);
				}
			}
		} else if ("DeleteAccount".equals(action)) {
			int result = JOptionPane.showConfirmDialog(this, "你确定要删除当前用户信息!", "提示!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (result == 0) {
				int selectRow = accountTable.getSelectedRow();
				if (selectRow != -1) {
					if (polarBear.deleteOrdinaryUser(ordinaryUsers.get(selectRow))) {
						ordinaryUsers.remove(selectRow);
						accountTableModel.removeRow(selectRow);
						checkAccountTable(selectRow);
						JOptionPane.showConfirmDialog(this, "删除用户信息成功了!", "提示!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					JOptionPane.showConfirmDialog(this, "删除用户信息出错了,详情请查看日志!", "错误!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if ("CopyAccountInfo".equals(action)) {
			int selectRow = accountTable.getSelectedRow();
			if (selectRow != -1) {
				try {
					// 解密当前用户信息
					OrdinaryUser ordinaryUser = polarBear.decryption(ordinaryUsers.get(selectRow));
					
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Clipboard clipboard = toolkit.getSystemClipboard();
					
					String value = ordinaryUser.getName() + "\n" + ordinaryUser.getPassword();
					StringSelection data = new StringSelection(value);
					clipboard.setContents(data, null);
					
					JOptionPane.showConfirmDialog(this, "以成功复制用户信息到粘贴板了!", "提示!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					Tools.log.error("Exception!", ex);
				}
			}
		} else if ("ShowAccountInfo".equals(action)) {
			int selectRow = accountTable.getSelectedRow();
			if (selectRow != -1) {
				try {
					ShowAccount showAccount = new ShowAccount(this, polarBear.decryption(ordinaryUsers.get(selectRow)));
					showAccount.setVisible(true);
				} catch (Exception ex) {
					Tools.log.error("Exception!", ex);
				}
			}
		} else if ("ImportAccount".equals(action)) {
			
		} else if ("ExportAccount".equals(action)) {
			
		} else if ("ExitApplication".equals(action)) {
			exitApplication();
		}
		
		checkAccountTable(-1);
	}

	@Override
	public void exitApplication() {
		this.dispose();
	}

	@Override
	public void updateAdminPassword() {
		ReviseAdminAccount reviseAdminAccount = new ReviseAdminAccount(this, polarBear.getAdminUser());
		reviseAdminAccount.setVisible(true);
		
		if (!reviseAdminAccount.isCancel()) {
			if (polarBear.updateAdminUser(reviseAdminAccount.getAdminUser())) {
				cleanAllContent();
				checkAccountTable(-1);
				return ;
			}
			JOptionPane.showConfirmDialog(this, "修改管理员信息失败!,详情请查看日志!", "错误!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void aboutApplication() {
		AboutDialog aboutDialog = new AboutDialog(this);
		aboutDialog.setVisible(true);
	}

	@Override
	public void themeSettings() {
		UiSettings uiSettings = new UiSettings(this, polarBear.loadUiProperty());
		uiSettings.setVisible(true);
		
		if (!uiSettings.isCancel()) {
			if (polarBear.saveUiProperty(uiSettings.getUiProperty())) {
				polarBear.refurbishTheme(this);
			}
		}
	}

	class AccountTableModel extends DefaultTableModel {
		
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			
			return false;
		}
	}
	
	class AccountMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			if (e.getSource() == accountTable) {
				checkAccountTable(-1);
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			
			if (e.getSource() == accountTable) {
				checkAccountTable(-1);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			
			if (e.getSource() == accountTable) {
				checkAccountTable(-1);
			}
		}
	}
}
