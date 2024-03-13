package com.taskManagement.entity;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String username;
	private String email;
	private String password;
	
	 @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
	    @JsonIgnore
	    private Set<UserRole> userRoles = new HashSet<>();
	 
	 

	    public Set<UserRole> getUserRoles() {
	        return userRoles;
	    }

	    public void setUserRoles(Set<UserRole> userRoles) {
	        this.userRoles = userRoles;
	    }

	  

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }


	    public void setUsername(String username) {
	        this.username = username;
	    }


	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        //authority naam ka class bna lenge
	        Set<Authority> set=new HashSet<>();
	        //ab authority pass krna hai ye auth userRole se ayega
	        this.userRoles.forEach(userRole -> {
	            set.add(new Authority(userRole.getRole().getRoleName()));
	        });

	        return set;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	  

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	  
		public User get() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}


	
	

}
