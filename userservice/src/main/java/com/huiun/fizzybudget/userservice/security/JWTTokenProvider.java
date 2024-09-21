package com.huiun.fizzybudget.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecret;

    @Value("${JWT_EXPIRATION}")
    private long jwtExpiration;

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    // Generate JWT token
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        logger.info(userDetails.getUsername());
        logger.info(userDetails.getPassword());
        logger.info(jwtSecret);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Handle specific exceptions like ExpiredJwtException
            return false;
        }
    }
}
