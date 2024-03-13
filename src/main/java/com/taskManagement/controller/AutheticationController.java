package com.taskManagement.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.taskManagement.config.JwtUtils;
import com.taskManagement.dto.JwtRequest;
import com.taskManagement.dto.JwtResponse;
import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.RefreshToken;
import com.taskManagement.entity.User;
import com.taskManagement.exception.UserNotFoundException;
import com.taskManagement.service.RefreshTokenService;
import com.taskManagement.service.impl.UserDetailsServiceImpl;


@RestController
@CrossOrigin("*")
public class AutheticationController {

	
	 @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @Autowired
	    private UserDetailsServiceImpl userDetailsService;
	   
	    @Autowired
	    private JwtUtils jwtUtils;
	 
	    @Autowired
	    private RefreshTokenService refreshTokenService;

	    //generate token
	    @PostMapping("/generate-token-authentication")
	    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
	        try {

	            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
	        } catch (UserNotFoundException e){
	            e.printStackTrace();
	            throw new Exception("user not found ");

	        }

	        //////authenticate
	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
	        String token = this.jwtUtils.generateToken(userDetails);
	        
	       RefreshToken refreshToken= refreshTokenService.createRefreshToken(userDetails.getUsername());
	        
	       
	    JwtResponse jwtResponse  = JwtResponse.builder()
	       .token(token)
	       .refreshToken(refreshToken.getRefreshToken())
	       .username(userDetails.getUsername()).build();
	        return  new ResponseEntity<>(jwtResponse,HttpStatus.OK);
	    }



	    private void authenticate(String username, String password) throws Exception {

	        try {

	           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

	        }catch (DisabledException e){
	            throw new Exception("Use Disable "+ e.getMessage());
	        }catch (BadCredentialsException e){
	            throw new Exception("bad credential "+ e.getMessage());
	        }
	    }

	// return the current user details
	 @GetMapping("/currentUser")
	    public User getCurrentUser(Principal principal){

	     return  (User)this.userDetailsService.loadUserByUsername(principal.getName());

	 }
}
