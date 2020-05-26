package com.example.demo.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SpringBootSecurityv1Application;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.apache.logging.log4j.*;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {
	
	private static final Logger logger=LogManager.getLogger(PublicRestApiController.class);
	private UserRepository userRepository;
	
	

    public PublicRestApiController(UserRepository userRepository){
    	this.userRepository = userRepository;
    }

    @GetMapping("test1")
    public String test1(){
    	
    	Authentication auth =         SecurityContextHolder.getContext().getAuthentication();
    	logger.info("API test1 has been used");
    	logger.debug("API test1 has been used in debug mode");
    	String name = auth.getName();
    	
    	return "API Test 1 "+name;
    }

    @GetMapping("test2")
    public String test2(){
    	
    	logger.info("API test2 has been used");
    	logger.debug("API test2 has been used in debug mode");
        return "API Test 2";
    }
    
    @GetMapping("users")
    public List<User> users(){
    	
        return this.userRepository.findAll();
    }

}
