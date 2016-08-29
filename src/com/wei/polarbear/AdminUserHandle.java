package com.wei.polarbear;

import com.wei.polarbear.bean.User;
import com.wei.polarbear.db.PolarBearDB;
import com.wei.polarbear.tools.Tools;

public class AdminUserHandle {
	
	private PolarBearDB polarBearDB;
	private User adminUser;
	
	public AdminUserHandle(PolarBearDB polarBearDB) {
		this.polarBearDB = polarBearDB;
	}
	
	/**
	 * 向数据库中添加一个管理员用户信息,
	 * @param adminUser 添加管理员信息(信息未加)
	 * @return 建成功返回true,否则返回false
	 */
	public boolean addAdminUser(User adminUser) {
		
		if (adminUser == null) return false;
		
		// 加密用户信息
		User enUser = encrypt(adminUser);
		// 向数据库中添加数据
		int id = polarBearDB.insertAdminUser(enUser);
		
		if (id > 0) {
			
			User newAdminUser = adminUser.clone();
			newAdminUser.setId(id);
			
			this.adminUser = newAdminUser;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 登录管理员用户,
	 * @param adminUser 登录的管理员用户信息(信息未加密)
	 * @return 如果成功返回true,否则返回false
	 */
	public boolean loginAdminUser(User adminUser) {
		
		if (adminUser == null) return false;
		
		User loginUser = polarBearDB.queryAdminUser(adminUser.getName());
		
		if (loginUser != null) {
			
			// 加密用户信息
			User enUser = encrypt(adminUser);
			
			if (loginUser.getPassword()
					.equals(enUser.getPassword())) {
				
				// 还原为明文
				loginUser.setPassword(adminUser.getPassword());
				
				this.adminUser = loginUser;
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 更新当前的管理员信息到数据库中
	 * @param adminUser 管理员信息
	 * @return true:更新成功,false:更新失败
	 */
	public boolean updateAdminUser(User adminUser) {
		
		if (adminUser == null) return false;
		
		// 加密用户信息
		User enUser = encrypt(adminUser);
		
		// 把用户信息更新到数据库中
		return polarBearDB.updateAdminUser(enUser);
	}
	
	/**
	 * 加密用户信息,并返回一个加密好的用户信息
	 * @param adminUser 需要加密的用户信息
	 * @return 加密好的用户信息
	 */
	public User encrypt(User adminUser) {
		
		if (adminUser == null) {
			return null;
		}
		
		User enUser = adminUser.clone();
		// 目前只针对密码进行加密
		enUser.setPassword(Tools.md5Encryption(adminUser.getPassword()));
		
		return enUser;
	}

	/**
	 * 返回当前用户的详细信息
	 * @return User用户信息
	 */
	public User getAdminUser() {
		return adminUser;
	}
}
