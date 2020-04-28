/**
 * 
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

/**
 * @author Harshesh
 *
 */
@Repository
public interface UserRepository extends JpaRepository <User, Integer>	{
	
	public User findByUsername (String username);
	
	public User findByUsernameAndPassword(String username, String password);

}
