package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private UserPrincipleDetailsService userPrincipleDetailsService;
	
	public SecurityConfiguration (UserPrincipleDetailsService userPrincipleDetailsService) {
		
		this.userPrincipleDetailsService = userPrincipleDetailsService;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.authenticationProvider(authenticationProvider());
		
				/*.inMemoryAuthentication()
				.withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").authorities("ACCESS_TEST1","ACCESS_TEST2","ROLE_ADMIN")
				.and()
				.withUser("Harshesh").password(passwordEncoder().encode("Harshesh01")).roles("USER")
				.and()
				.withUser("Manager").password(passwordEncoder().encode("Manager123")).roles("MANAGER").authorities("ACCESS_TEST1","ROLE_MANAGER");*/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		
				.authorizeRequests()
				.antMatchers("/index.html").permitAll()
				.antMatchers("/register.html").permitAll()
				.antMatchers("/save-user").permitAll()
				.antMatchers("/bomGeneration/**").authenticated()
				.antMatchers("/bomGeneration/**").authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/management/**").hasAnyRole("ADMIN","MANAGER")
				.antMatchers("/api/public/test1").hasAnyAuthority("ACCESS_TEST1")
				.antMatchers("/api/public/test2").hasAnyAuthority("ACCESS_TEST2")
				.antMatchers("/api/public/users").hasAnyRole("ADMIN")
				//.anyRequest().authenticated()
				.and()
				//.httpBasic();
				.formLogin()
				.loginPage("/login").permitAll()
				.failureUrl("/login-error.html")
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
	            .clearAuthentication(true)
				.and()
				.rememberMe().tokenValiditySeconds(2592000).key("MySecret!");
	}
	
	
	@Bean
	DaoAuthenticationProvider authenticationProvider () {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userPrincipleDetailsService);
		
		return daoAuthenticationProvider;
	}
	
	@Bean	
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

