package com.taskManagement.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskManagement.entity.User;
import com.taskManagement.entity.UserRole;
import com.taskManagement.exception.UserNotFoundException;
import com.taskManagement.repository.RoleRepository;
import com.taskManagement.repository.UserRepo;
import com.taskManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local = this.userRepo.findByUsername(user.getUsername());
        if(local!=null)
        {
            System.out.println("User is already there!");
            throw new Exception("User is already present!");
        }
        else 
        {
            for (UserRole ur : userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepo.save(user);
        }
        return local;
    }
   
	//getting user by username
    @Override
    public User getUser(String username) {
        return this.userRepo.findByUsername(username);

    }

    @Override
    public void deleteUser(Long userId) {
         this.userRepo.deleteById(userId);
         System.out.println("User Deleted Successfully!!1");

    }

    @Override
	public User getUserById(long userId) throws UserNotFoundException {
		return userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
	}

}
