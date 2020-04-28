package com.example.demo.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {
	
	
	private UserRepository userRepository;
	
	

    public PublicRestApiController(UserRepository userRepository){
    	this.userRepository = userRepository;
    }

    @GetMapping("test1")
    public String test1(){
    	
    	Authentication auth =         SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName();
    	
    	return "API Test 1 "+name;
    }

    @GetMapping("test2")
    public String test2(){
        return "API Test 2";
    }
    
    @GetMapping("users")
    public List<User> users(){
    	
        return this.userRepository.findAll();
    }

}
