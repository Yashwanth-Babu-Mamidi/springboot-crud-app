package com.example.crudapp.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component

public class JwtUtil {

    // Secret key (should be at least 256 bits)
    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    private static final long EXPIRATION =
            1000 * 60 * 60 * 24; // 24 hours

    private Key getSigningKey(){

        return Keys.hmacShaKeyFor(SECRET.getBytes());

    }

    // Generate Token
    public String generateToken(String username){

        return Jwts.builder()

                .setSubject(username)

                .setIssuedAt(new Date())

                .setExpiration(

                        new Date(System.currentTimeMillis()
                                + EXPIRATION)

                )

                .signWith(getSigningKey(),
                        SignatureAlgorithm.HS256)

                .compact();
    }

    // Extract Username
    public String extractUsername(String token){

        return extractClaim(token,
                Claims::getSubject);

    }

    // Extract Expiration
    public Date extractExpiration(String token){

        return extractClaim(token,
                Claims::getExpiration);

    }

    // Extract single claim
    public <T> T extractClaim(

            String token,

            Function<Claims,T> resolver){

        final Claims claims = extractAllClaims(token);

        return resolver.apply(claims);

    }

    // Extract all claims
    private Claims extractAllClaims(String token){

        return Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

    }

    // Check expiration
    private boolean isTokenExpired(String token){

        return extractExpiration(token)

                .before(new Date());

    }

    // Validate token
    public boolean validateToken(

            String token,

            String username){

        final String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username)

                && !isTokenExpired(token);

    }

}