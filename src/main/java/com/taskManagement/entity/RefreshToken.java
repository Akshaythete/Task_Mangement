package com.taskManagement.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RefreshToken {
	
	//unique
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int tokenId;
		
		private  String refreshToken;
		private Instant expiry;
		
		
		@OneToOne
		private User user;

}
