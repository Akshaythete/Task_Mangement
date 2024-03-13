package com.taskManagement.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagement.entity.Role;
import com.taskManagement.entity.User;
import com.taskManagement.entity.UserRole;
import com.taskManagement.service.UserService;



@RestController
@CrossOrigin("*")
@RequestMapping("/user")

public class UserController {
	
	@Autowired
    private UserService userService;
    
	//create user
	
	@PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) throws Exception {
        
        Set<UserRole> userRoles = new HashSet<>();
        Role role = new Role();
        role.setRoleId(50L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoles.add(userRole);

       return this.userService.createUser(user,userRoles);
    }

	//get user by username
	
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser( @PathVariable("username") String username){
        return ResponseEntity.ok(userService.getUser(username));
    }
    
    //delete user bu userId
    
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") long userId){
        this.userService.deleteUser(userId);
    }


}
