package com.revature.daos;

import com.revature.models.User;

public interface UserDAO {

	boolean checkPassword(String username, String password);

	User findUserByUsername(String username);

	boolean addPerson(String legalName, String username, String password);

}
