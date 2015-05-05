package com.system.run;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Scanner;

import com.system.storage.*;
import com.system.file.*;
import com.system.file.user.Admin;
import com.system.file.user.User;

public class main {
	static system sys;

	public static void main(String[] args) {
		Admin admin = Admin.getInstance();
		User currentuser = boot(admin);
	}

	public static void Main(User user, Admin admin) {
		String command = "";
		while (!command.equals("back")) {
			command = new Scanner(System.in).nextLine();
			String str[] = command.trim().split(" ");
			switch (str[0]) {
			case "createfile":
				if (admin.getPermission(sys, str[1], user, "Create")) {
					sys.createFile(str[1], Integer.parseInt(str[2]));
					System.out.println("->file has been created");
				} else {
					System.out
							.println("You do not have the permission to do this action");
				}
				break;
			case "createfolder":
				if (admin.getPermission(sys, str[1], user, "Create")) {
					sys.createFolder(str[1]);
					System.out.println("->folder has been created");
				} else {
					System.out
							.println("You do not have the permission to do this action");
				}
				break;
			case "deletefolder":
				if (admin.getPermission(sys, str[1], user, "Delete")) {
					sys.deleteFolder(str[1]);
					System.out.println("->folder has been deleted");
				} else {
					System.out
							.println("You do not have the permission to do this action");
				}
				break;
			case "deletefile":
				if (admin.getPermission(sys, str[1], user, "Delete")) {
					sys.deleteFile(str[1]);
					System.out.println("->file has been deleted");
				} else {
					System.out
							.println("You do not have the permission to do this action");
				}
				break;
			case "status":
				sys.DisplayDiskStatus();
				break;
			case "structure":
				sys.DisplayDiskStructure();
				break;
			case "back":
				
				break;
			default:
				System.out.println("->No match command");
			}
		}
	}

	public static User boot(Admin admin) {
		User currentuser = Admin.getInstance();
		boolean b = true;
		String command = " ";
		while (!command.equals("back")) {
			command = new Scanner(System.in).nextLine();
			String str[] = command.trim().split(" ");
			switch (str[0]) {
			case "TellUser":
				if (currentuser instanceof Admin)
					System.out.println("->Admin");
				else
					System.out.println("->" + currentuser.getName());
				break;
			case "CreateUser":
				if (currentuser instanceof Admin)
					admin.createUser(str[1], str[2]);
				else
					System.out
							.println("->You have to log in as Admin to perform this action");
				break;
			case "DeleteUser":
				if (currentuser instanceof Admin)
					admin.deleteUser(str[1]);
				else
					System.out
							.println("->You have to log in as Admin to perform this action");
				break;
			case "Login":
				User currentuser1;
				if (str.length == 1) {
					currentuser = admin;
					System.out.println(currentuser.getName()
							+ " you'r logged in");
					systemLog(currentuser, admin);
					continue;
				}

				currentuser1 = admin.getUser(str[1]);
				if (currentuser1 != null) {
					if (currentuser1.getPass().equals(str[2])) {
						currentuser = currentuser1;
						System.out.println(currentuser.getName()
								+ " you'r logged in");
						systemLog(currentuser, admin);
					} else
						System.out.println("->name or pass is incorrect");
				} else {
					System.out.println("->name or pass is incorrect");
				}
				break;
			case "Show_All":
				System.out.print("avilable Users: 1-Admin(pass is 123) ");
				String[] users = admin.getAllUsers();
				int i = 2;
				for (String string : users) {
					System.out.print(i++ + "-" + string + " ");
				}
				System.out.println();
				break;
			case "Grant":
				if (currentuser instanceof Admin) {
					Parse parse = new Parse();
					try {
						User user = admin.getUser(str[1]);
						if (user != null) {
							admin.changePrivacy(user, sys, str[2], str[3]);
						} else {
							System.out.println("it's not a user");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else
					System.out
							.println("->You have to log in as Admin to perform this action");
				break;
			case "back":
				break;
			default:
				System.out.println("->No match command");
			}
		}
		return currentuser;
	}

	/*
	 * System.out.print("avilable Users 1-Admin(pass is 123) "); String[] users
	 * = admin.getAllUsers(); int i = 2; for (String string : users) {
	 * System.out.print(++i + "-" + string); } System.out.println(); String
	 * choice = new Scanner(System.in).nextLine(); userCommands(admin, choice);
	 * if (choice.equals("Admin")) { currentuser = admin; break; } else if
	 * (admin.getUser(choice) != null) { currentuser = admin.getUser(choice);
	 * System.out.println("Eneter pass"); String pass = new
	 * Scanner(System.in).nextLine(); if (currentuser.getPass().equals(pass)) {
	 * System.out.println("You'r logged in"); break; } else {
	 * System.out.println("Your pass is not correct"); } } else
	 * System.out.println("No such a user!"); }
	 */

	public static void systemLog(User user, Admin admin) {
		String lock = "1";
		String command = "";
		tech t;
		// system sys;
		Parse parse = new Parse();
		System.out.println("->1-New System 2-Old one 3-back");
		lock = new Scanner(System.in).nextLine();
		// saved state
		switch (lock) {
		case "1":
			System.out.println("Type your commands");
			if (sys != null) {
				Main(user, admin);
			} else {
				while (!command.equals("back")) {
					command = new Scanner(System.in).nextLine();
					String str[] = command.trim().split(" ");
					if (str[0].equals("createsystem")) {
						if (str[1].equals("con"))
							t = new Contiguous();
						else if (str[1].equals("index"))
							t = new Indexed();
						else
							break;
						sys = new system(Integer.parseInt(str[2]), t);
						System.out.println("->System has been initialized");
						Main(user, admin);
						// t.write(sys,
						// "C:\\Users\\Anoos\\Desktop\\.metadata.vsf");
					} else if (!str[0].equals("exit"))
						System.out.println("->Wrong command try again");
				}
			}
			break;

		case "2":
			// create System
			t = new Contiguous();
			System.out.println("Type your commands");
			try {
				sys = parse.read("C:\\Users\\Anoos\\Desktop\\.metadata.vsf");
				System.out
						.println("->System has been loaded enter your commands");
				Main(user, admin);
				t.write(sys, "C:\\Users\\Anoos\\Desktop\\.metadata.vsf");
			} catch (Exception e) {
				System.out.println("->ther is no system to load!");
			}
			break;
		case "3":
			return;
		}
	}

}