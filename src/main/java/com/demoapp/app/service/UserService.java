package com.demoapp.app.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoapp.app.model.User;
import com.demoapp.app.repository.UserRepository;

@Service
public class UserService {

	@Autowired    
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public User createUser(User user) {
		try {
			User savedUser= userRepository.save(user);
			return savedUser;
		} catch(Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
	
	public User deleteUser(User user) {
		User deletedUser = user;
		try {
			if(userRepository.existsById(user.getUserId())) {
				userRepository.delete(user);
			} else {
				logger.error("Cannot delete non-existent user.");
				return null;
			}
			
		} catch(Exception e) {
			logger.error(e.toString());
			return null;
		}
		return deletedUser;
	}
	
	public Optional<User> getUser(String userId) {
		
		Optional<User> user;
		
		try {
			user = userRepository.findById(userId);
			
		} catch(Exception e) {
			logger.error(e.toString());
			return null;
		}
		
		return user;
	}
}
