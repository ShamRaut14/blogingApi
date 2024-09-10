package com.codewithme.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithme.blog.security.CustemUserDetailService;
import com.codewithme.blog.security.JwtAuthenticationEntryPoint;
import com.codewithme.blog.security.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
 
	
	public static final String[] PUBLIC_URL= {
			"/api/auth/**", 
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-ui/**", 
		    "/swagger-resources/**", 
		    "/webjars/**"
			};
  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @Autowired
  private CustemUserDetailService costomUserDetilService;
  
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  
  @Bean
  SecurityFilterChain defoultSecurityFilterChain(HttpSecurity http)throws Exception{
	  http.authorizeHttpRequests(authorizeRequests ->
	  authorizeRequests.requestMatchers(PUBLIC_URL).permitAll()
	  .requestMatchers(HttpMethod.GET).permitAll()
			  .anyRequest().authenticated());
	  http.sessionManagement(
			  session->
			           session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			  );
	  http.exceptionHandling(exception->
			  							exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
	  http.headers(headers -> headers.
			  frameOptions(frameOptions->frameOptions.sameOrigin()));
	  http.csrf(csrf -> csrf.disable());
	  http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
	  
	  return http.build();
  }

  protected void configure(AuthenticationManagerBuilder auth)throws Exception{
	  auth.userDetailsService(this.costomUserDetilService).passwordEncoder(passwordEncoder());
  }
 
  @Bean
  public PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	  return authenticationConfiguration.getAuthenticationManager();
  }
  
}
