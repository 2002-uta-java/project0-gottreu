package com.revature.banking;

import java.util.Scanner;

public class BankingApp {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s;
		while (sc.hasNextLine()) {
			s = sc.nextLine();
			System.out.println(">>" + s + "<<");
			switch(s) {
			case "create":
				System.out.println("enter new username");
				System.out.println("username entered = " + sc.nextLine());
				break;
			case "login":
				System.out.println("supply username to login");
				break;
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
}
