package com.wei.polarbear;

import java.util.ArrayList;
import java.util.List;

import com.wei.polarbear.bean.OrdinaryUser;
import com.wei.polarbear.bean.User;
import com.wei.polarbear.db.PolarBearDB;
import com.wei.polarbear.tools.DESedeCoder;
import com.wei.polarbear.tools.Tools;

public class OrdinaryUserHandle {
	
	private User adminUser;
	private PolarBearDB polarBearDB;
	private DESedeCoder deSedeCoder;
	private OrdinaryUser ordinaryUser;
	
	public OrdinaryUserHandle(User adminUser, PolarBearDB polarBearDB) {
		this.adminUser = adminUser;
		this.polarBearDB = polarBearDB;
		
		deSedeCoder = new DESedeCoder();
	}
	
	public OrdinaryUser getOrdinaryUser() {
		return ordinaryUser;
	}

	/**
	 * 添加一个用户信息到数据库中
	 * @param ordinaryUser 用户信息
	 * @return true:添加成功,false:添加失败
	 */
	public boolean addOrdinaryUser(OrdinaryUser ordinaryUser) {
		
		if (ordinaryUser == null) return false;
		
		try {
			// 加密用户信息
			OrdinaryUser enOrdinaryUser = encrypt(ordinaryUser);
			// 设置管理的id
			enOrdinaryUser.setAdminID(adminUser.getId());
			
			if (polarBearDB.insertOrdinaryUser(enOrdinaryUser)) {
				
				this.ordinaryUser = ordinaryUser;
				
				return true;
			}
		} catch (Exception e) {
			Tools.log.error("加密用户失败!", e);
		}
		
		return false;
	}
	
	/**
	 * 更新用户信息到数据库中
	 * @param ordinaryUser 用户信息
	 * @return true:更新成功,false:更新失败
	 */
	public boolean updateOrdinaryUser(OrdinaryUser ordinaryUser) {
		
		if (ordinaryUser == null) return false;
		
		try {
			// 加密用户信息
			OrdinaryUser enOrdinaryUser = encrypt(ordinaryUser);
			
			if (polarBearDB.updateOrdinaryUser(enOrdinaryUser)) {
				
				// 更新成功了...
				this.ordinaryUser = ordinaryUser;
				
				return true;
			}
		} catch (Exception e) {
			Tools.log.error("加密用户失败!", e);
		}
		
		return false;
	}
	
	/**
	 * 从数据库中删除对应的用户信息
	 * @param ordinaryUser 用户信息
	 * @return true:删除成功,false:删除失败
	 */
	public boolean deleteOrdinaryUser(OrdinaryUser ordinaryUser) {
		
		return polarBearDB.deleteOrdinaryUser(ordinaryUser);
	}
	
	/**
	 * 对用户信息进行加密处理
	 * @param ordinaryUser 用户信息
	 * @return 加密后的用户信息
	 * @throws Exception
	 */
    public OrdinaryUser encrypt(OrdinaryUser ordinaryUser) throws Exception {
    	
    	if (ordinaryUser == null)	return null;
    	
    	OrdinaryUser enOrdinaryUser = ordinaryUser.clone();
    	enOrdinaryUser.setPassword(deSedeCoder.encrypt(ordinaryUser.getPassword(),
    			adminUser.getPassword() + ordinaryUser.getName()));
    	
    	return enOrdinaryUser;
    }
    
	/**
	 * 对用户信息进行加密处理
	 * @param ordinaryUsers 用户信息集
	 * @return 加密后的用户信息
	 * @throws Exception
	 */
    public List<OrdinaryUser> encrypt(List<OrdinaryUser> ordinaryUsers) throws Exception {
    	
    	if (ordinaryUsers == null)	return null;
    	
    	List<OrdinaryUser> enOrdinaryUsers = new ArrayList<OrdinaryUser>(ordinaryUsers.size());
    	
    	for (int i = 0; i < ordinaryUsers.size(); i++) {
    		
    		enOrdinaryUsers.add(encrypt(ordinaryUsers.get(i)));
    	}
    	
    	return enOrdinaryUsers;
    }
    
    /**
     * 对用户信息进行解密处理
     * @param ordinaryUser 用户信息
     * @return 解密后的用户信息
     * @throws Exception
     */
    public OrdinaryUser decryption(OrdinaryUser ordinaryUser) throws Exception {
    	
    	if (ordinaryUser == null)	return null;
    	
    	OrdinaryUser deOrdinaryUser = ordinaryUser.clone();
    	deOrdinaryUser.setPassword(deSedeCoder.decryption(ordinaryUser.getPassword(),
    			adminUser.getPassword() + ordinaryUser.getName()));
    	
    	return deOrdinaryUser;
    }
    
    /**
     * 对用户信息进行解密处理
     * @param ordinaryUsers 用户信息集
     * @return 解密后的用户信息
     * @throws Exception
     */
    public List<OrdinaryUser> decryption(List<OrdinaryUser> ordinaryUsers) throws Exception {
    	
    	if (ordinaryUsers == null)	return null;
    	
    	List<OrdinaryUser> deOrdinaryUsers = new ArrayList<OrdinaryUser>(ordinaryUsers.size());
    	
    	for (int i = 0; i < ordinaryUsers.size(); i++) {
    		
    		deOrdinaryUsers.add(decryption(ordinaryUsers.get(i)));
    	}
    	
    	return deOrdinaryUsers;
    }
    
    /**
     * 搜索数据库中指定用户信息,没有对用户信息进行解密操作
     * @param type 搜索类型
     * @param key 搜索的关键字
     * @return List<OrdinaryUser>搜索到的结果集
     */
    public List<OrdinaryUser> search(String type, String key) {
    	
    	if ("所有".equals(type)) {
    		return polarBearDB.queryOrdinaryUsers(adminUser, null, null);
    	} else if ("名称".equals(type)) {
    		if (key != null && key.trim().length() > 0) {
        		String parame = "and name like ?";
        		String[] parameValues = {"%" + key + "%"};
        		return polarBearDB.queryOrdinaryUsers(adminUser, parame, parameValues);
    		}
    	} else if ("描述".equals(type)) {
    		if (key != null && key.trim().length() > 0) {
        		String parame = "and descr like ?";
        		String[] parameValues = {"%" + key + "%"};
        		return polarBearDB.queryOrdinaryUsers(adminUser, parame, parameValues);
    		}
    	}
    	return null;
    }
    
    public List<OrdinaryUser> getAllOrdinaryUser() {
    	
    	return polarBearDB.queryOrdinaryUsers(adminUser, null, null);
    }
}
