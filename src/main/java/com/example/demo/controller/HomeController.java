package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.User;
import com.example.demo.services.UserService;

	@Controller
	@RequestMapping("/")
	public class HomeController {
		

		@Autowired
		private UserService userService;

		
	    @GetMapping("index")
	    public String index(){
	        return "index";
	    }
    

	    @GetMapping("login")
	    public String login(){
	        return "login";
	    }
	    
	    @GetMapping("register")
	    public String register(){
	        return "register";
	    }
	    
		@PostMapping("/save-user")
		public String registerUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {
			
			try{
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(user.getPassword());
		        user.setPassword(hashedPassword);
				userService.saveMyUser(user);
				redirectAttributes.addFlashAttribute("message", "You have successfully completed the registraion, please login to use the application!");
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			    return "redirect:/register";
				
			}catch(Exception e) {
				redirectAttributes.addFlashAttribute("message", "Registration has failed: "+e.getMessage());
			    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			    return "redirect:/register";
			}
			
			
		}
		
		// Login form with error
		  @RequestMapping("/login-error.html")
		  public String loginError(Model model) {
		    model.addAttribute("loginError", true);
		    return "login";
		  }
  
}
