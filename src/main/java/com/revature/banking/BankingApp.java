package com.revature.banking;

import java.util.Scanner;

import com.revature.models.User;
import com.revature.services.UserService;

public class BankingApp {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s;
		User currentUser = null;
		while (sc.hasNextLine()) {
			if(currentUser == null) {
				System.out.println("[create | login]");
				s = sc.nextLine();
				//System.out.println(">>" + s + "<<");
				switch(s) {
				case "create":
					System.out.println("not implemented");
					break;
				case "login":
					System.out.println("username > ");
					String username = sc.nextLine();
					System.out.println("password > ");
					String password = sc.nextLine();
					currentUser = login(username, password);
					if(currentUser == null) {
						System.out.println("Login failed.");
					}
					break;
				}
			} else {
				System.out.println("[balance | deposit | withdraw]");
				s = sc.nextLine();
				switch(s) {
				case "balance":
					System.out.println("not implemented");
					break;
				case "deposit":
					System.out.println("not implemented");
					break;
				case "withdraw":
					System.out.println("not implemented");
					break;
				}
			}
		}
		sc.close();		
	}
	/*
	1) create a user account with a unique email and/or username 
    (must have multiple users)
 	2) secure my account using a password
	3) log in/log out
	4) create a bank account associated with each user
	5) deposit money
	6) withdraw money (no negative balances!)
	7) view account balance
	*/

	private static User login(String username, String password) {
		/*
		 select crypt('security', 
			(select password from person where username='gottreu')) 
			= password from person where username = 'gottreu';
		*/
		UserService us = new UserService();
		if(us.checkPassword(username, password)) {
			System.err.println("password was correct");
			return us.findUserByUsername(username);
		}
		return null;
	}
}
