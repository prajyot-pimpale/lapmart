package com.lapmart.util;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component  // Marks this as a Spring-managed bean
public class JwtUtil {

	// Read secret key from application.properties
	@Value("${app.jwt.secret}")  
	private String secretKey;
	
	// Read expiration from application.properties (default: 24 hours in ms)
	@Value("${app.jwt.expiration}")  
	private Long expirationTime;
	
	// Build a cryptographic signing key from the secret string
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	// ─── Generate Token ───────────────────────────────────────────
    // email is stored as the "subject" inside the token
	public String generateToken(String email, String role){
		return Jwts.builder()
				.setSubject(email)                          // who this token belongs to
                .claim("role", role)                        // extra data inside token
                .setIssuedAt(new Date())                    // when token was created
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // expiry
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign with secret
                .compact();  
	}
	
	// ─── Extract Email from Token ─────────────────────────────────
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    // ─── Extract Role from Token ──────────────────────────────────
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // ─── Validate Token ───────────────────────────────────────────
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token); // if this doesn't throw, token is valid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // expired, tampered, or malformed
        }
    }

    // ─── Internal: Parse & Verify Signature ──────────────────────
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
