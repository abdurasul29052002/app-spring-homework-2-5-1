package com.example.appspringhomework251.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    public static final long expireTime = 1000*60*60*24;
    public static final String secretWord = "ThisIsSecretWord";

    public String generateToken(String username){
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expireTime))
                .signWith(SignatureAlgorithm.HS512,secretWord)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
