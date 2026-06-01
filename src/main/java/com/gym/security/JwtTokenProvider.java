package com.gym.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long EXPIRATION_TIME = 4 * 60 * 60 * 1000L; // 4 hours

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        String userType = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("TYPE_"))
                .findFirst()
                .map(auth -> auth.replace("TYPE_", ""))
                .orElse("UNKNOWN");

        String role = authentication.getAuthorities().stream()
                .filter(auth -> auth.getAuthority().startsWith("ROLE_"))
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        List<String> permissions = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_") && !auth.startsWith("TYPE_"))
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("userType", userType)
                .claim("role", role)
                .claim("permissions", permissions)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String getUserType(String token) {
        return getClaims(token).get("userType", String.class);
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getPermissions(String token) {
        return getClaims(token).get("permissions", List.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT");
        } catch (IllegalArgumentException e) {
            System.out.println("Empty JWT claims");
        }
        return false;
    }
}
