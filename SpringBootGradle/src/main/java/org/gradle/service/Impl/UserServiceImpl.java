package org.gradle.service.Impl;

import java.util.List;

import org.gradle.entity.User;
import org.gradle.responsitory.UserRepository;
import org.gradle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils; 

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	 

	public User insertUser(User user){ 
		boolean check = checkUserByEmail(user.getEmail());
		if(check){
		    throw new RuntimeException("Email is exist");
		}
		updateUser(user);
		return user;
	}
	
	@Override
	public void updateUser(User user) {
		userRepository.save(user);
		if(!StringUtils.isEmpty(user.getPassword()))
			user.setPassword("");
	}

	public List<User> getUsers(){ 
		List<User> list = userRepository.findAll(); 
		if(!CollectionUtils.isEmpty(list)){
			   list.forEach(us -> us.setPassword(""));
			}
		return list;
	} 

	public User getUserByEmail(String email) {
		User user =  userRepository.findByEmail(email); 
		return user;
	}
	
	@Override
	public User getUserByEmailHiddenPass(String email) {
		User user = getUserByEmail(email);
		if(user == null){
			   throw new IllegalArgumentException("could not user empty !");
		}
		if(!StringUtils.isEmpty(user.getPassword()))
			user.setPassword("");
		return user;
	}

	private boolean checkUserByEmail(String email){
		User user = getUserByEmail(email);
		if(user == null)
			return false;
		return true;
	}
	
	public User getUserById(String id){
		User user = userRepository.findOne(id);
		if(user == null){
		    throw new IllegalArgumentException("could not user empty !");
		} 
		return user;
	}

	@Override
	public void deleteUser(String idUser) {
		User userDelete = getUserById(idUser);
		userRepository.delete(userDelete);
	} 
}
