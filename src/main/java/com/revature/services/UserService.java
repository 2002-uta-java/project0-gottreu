package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

public class UserService {
	private UserDAO userDAO = new UserDAOImpl();
	
	public boolean checkPassword(String username, String password) {
		return userDAO.checkPassword(username, password);
	}

	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}

	public boolean addPerson(String legalName, String username, String password) {
		return userDAO.addPerson(legalName, username, password);
	}
	
	

}
