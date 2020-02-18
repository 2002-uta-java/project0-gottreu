package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

public class UserService {
	private UserDAO userDAO = new UserDAOImpl();
	
	public boolean checkPassword(String username, String password) {
		// TODO Auto-generated method stub
		/*
		 select crypt('security', 
			(select password from person where username='gottreu')) 
			= password from person where username = 'gottreu';
		*/
		return userDAO.checkPassword(username, password);
		//return false;
	}

	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
