package com.wei.polarbear.interfaces;

import java.util.List;

import javax.swing.JFrame;

import com.wei.polarbear.bean.OrdinaryUser;
import com.wei.polarbear.bean.UiProperty;
import com.wei.polarbear.bean.User;

public interface PolarBear {
	
	/**
	 * 创建管理员用户
	 * @param adminUser 管理员用户信息
	 * @return true:创建失败,false:创建成功
	 */
	boolean createAdminUser(User adminUser);
	
	/**
	 * 登录管理员用户
	 * @param adminUser 管理员用户信息
	 * @return true:登录成功,false:登录失败
	 */
	boolean loginAdminUser(User adminUser);
	
	boolean updateAdminUser(User adminUser);
	
	User getAdminUser();
	
	List<OrdinaryUser> search(String type, String key);
	
	boolean createOrdinaryUser(OrdinaryUser ordinaryUser);
	
	boolean deleteOrdinaryUser(OrdinaryUser ordinaryUser);
	
	boolean updateOrdinaryUser(OrdinaryUser ordinaryUser);
	
	OrdinaryUser decryption(OrdinaryUser ordinaryUser) throws Exception;
	
	OrdinaryUser encrypt(OrdinaryUser ordinaryUser) throws Exception;
	
	UiProperty loadUiProperty();
	
	boolean saveUiProperty(UiProperty uiProperty);
	
	void refurbishTheme(JFrame frame);
}
