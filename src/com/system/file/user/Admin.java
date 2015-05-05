package com.system.file.user;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.system.storage.Directory;
import com.system.storage.system;

/*
 * @author Anas 
 * @version 1.0
 * @since 2-5-2105
 * This class is Admin of the system ,just one instance is allowed 
 */
public class Admin extends User {
	private static Admin admin;
	static final String DEFAUL_TPRIVACY = "00";
	private HashMap<String, User> users;
	//private HashMap<String, String> privacytables;
	private HashMap<String, Integer> accessTypes;

	private Admin() {
		super("Admin", "123");
		users = new HashMap<>();
		//privacytables = new HashMap<>();
		accessTypes = new HashMap<>();
		accessTypes.put("Create", 0);
		accessTypes.put("Delete", 1);
	}

	public static Admin getInstance() {
		if (admin == null) {
			admin = new Admin();
		}
		return admin;
	}

	public void createUser(String name, String pass) {
		if (users.get(name) == null) {
			User user = new NormalUser(name, pass);
			users.put(name, user);
			//privacytables.put(name, DEFAUL_TPRIVACY);
		}
	}

	public User getUser(String name) {
		return users.get(name);
	}

	public User deleteUser(String name) {
		return users.remove(name);
	}

	public boolean getPermission(system sys,String dir,User user, String permissionType) {
		if(user instanceof Admin)
			return true;
		String[]paths=dir.trim().split("/");
		Directory dire=sys.getDire(sys.getRoot(), paths, 0);
		if (dire != null&&accessTypes.get(permissionType)!=null) {
			String temp=dire.getPrivacy(user.getName());
			if(temp==null)
				return false;
			if (temp.charAt(accessTypes.get(permissionType))=='1') {
				return true;
			}
		}
		return false;
	}

	public boolean changePrivacy(User user,system sys,String path, String newPrivacy) {
		String paths[]=path.trim().split("/");
		Directory dir=sys.getDire(sys.getRoot(), paths, 0);
		if(dir!=null)
		{
			dir.setPrivacy(user, newPrivacy);
			return true;
		}
		else
			return false;
	}

	public String[] getAllUsers() {
		String[] temp = new String[users.size()];
		int i = 0;
		for (String string : users.keySet()) {
			temp[i] = string;
			++i;
		}
		return temp;
	}

}