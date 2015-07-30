package org.gradle.controller;

import java.util.List;

import org.gradle.entity.User;
import org.gradle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinhhoa
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @param email
	 * @return User hidden password
	 * @example: http://localhost:8080/user/thanhtue@gmail.com
	 */
	@RequestMapping("/{email:.*}")
	public User read(@PathVariable String email) {
		return userService.getUserByEmailHiddenPass(email);
	}

	/**
	 * @return List <User> created in database.
	 * @example: http://localhost:8080/user/list
	 */
	@RequestMapping("/list")
	public List<User> getListUser() {
		return userService.getUsers();
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @return User hidden password
	 * @example: http://localhost:8080/user/add
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public User addUser(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		User user = new User(firstName, lastName, email, password);
		return userService.insertUser(user);
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return User updated and hidden password
	 * @example: http://localhost:8080/user/55b9ed2054500b4c3b2ecb81
	 *           ?firstName=Tran231
	 *           ?firstName=Tran&lastName=Thanh+Tue&email=tranthanhtue%40gmail.
	 *           com
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String id,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "email", required = false) String email) {
		User user = userService.getUserById(id);
		if (!StringUtils.isEmpty(firstName))
			user.setFirstName(firstName);
		if (!StringUtils.isEmpty(lastName))
			user.setLastName(lastName);
		if (!StringUtils.isEmpty(email))
			user.setEmail(email);
		userService.updateUser(user);
		return user;
	}

	/**
	 * @param id
	 *            User id=55b9ed2054500b4c3b2ecb81
	 * @example http://localhost:8080/user/55b9ed2054500b4c3b2ecb81
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		userService.deleteUser(id);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public List<User> deleteByPost(@RequestParam String id) {
		userService.deleteUser(id);
		return userService.getUsers();
	}

}
