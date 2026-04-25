package JobPortal.SpringJobPortal.Security;

import JobPortal.SpringJobPortal.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretkey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningkey(){
        return Keys.hmacShaKeyFor(secretkey.getBytes());
    }

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date())
                .signWith(getSigningkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        return Jwts.parser()
                .setSigningKey(getSigningkey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }



    public Boolean isTokenValid(String token, User user){
        String email = extractUserName(token);
        return email.equals(user.getEmail())
                &&isToken
    }

}
