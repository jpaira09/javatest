package org.dtna.authoriser.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.dtna.authoriser.dto.AuthDto;
import org.dtna.authoriser.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Value("${token_email}")
    public String email;
    @Value("${token_password}")
    public String password;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    private static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(AuthDto authDto) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        // Generate the JWT token
        return Jwts.builder()
                .setSubject(authDto.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("email", authDto.getEmail()) // Add email as a claim
                .signWith(key) // Sign the token
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            // Parse and validate the token
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Token is malformed", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Token signature is invalid", e);
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed", e);
        }
    }

}
