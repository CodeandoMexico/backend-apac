/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Gad
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Authentication : User --> Roles
        @Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {

            auth.inMemoryAuthentication().withUser("apac_root").password("$ecR3t1").roles("USER","ADMIN");
	}

	// Authorization : Role -> Access
        @Override
	protected void configure(HttpSecurity http) throws Exception {

            http.httpBasic().and().authorizeRequests().antMatchers(Main.API_V1+"**").hasRole("USER")
                    .and().csrf().disable().headers().frameOptions().disable();

	}

}
