package com.blo.sales.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public final class JwtUtil {

	// Reutiliza la misma clave secreta
    private static String SECRET_KEY = "blo_sales_token_password_too_long_abcdefghijklmnopqrstuvwxyz_1234567890";
    
	// 18 horas en milisegundos
    private static final long EXPIRATION_TIME_MS = 18 * 60 * 60 * 1000;
    
    private JwtUtil() { }
    
    public static String generateTokenConClaims(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setSubject(username) // sub: "usuario"
                .claim("role", role)   // claim
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).getBody().getSubject(); // subject == username
    }

    public static Date extractExpiration(String token) {
        return extractAllClaims(token).getBody().getExpiration();
    }

    public static Jws<Claims> extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public static boolean isTokenValid(String token) {
        try {
            return extractExpiration(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    private static Key getSigningKey() {
    	byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
