package com.taskManagement.service;

import java.util.List;


import com.taskManagement.dto.TaskDto;

public interface TaskService {
	
	//create task
	
	 public TaskDto createTask(TaskDto taskDto,long userId);
	 
	 // get all list
	 List<TaskDto> getAllTask();
	 
	 //update task
	 public TaskDto updateTask(Long taskId, TaskDto taskDto);
	 
	 //delete task
	 public void deleteTask(Long taskId);
	 
	 //get task by userid
	 public List<TaskDto> getTasksByUserId(Long userId) ;

	 
	 //get task by userid and completionstatus
	List<TaskDto> getTasksByUserIdAndCompletionStatus(Long userId, boolean completed);

}
