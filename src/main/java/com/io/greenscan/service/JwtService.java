package com.io.greenscan.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private Key signingKey;
    private SignatureAlgorithm signatureAlgorithm;
    Map<String, Object> body = new HashMap<>();


    /*
    [토큰 발행]
    -userEmail과 만료시간을 받아온다.
    -토큰을 발행한다.
     */
    public String createToken(String userEmail) {
        Map<String, Object> body = new HashMap<>();
        body.put("userEmail", userEmail);
        Claims claims = Jwts.claims().setSubject(userEmail);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        long nowTime = System.currentTimeMillis();
        builder.setExpiration(new Date(nowTime + 1000 * 60 * 60 * 24));

        return builder.compact();
    }

    public String check(String token) throws Exception {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        Claims body = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();

        return body.toString();
    }




    public String getUserEmail(String token) {
        try {
            byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            Key signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            return body.getSubject(); // 토큰의 subject로 사용자 이메일을 저장하는 것이 일반적입니다.
        } catch (Exception e) {
            // 토큰 파싱에 실패하거나 유효하지 않은 경우 처리
            e.printStackTrace();
            return null;
        }
    }

}

