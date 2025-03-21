package com.mutual.funds.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;


import com.mutual.funds.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class jwtSecurity {
	
	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	@Value("${security.jwt.issuer}")
	private String issuer;

	public String createJwtToken(UserEntity u) {
		byte[] keybytes = Decoders.BASE64.decode(secretKey);
		SecretKey key = Keys.hmacShaKeyFor(keybytes);
		long mins = 1800000;
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", u.getEmail());
		claims.put("phone", u.getPhone_number());
		claims.put("user_id", u.getId());
		String jwtToken = Jwts.builder().setIssuer(issuer)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setClaims(claims)
		.setExpiration(new Date(System.currentTimeMillis()+ mins))
		.setSubject(u.getName())
		.signWith(key)
		.compact();
			
		return jwtToken;
	}
}
