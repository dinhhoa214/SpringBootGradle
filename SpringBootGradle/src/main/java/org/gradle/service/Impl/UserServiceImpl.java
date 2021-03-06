package org.gradle.service.Impl;

import java.util.List;

import org.gradle.entity.User;
import org.gradle.responsitory.UserRepository;
import org.gradle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	public User save(User user){
		User us = getUserByEmail(user.getEmail());
		if (us == null){
		userRepository.save(user);
		user.setPassword("");
		return user;
		}
		return null;
	}
	
	public List<User> getUser(){
		
		List<User> list = userRepository.findAll();
		list.forEach(us -> us.setPassword(""));
		return list;
	}
	
	public User getUserByEmail(String email){
		User user =  userRepository.findByEmail(email);
		if (user == null) return null;
		user.setPassword("");
		return user;
	}

	@Override
	public List<User> deleteUser(String email) {
		if (getUserByEmail(email) != null){
			userRepository.delete(userRepository.findByEmail(email));
		}
		return getUser();
	}

	@Override
	public User updateUser(User user) {
		if (getUserByEmail(user.getEmail()) == null)
		return null;
		else{
			userRepository.delete(userRepository.findByEmail(user.getEmail()));
			return save(user);
		}
	}
}
