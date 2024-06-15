package com.io.greenscan.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey() {
        byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String createToken(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * 60 * 60 * 24); // 1 day

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void check(String token) throws JwtException {
        log.debug("Checking token: {}", token);
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public String getUserEmail(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);  // Remove "Bearer " prefix
            }
            byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY);
            Key signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            return body.getSubject();  // 토큰의 subject로 사용자 이메일을 저장하는 것이 일반적입니다.
        } catch (JwtException e) {
            // 토큰 파싱에 실패하거나 유효하지 않은 경우 처리
            log.error("Invalid JWT token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, String userEmail) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);  // Remove "Bearer " prefix
            }
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.debug("Token subject: {}", claims.getSubject());
            log.debug("Expected subject: {}", userEmail);
            return claims.getSubject().equals(userEmail) && !isTokenExpired(claims.getExpiration());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT validation error: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(Date expiration) {
        boolean expired = expiration.before(new Date());
        log.debug("Token expired: {}", expired);
        return expired;
    }
}
