package com.system.storage;

import java.util.ArrayList;
import java.util.HashMap;

import com.system.file.user.User;

public class Directory {
	String name;
	HashMap<String, String> users;
	public ArrayList<File1> files;

	public void setFiles(ArrayList<File1> files) {
		this.files = files;
	}

	public ArrayList<Directory> subDirectory;
	boolean deleted = false;

	public Directory() {
		super();
		users = new HashMap<>();
		// TODO Auto-generated constructor stub
	}

	public Directory(String name) {
		this.name = name;
		files = new ArrayList<>();
		subDirectory = new ArrayList<>();
		users = new HashMap<>();
	}

	public void setPrivacy(User user, String privacy) {
		users.put(user.getName(), privacy);
		notifyAllsons( user, privacy);
	}

	public String getPrivacy(String user) {
		return users.get(user);
	}
	private void notifyAllsons( User user, String privacy) {
		for (Directory dire : subDirectory) {
			dire.setPrivacy(user, privacy);
		}
	}

	public void printDirectoryStructure(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print(" ");
		}
		if (!this.deleted) {
			System.out.print("<" + name + ">");
			System.out.println();
			for (File1 file : files) {
				for (int i = 0; i < level + 5; i++) {
					System.out.print(" ");
				}
				System.out.println(file.name
						+ (file.deleted ? " is deleted" : ""));
			}
			for (int i = 0; i < subDirectory.size(); i++) {
				subDirectory.get(i).printDirectoryStructure(level + 6);
			}
		} else
			System.out.print("<" + name + "> is deleted");
	}
}
