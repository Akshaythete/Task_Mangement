package com.taskManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskManagement.entity.Task;
import com.taskManagement.entity.User;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

	//custom finder methods
	
	List<Task> findByUserOrderByDeadline(User user);
	
    List<Task> findByUserAndCompletedOrderByDeadline(Long userId, boolean completed);

    //find task by userid
	List<Task> findByUserId(Long userId);
}
