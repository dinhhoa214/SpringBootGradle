package org.gradle.service;

import java.util.List;

import org.gradle.entity.User;
 

public interface UserService {
	
	User save(User user);
	
	List<User> getUser();
	
	User getUserByEmail(String email);

	List<User> deleteUser(String email);

	User updateUser(User user);
}
