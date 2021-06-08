package com.gd.checkin.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/","/**") // 아직은 Security 적용 x
			.access("hasRole('ROLE_USER')")
			.antMatchers("/","/**").access("permitAll")
			.and()
			  .httpBasic();
	}
	
	@Override 
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		
	}
}
