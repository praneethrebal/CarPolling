package com.carPooling.service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.carPooling.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	String key="";
	public JWTService()
	{
		KeyGenerator keyGenerator;
		try {
			keyGenerator=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk=keyGenerator.generateKey();
			key=Base64.getEncoder().encodeToString(sk.getEncoded());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String generateToken(User user) {
		Map<String, Object> claims=new HashMap<String, Object>();
		claims.put("role", user.getRole());
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+10*60*60*60))
				.and()
				.signWith(getKey())
				.compact();
				
	}

	private SecretKey getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(key);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	public String extractUsername(String token) {
		
		return extractClaim(token,Claims::getSubject);
	}
	private <T>T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		
		return  Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

	}
	public boolean validate(String token, UserDetails details) {
	String username=extractUsername(token);
		return ((username.equals(details.getUsername())) && !isTokenExpried(token));
	}
	private boolean isTokenExpried(String token) {
		return extractExpration(token).before(new Date());
	}

	private Date extractExpration(String token) {

		return extractClaim(token,Claims::getExpiration);
	}


}
