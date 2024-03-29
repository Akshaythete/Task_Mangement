package com.taskManagement.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	
	private  String token;
	   
	   private String refreshToken;
	   
	   private String username;
	    
}
