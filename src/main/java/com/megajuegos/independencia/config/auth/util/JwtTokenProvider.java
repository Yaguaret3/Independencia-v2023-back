package com.megajuegos.independencia.config.auth.util;

import com.megajuegos.independencia.entities.UserIndependencia;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${ar.com.independencia.jwt-secret}")
    private String jwtSecret;

    @Value("${ar.com.independencia.jwt-expiration-milliseconds}")
    private int jwtExpirationMs;

    public String generateToken(UserIndependencia userDetails) {
        Map<String, Object> authorities = new HashMap<>();
        authorities.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(authorities)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<?> authorities = (List<?>) claims.get("authorities");
        return authorities.stream()
                .map(String.class::cast)
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){

        final String email = extractUsername(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenValid(String token){
        return  token != null && !token.isEmpty() && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    private Key getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}