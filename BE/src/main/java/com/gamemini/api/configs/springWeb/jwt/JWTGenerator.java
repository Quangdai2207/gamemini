package com.gamemini.api.configs.springWeb.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/// Tao token
@Component
public class JWTGenerator {
//    private static final String rawKey = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";
    /// Create secret-key bang cau lenh cua chuong trinh openssl: openssl rand -hex 32
    private static final String rawKey = "8cbcd00526ad8c0794326b99363db523a379577db9378a6f84f98e81e4dab739";
    private static final Key secretKey = Keys.hmacShaKeyFor(rawKey.getBytes(StandardCharsets.UTF_8));

    /// Tao Token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstant.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        System.out.println("New token : " + token);
        return token;
    }

    ///  Lay Username tu Token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /// Xac thuc token:
    ///  + xac thuc thoi giann song cua token (expiration)
    ///  + xac thuc chu ky
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            System.out.println("JWT ERROR: " + ex.getMessage());
            return false;
        }
    }
}
