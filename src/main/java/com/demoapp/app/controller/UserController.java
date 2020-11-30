package com.demoapp.app.controller;

import java.util.Optional;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.app.model.User;
import com.demoapp.app.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/create-user")
	@ResponseBody
	public ResponseEntity createUser(@RequestBody User user) {

		WeakHashMap<String, Object> dataMap = new WeakHashMap<String, Object>();

		if (user.getFirstName() == null || user.getLastName() == null) {
			dataMap.put("message", "Mandatory fields not present.");
			return new ResponseEntity<>(dataMap, HttpStatus.BAD_REQUEST);
		}
		
		if(!user.getEmail().matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}")) {
			dataMap.put("message", "Invalid email.");
			return new ResponseEntity<>(dataMap, HttpStatus.BAD_REQUEST);
		}
		
		if(!user.getPhoneNumber().matches("^\\+[1-9]{1}[0-9]{3,14}")) {
			dataMap.put("message", "Invalid phone number.");
			return new ResponseEntity<>(dataMap, HttpStatus.BAD_REQUEST);
		}

		try {
			User savedUser = userService.createUser(user);
			dataMap.put("savedUser", savedUser);
			dataMap.put("message", "Successfully created new user.");
			return new ResponseEntity<>(dataMap, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.toString());
			dataMap.put("message", "Something went wrong, please try again later.");
			return new ResponseEntity<>(dataMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/delete-user")
	@ResponseBody
	public ResponseEntity deleteUser(@RequestBody User user) {

		WeakHashMap<String, Object> dataMap = new WeakHashMap<String, Object>();

		User deletedUser = userService.deleteUser(user);

		if (deletedUser == null) {
			
			dataMap.put("message", "Something went wrong, please try again later.");
			return new ResponseEntity<>(dataMap, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		dataMap.put("deletedUser", deletedUser);
		dataMap.put("message", "Successfully deleted the user.");
		return new ResponseEntity<>(dataMap, HttpStatus.OK);
		
	}

	@GetMapping("/get-user/{userId}")
	public ResponseEntity getUser(@PathVariable("userId") String userId) {

		WeakHashMap<String, Object> dataMap = new WeakHashMap<String, Object>();
		Optional<User> user = userService.getUser(userId);

		if (user == null) {
			dataMap.put("message", "Something went wrong, please try again later.");
			return new ResponseEntity<>(dataMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if( user.isPresent()) {
			dataMap.put("user", user.get());
			return new ResponseEntity<>(dataMap, HttpStatus.OK);
		} 
		
		dataMap.put("message", "NonExistentUser");
		return new ResponseEntity<>(dataMap, HttpStatus.BAD_REQUEST);
		
	}
}
