package com.wei.polarbear.bean;

public class User {
	
	private int id;
	private String name;
	private String password;
	private String createTime;
	private String descr;
	
	public User() {
		this.id = -1;
	};
	
	public User(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public User clone() {
		
		User user = new User(id);
		user.setName(name);
		user.setPassword(password);
		user.setDescr(descr);
		user.setCreateTime(createTime);
		
		return user;
	}

	@Override
	public String toString() {
		return "id: " + id + ", name: " + name + ", password: " + password
				+ ", createTime: " + createTime + ", descr: " + descr;
	}
}
