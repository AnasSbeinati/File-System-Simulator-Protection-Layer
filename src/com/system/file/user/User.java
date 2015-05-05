package com.system.file.user;

public abstract class User {
	String name;
	String pass;
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public User(String name,String pass) {
		super();
		this.name = name;
		this.pass=pass;
	}
	public User() {}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
