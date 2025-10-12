package com.jnas.books_marketplace_be.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration:604800000}") // Default 7 days
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> function) {
        final Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isNotTokenValid(String token, UserDetails userDetails) {
        return !isTokenValid(token, userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiryDate(token).before(new Date());
    }

    private Date getExpiryDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateAccessToken(UserDetails user) {
        return generateAccessToken(user, new HashMap<>());
    }

    public String generateAccessToken(UserDetails user, Map<String, Object> claims) {
        return buildToken(user, claims, accessTokenExpiration);
    }
    
    public String generateRefreshToken(UserDetails user) {
        return buildToken(user, new HashMap<>(), refreshTokenExpiration);
    }

    private String buildToken(UserDetails user, Map<String, Object> claims, long expiration) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }
}

