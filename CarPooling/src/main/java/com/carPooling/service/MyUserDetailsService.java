package com.carPooling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carPooling.entity.User;
import com.carPooling.principal.UserDetailPrincipal;
import com.carPooling.repo.UserRepo;


@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.findByUsername(username);
		if(user == null)
		{
			throw new UsernameNotFoundException("not found");
		}
		
		return new UserDetailPrincipal(user);
	}

}
