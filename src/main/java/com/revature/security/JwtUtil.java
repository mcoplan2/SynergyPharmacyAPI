package com.revature.security;

import com.revature.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jdk.internal.org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource("application.properties")
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY = "eyJhbGciOiJIUzM4NCJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY1ODExMDk2OCwiaWF0IjoxNjU4MTEwOTY4fQ.apb-MphkYCm5vcegkVCPQsS95pIVk1bUOeWohXMY4CHvtx86aT4a2NioPGOZv7R8";

    private final String secretKeyEncoded = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

    Key key = new SecretKeySpec(secretKeyEncoded.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(User userDetails){
        Map<String, Object> claims = Collections.singletonMap("userId", userDetails.getUserId());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).signWith(key).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }
}