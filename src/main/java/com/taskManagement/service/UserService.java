package com.taskManagement.service;

import java.util.Set;

import com.taskManagement.entity.User;
import com.taskManagement.entity.UserRole;



public interface UserService {
	
	//create user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
   
    //get user by userName
    public User getUser(String username);

  //delete user by userId
    public void deleteUser(Long userId);

	public User getUserById(long userId);

}
