package com.taskManagement.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.taskManagement.dto.TaskDto;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.User;
import com.taskManagement.repository.TaskRepo;
import com.taskManagement.service.TaskService;
import com.taskManagement.service.UserService;
@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	 @Autowired
	    private UserService userService;


	@Override
	public TaskDto createTask(TaskDto taskDto ,long userId) {
		Task task = dtoToTask(taskDto);
		
		User user = userService.getUserById(userId);
        if (user == null) {
            // Handle the case where the user with the given ID is not found
            // You can throw an exception or handle it based on your application's logic
            throw new RuntimeException("User not found with ID: " + userId);
        }

        // Set the user for the task
        task.setUser(user);
		
		//task.setDeadline(new Date());
        Task save = taskRepo.save(task);
        TaskDto taskDto1 = taskToDto(save);
        return taskDto1;
		
	}
	
	//get all task
	@Override
	public List<TaskDto> getAllTask() {
		List<Task> allTask = taskRepo.findAll();
        return allTask.stream().map(task -> taskToDto(task)).collect(Collectors.toList());
	
	}
	
	
	//get task by userid
	@Override
	public List<TaskDto> getTasksByUserId(Long userId) {
	    List<Task> tasksByUserId = taskRepo.findByUserId(userId);
	     return tasksByUserId.stream()
	                        .map(this::taskToDto)
	                        .collect(Collectors.toList());
	}

	
	//update task
	 @Override
	    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
	        Task existingTask = taskRepo.findById(taskId)
	                                     .orElseThrow(() -> new IllegalArgumentException("Task not found"));
	        updateTaskFromDto(existingTask, taskDto);
	        Task updatedTask = taskRepo.save(existingTask);
	        return taskToDto(updatedTask);
	    }

	 
	 //delete task
	    @Override
	    public void deleteTask(Long taskId) {
	        Task existingTask = taskRepo.findById(taskId)
	                                     .orElseThrow(() -> new IllegalArgumentException("Task not found"));
	        taskRepo.delete(existingTask);
	        
	    }
	    
	   
	    @Override
	    public List<TaskDto> getTasksByUserIdAndCompletionStatus(Long userId, boolean completed) {
	        List<Task> tasks = taskRepo.findByUserAndCompletedOrderByDeadline(userId, completed);
	        return tasks.stream().map(this::taskToDto).collect(Collectors.toList());
	    }
	
	private TaskDto taskToDto(Task task){
		
		TaskDto taskDto=this.modelMapper.map(task, TaskDto.class);
		return taskDto;
	}
	private Task dtoToTask(TaskDto taskDto){
		
		Task task=this.modelMapper.map(taskDto, Task.class);
		return task;

     }
	 private void updateTaskFromDto(Task task, TaskDto taskDto) {
	        task.setTitle(taskDto.getTitle());
	        task.setDescription(taskDto.getDescription());
	        task.setDeadline(taskDto.getDeadline());
	        task.setCompleted(taskDto.isCompleted());
	    }

	
}
