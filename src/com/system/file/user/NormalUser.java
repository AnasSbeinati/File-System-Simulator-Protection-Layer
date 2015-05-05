package com.system.file.user;

class NormalUser extends User {
	String name;
	String privacy;
	String pass;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public NormalUser(String name, String pass) {
		super();
		this.name = name;
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
