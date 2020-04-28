/**
 * 
 */
package com.example.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

/**
 * @author Harshesh
 *
 */
@Service
public class UserPrincipleDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public UserPrincipleDetailsService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User user = this.userRepository.findByUsername(username);
		UserPrinciple userPrinciple = new UserPrinciple(user);
		
		return userPrinciple;
	}

}
