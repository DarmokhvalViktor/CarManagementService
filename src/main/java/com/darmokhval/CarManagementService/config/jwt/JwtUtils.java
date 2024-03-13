package com.darmokhval.CarManagementService.config.jwt;

import com.darmokhval.CarManagementService.exception.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final String jwtSecret = "aHbJkLmNpQrStUvWxYz1234567890ABCDEabcdeFGHIfghijJKLMnopqRUVWxyzf";
    private final int jwtAccessTokenExpirationMs = 3600000;
    private final int jwtRefreshTokenExpirationMs = 86400000;

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(Map.of("roles", getRoles(userDetails), "type", "access","iss", "carManagementService"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtAccessTokenExpirationMs))
                .signWith(key())
                .compact();
    }
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(Map.of("roles", getRoles(userDetails), "type", "refresh", "iss", "carManagementService"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtRefreshTokenExpirationMs))
                .signWith(key())
                .compact();
    }
    private List<String> getRoles(UserDetails userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(key()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtValidationException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtValidationException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is not supported: {}", e.getMessage());
            throw new JwtValidationException("JWT token is not supported");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty: {}", e.getMessage());
            throw new JwtValidationException("JWT claims is empty");
        }

    }
    public boolean isRefreshToken(String token) {
        return resolveClaim(token, claims -> Objects.equals(claims.get("type", String.class), "refresh"));
    }
    public boolean isMyCustomToken(String token) {
        return resolveClaim(token, claims -> Objects.equals(claims.get("iss", String.class), "carManagementService"));
    }
    public Date extractExpiration(String token) {
        return resolveClaim(token, Claims::getExpiration);
    }
    private <T> T resolveClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts
                .parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
}

