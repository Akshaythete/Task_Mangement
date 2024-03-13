package com.taskManagement.service.impl;

import java.time.Instant;
import java.util.UUID;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskManagement.entity.RefreshToken;
import com.taskManagement.repository.RefreshTokenRepo;
import com.taskManagement.repository.UserRepo;
import com.taskManagement.service.RefreshTokenService;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
	
public long refreshTokenValidity=5*60*60*10000;
	
	@Autowired
	private RefreshTokenRepo refreshTokenRepo;
	
	@Autowired
	private UserRepo userRepository;

	//create Refresh token
	@Override
	public RefreshToken createRefreshToken(String userName) {
		
		
		
		
		RefreshToken refreshToken2=RefreshToken.builder()
				.refreshToken(UUID.randomUUID().toString())
				.expiry(Instant.now().plusMillis(refreshTokenValidity))
				.user(userRepository.findByUsername(userName).get())
				.build();		
		
		//issue with token
//		Optional<User> userOptional = userRepository.findByUsername(userName);
//
//	    if (userOptional.isEmpty()) {
//	        // Handle the case where the user is not found.
//	        // You can throw an exception, log a message, or return an appropriate response.
//	        throw new RuntimeException("User not found for username: " + userName);
//	    }
//
//	    User user = userOptional.get();
//
//	    RefreshToken refreshToken2 = user.getRefreshToken();
//
//	    if (refreshToken2 == null) {
//	        refreshToken2 = RefreshToken.builder()
//	                .refreshToken(UUID.randomUUID().toString())
//	                .expiry(Instant.now().plusMillis(refreshTokenValidity))
//	                .user(user)
//	                .build();
//	    } else {
//	        refreshToken2.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
//	    }
//	
		
		
		//save refresh token in database
		refreshTokenRepo.save(refreshToken2);
		return refreshToken2;
	}

	//verify refresh token
	@Override
	public RefreshToken verifyToken(String refreshToken) {
		// TODO Auto-generated method stub
		
	RefreshToken refreshTokenDb=refreshTokenRepo.findById(refreshToken).orElseThrow(()->new RuntimeException("given token does not exist!!!"))	;
		
		if(refreshTokenDb.getExpiry().compareTo(Instant.now())<0)
		{
			refreshTokenRepo.delete(refreshTokenDb);
			throw new RuntimeException("Refresh Token Expired");
		}
		return refreshTokenDb;
	}

	
	

}
