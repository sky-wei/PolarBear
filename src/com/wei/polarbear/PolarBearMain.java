package com.wei.polarbear;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.wei.polarbear.bean.OrdinaryUser;
import com.wei.polarbear.bean.UiProperty;
import com.wei.polarbear.bean.User;
import com.wei.polarbear.db.PolarBearDB;
import com.wei.polarbear.gui.CreateAdminAccount;
import com.wei.polarbear.gui.GuiTools;
import com.wei.polarbear.gui.LoginAdminAccount;
import com.wei.polarbear.interfaces.PolarBear;
import com.wei.polarbear.tools.Tools;
import com.wei.polarbear.tools.UiPropertyTools;

public class PolarBearMain implements PolarBear {

	public static final String THEME = "./res/Theme.property";
	public static final String VERSION = "1.0(Beta)";
	
	private UiPropertyTools uiPropertyTools;
	private PolarBearDB polarBearDB;
	
	private AdminUserHandle adminUserHandle;
	private OrdinaryUserHandle ordinaryUserHandle;
	
	private User adminUser;
	
	public PolarBearMain() {
		
		initApplication();
		
		List<User> users = polarBearDB.queryAdminUsers();
		
//		ManagerAccount managerAccount = new ManagerAccount(this);
//		managerAccount.setVisible(true);
		
//		adminUser = new User(1);
//		adminUser.setName("jingcai.wei");
//		adminUser.setPassword("jingcai1314.wei");
//		adminUser.setCreateTime("2013-05-04 21:21:53");
//		adminUser.setDescr("#微笑的人生更精彩......");
//		
//		initLoginInfo();
		
		if (users == null || users.isEmpty()) {
			// 需要创建管理用户状态
			CreateAdminAccount createAdminAccount = new CreateAdminAccount(this);
			createAdminAccount.setVisible(true);
			return ;
		}
		
		// 直接进行登录用户状态
		LoginAdminAccount loginAdminAccount = new LoginAdminAccount(this);
		loginAdminAccount.setVisible(true);
	}
	
	/**
	 * 初始化应用程序相关信息
	 */
	private void initApplication() {
		
		uiPropertyTools = new UiPropertyTools();
		polarBearDB = new PolarBearDB();
		adminUserHandle = new AdminUserHandle(polarBearDB);
		
		File propertyFile = new File(THEME);
		if (!propertyFile.isFile()) {
			uiPropertyTools.saveUiProperty(propertyFile, new UiProperty());
		}
		
		GuiTools.setGuiTheme(THEME);
	}
	
	/**
	 * 初始化登录后的用户信息
	 */
	private void initLoginInfo() {
		
		ordinaryUserHandle = new OrdinaryUserHandle(adminUser, polarBearDB);
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				new PolarBearMain();
			}
		});
	}

	@Override
	public boolean createAdminUser(User adminUser) {
		
		if (adminUserHandle.addAdminUser(adminUser)) {
			
			this.adminUser = adminUserHandle.getAdminUser();
			
			initLoginInfo();
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean loginAdminUser(User adminUser) {
		
		if (adminUserHandle.loginAdminUser(adminUser)) {
			
			this.adminUser = adminUserHandle.getAdminUser();
			
			initLoginInfo();
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean updateAdminUser(User adminUser) {
		
		List<OrdinaryUser> ordinaryUsers = ordinaryUserHandle.getAllOrdinaryUser();
		
		if (ordinaryUsers != null && !ordinaryUsers.isEmpty()) {
			try {
				// 解密原来的数据
				List<OrdinaryUser> deOrdinaryUsers = ordinaryUserHandle.decryption(ordinaryUsers);
				
				OrdinaryUserHandle newUserHandle = new OrdinaryUserHandle(adminUser, polarBearDB);
				// 重新加密解密后数据保存到数据库中
				for (int i = 0; i < deOrdinaryUsers.size(); i++) {
					if (!newUserHandle.updateOrdinaryUser(deOrdinaryUsers.get(i))) {
						// 更新数据时出错了...需要做保护措施吗?
						return false;
					}
				}
				
				// 替换现有的
				this.ordinaryUserHandle = newUserHandle;
			} catch (Exception e) {
				e.printStackTrace();
				Tools.log.error("Exception!", e);
				return false;
			}
		}
		
		if (adminUserHandle.updateAdminUser(adminUser)) {
			this.adminUser = adminUser;
			return true;
		}
		
		return false;
	}

	@Override
	public User getAdminUser() {
		return adminUser;
	}

	@Override
	public List<OrdinaryUser> search(String type, String key) {
		
		return ordinaryUserHandle.search(type, key);
	}

	@Override
	public boolean createOrdinaryUser(OrdinaryUser ordinaryUser) {
		
		return ordinaryUserHandle.addOrdinaryUser(ordinaryUser);
	}

	@Override
	public boolean deleteOrdinaryUser(OrdinaryUser ordinaryUser) {
		
		return ordinaryUserHandle.deleteOrdinaryUser(ordinaryUser);
	}

	@Override
	public boolean updateOrdinaryUser(OrdinaryUser ordinaryUser) {
		
		return ordinaryUserHandle.updateOrdinaryUser(ordinaryUser);
	}

	@Override
	public OrdinaryUser decryption(OrdinaryUser ordinaryUser) throws Exception {
		
		return ordinaryUserHandle.decryption(ordinaryUser);
	}

	@Override
	public OrdinaryUser encrypt(OrdinaryUser ordinaryUser) throws Exception {
		
		return ordinaryUserHandle.encrypt(ordinaryUser);
	}

	@Override
	public UiProperty loadUiProperty() {
		
		return uiPropertyTools.loadUiProperty(new File(THEME));
	}

	@Override
	public boolean saveUiProperty(UiProperty uiProperty) {
		
		return uiPropertyTools.saveUiProperty(new File(THEME), uiProperty);
	}

	@Override
	public void refurbishTheme(JFrame frame) {
		
		GuiTools.setGuiTheme(THEME);
		SwingUtilities.updateComponentTreeUI(frame);
	}
}
