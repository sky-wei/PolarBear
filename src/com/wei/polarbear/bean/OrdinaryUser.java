package com.wei.polarbear.bean;

public class OrdinaryUser extends User {

	private int adminID;
	
	public OrdinaryUser() {
		this(-1);
	};
	
	public OrdinaryUser(int id) {
		super(id);
		adminID = -1;
	}

	public int getAdminID() {
		return adminID;
	}

	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}

	@Override
	public String toString() {
		return super.toString() + ", adminID: " + adminID;
	}

	public OrdinaryUser clone() {
		
		OrdinaryUser ordinaryUser = new OrdinaryUser(getId());
		ordinaryUser.setAdminID(adminID);
		ordinaryUser.setName(getName());
		ordinaryUser.setPassword(getPassword());
		ordinaryUser.setDescr(getDescr());
		ordinaryUser.setCreateTime(getCreateTime());
		
		return ordinaryUser;
	}
}
