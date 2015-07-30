package org.gradle.service;

import java.util.List;

import org.gradle.entity.User;
 

public interface UserService {
	/*
	 * @
	 */
	User getUserById(String id);
	User insertUser(User user); 
	void updateUser(User user);
	User getUserByEmailHiddenPass(String email);
	/*
	 * 
	 *
	 */
	List<User> getUsers(); 
	/*
	 * 
	 * 
	 */
	void deleteUser(String idUser);

	
}
