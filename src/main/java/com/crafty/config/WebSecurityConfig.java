package com.crafty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
         // disabling csrf here, you should enable it before using in production
         .csrf().disable()
        .authorizeRequests()
            .antMatchers("/api/v1/register").permitAll()
//            .antMatchers("/api/v1/welcome").permitAll()
            .antMatchers("/api/v1/hello").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new SCryptPasswordEncoder(16384, 8, 1, 32, 32);
    }
}
