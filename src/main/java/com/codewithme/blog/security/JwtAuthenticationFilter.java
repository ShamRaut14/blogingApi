package com.codewithme.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JWTtokenHelpper jwtTokenHelpper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get token
		String requestToken=request.getHeader("Authorization");
		
		// Bearer 
		
		System.out.println(requestToken);
		
		String username = null;
		
		String token= null;
		if (requestToken!=null && requestToken.startsWith("Bearer")) {
			
			token = requestToken.substring(7);//it remove Bearer from TokenHeader
			try {
			username = this.jwtTokenHelpper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e){
				System.out.println("unable to get Jwt Token"); 
					}
			catch(ExpiredJwtException e) {
				System.out.println("Token is Expired");
			}
			catch(MalformedJwtException e) {
				System.out.println("invalid Jwt");
			}
			
		}else {
			 System.out.println("JWt token does not benig with Bearer");
		}
		
		// once we get the token now validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails =this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelpper.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				System.out.println("invalid Jwt token");
			}
		}
		else {
			System.out.println("username or context is null");

			
		}
		
		
		filterChain.doFilter(request, response);
	}
	
	

	

}
