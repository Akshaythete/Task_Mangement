package com.taskManagement.service;

import com.taskManagement.entity.RefreshToken;

public interface RefreshTokenService {
	
	//create token
		public RefreshToken createRefreshToken(String userName);
		
		//verify token
		public RefreshToken verifyToken(String refreshToken);

		//RefreshToken verifyToken(RefreshToken refreshToken);
		
		

}
