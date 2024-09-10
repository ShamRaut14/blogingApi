//package com.codewithme.blog.security;
//
//import java.security.Key;
//import java.util.Date;
//
//import javax.crypto.SecretKey;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//@Component
//public class JwtUtils {
//	
//	
//	private static final Logger longger =LoggerFactory.getLogger(JwtUtils.class);
//	
//	@Value("${spring.app.jwtSecret}")
//	private String jwtSecret;
//	
//	@Value("${spring.app.jwtExiprationMs}")
//	private int jwtExiprationMs;
//	
//	public String getJwtFromHeader(HttpServletRequest request) {
//		String bearerToken = request.getHeader("Authorization");
//		longger.debug("Authorization Header:{}",bearerToken);
//		System.out.println(bearerToken);
//		if(bearerToken != null && bearerToken.startsWith("Bearer")) {
//			return bearerToken.substring(7);
//		} else {
//	        longger.warn("Authorization header is missing or does not start with 'Bearer '");
//	    }
//		
//		return null;
//		}
//	  public String generateTokenFromUsername(UserDetails userDetails) {
//		  String username = userDetails.getUsername();
//		 
//		  String token= Jwts.builder()
//				  .subject(username)
//				  .issuedAt(new Date())
//				  .expiration(new Date((new Date()).getTime()+jwtExiprationMs))
//				  .signWith(key())
//				  .compact();
//
//		    longger.debug("Generated JWT Token: {}", token);
//		    return token;
//		
//	  }
//	  
//	  
//	  public String getUsernameFromJwtToken(String token) {
//		  
//		  return Jwts.parser()
//				  .verifyWith((SecretKey) key())
//				  .build()
//				  .parseSignedClaims(token)
//				 .getPayload()
//				 .getSubject();	  }
//	  private Key key() {
//		  return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//	  }
//	  public boolean validateJwtToken(String authToken) {
//		  if (authToken == null || authToken.isEmpty()) {
//		        longger.error("JWT token is empty");
//		        return false;
//		    }
//		  try {
//			
//	        Jwts.parser()
//	        .verifyWith((SecretKey) key())
//	        .build()
//	        .parseSignedClaims(authToken);
//	        System.out.println("validate");
//	        return true;
//			
//		  }catch(MalformedJwtException e){
//			  longger.error("invalid JWT Token: {}",e.getMessage());
//		  }catch(ExpiredJwtException e) {
//			  longger.error("JWT token is Expired:{}",e.getMessage());
//		  }catch(UnsupportedJwtException e) {
//			  longger.error("JWT token is Unsupportad: {}", e.getMessage());
//		  }catch(IllegalArgumentException e) {
//			  longger.error("JWT claims string is empty: {}",e.getMessage());
//		  }
//		  return false;
//	  
//	  }
//}
