package com.crafty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.crafty.security.JwtAuthenticationEntryPoint;
import com.crafty.security.JwtAuthenticationTokenFilter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new SCryptPasswordEncoder(16384, 8, 1, 32, 32);
    }
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
         // disabling csrf here, you should enable it before using in production
        .csrf().disable()
        
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

        // Don't create session.
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        
        .authorizeRequests()
	        .antMatchers("/api/v1/welcome").permitAll()
            .antMatchers("/api/v1/register").permitAll()
            .antMatchers("/api/v1/login").permitAll()
            .antMatchers("/api/v1/refresh").permitAll()
			.antMatchers("/api/v1/upload").permitAll()
            .antMatchers("/images/**").permitAll()
            .antMatchers("/api/v1/categories").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/items/*").permitAll()
            .antMatchers("/api/v1/items/*").hasRole("AUTHOR")
			.antMatchers(HttpMethod.POST, "/api/v1/items").hasRole("MEMBER")
            .anyRequest().authenticated();
    	
    	// Custom JWT based security filter.
        httpSecurity
            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // Disable page caching.
        httpSecurity.headers().cacheControl();
    }
    
}
