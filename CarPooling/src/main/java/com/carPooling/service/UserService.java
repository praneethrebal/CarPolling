package com.carPooling.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carPooling.entity.User;
import com.carPooling.repo.UserRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepo userRepo;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	

	public User findByUsername(String username) {
		
		return userRepo.findByUsername(username);
	}

	public String verify(String username, String password) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		User user=userRepo.findByUsername(username);
		if(authentication !=null)
		{
			return jwtService.generateToken(user);
		}
		 throw new UsernameNotFoundException("Enter Valid Details");
	}

	public void register(User user) {
		if(user == null)
		{
			throw new UsernameNotFoundException("Enter Valid Details");
		}
		userRepo.save(user);
		
	}

	public String getUserRole(String username) {
		
		return userRepo.findRole(username);
	}

	public User findById(Long id) {
		
		return userRepo.findById(id).orElse(null);
	}


}
