package com.taskManagement.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
	
   private long id;
	
	private String title;
	private String description;
	private Date deadline;
	private boolean completed;

}
