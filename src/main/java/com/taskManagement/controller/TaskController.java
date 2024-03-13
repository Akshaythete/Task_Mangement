package com.taskManagement.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	 @PostMapping("/create/{userId}")
	    public ResponseEntity<TaskDto>createTask(@RequestBody TaskDto taskDto,@PathVariable long userId){
	        TaskDto taskDt = taskService.createTask(taskDto,userId);
	        return new ResponseEntity<>(taskDt, HttpStatus.CREATED);

	    }
	    
	 @GetMapping("/getAll")
	    public List<TaskDto> getAllTask(){
	        return taskService.getAllTask();
	    }
	 
	 
	 
	 @PutMapping("/update/{taskId}")
	    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
	        TaskDto updatedTask = taskService.updateTask(taskId, taskDto);
	        return ResponseEntity.ok(updatedTask);
	    }

	    @DeleteMapping("/delete/{taskId}")
	    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
	        taskService.deleteTask(taskId);
	        return ResponseEntity.noContent().build();
	    }
	    
	    @GetMapping("/user/{userId}")
	    public ResponseEntity<List<TaskDto>> getTasksByUserId(
	            @PathVariable Long userId,
	            @RequestParam(required = false) Boolean completed,
	            @RequestParam(defaultValue = "true") boolean sortByDeadline) {
	        
	        List<TaskDto> tasks;

	        if (completed != null) {
	            tasks = taskService.getTasksByUserIdAndCompletionStatus(userId, completed);
	            System.out.println("c");
	        } else {
	            tasks = taskService.getTasksByUserId(userId);
	            System.out.println("c==null");
	        }

	        if (sortByDeadline) {
	            tasks.sort(Comparator.comparing(TaskDto::getDeadline));
	        }

	        return ResponseEntity.ok(tasks);
	    }
	    
	    @GetMapping("/user/{userId}/completed/{completed}")
	    public ResponseEntity<List<TaskDto>> getTasksByUserIdAndCompletionStatus(
	            @PathVariable Long userId,
	            @PathVariable boolean completed) 
	    {
	        List<TaskDto> tasks = taskService.getTasksByUserIdAndCompletionStatus(userId, completed);
	        return ResponseEntity.ok(tasks);
	    }

}
