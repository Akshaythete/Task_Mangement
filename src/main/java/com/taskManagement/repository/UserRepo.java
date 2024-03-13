package com.taskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskManagement.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	//custom method
		public User findByUsername(String username);
		
//		 Optional<User> findByUsername(String username);
		
		 User findByUsernameAndPassword(String username, String currentPassword);

}
