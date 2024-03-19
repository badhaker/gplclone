package com.alchemy.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomUserDetailService implements UserDetailsService {

	public CustomUserDetailService() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomUserDetailService(BCryptPasswordEncoder bCryptPasswordEncoder) {
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
}
