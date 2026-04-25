package JobPortal.SpringJobPortal.Security;

import JobPortal.SpringJobPortal.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
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

    public Boolean isTokenExpired(String token){
        Date expiry = extractAllCalims(token).getExpiration();

        return expiry.before(new Date());

    }
    private Claims extractAllCalims(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }




    public Boolean isTokenValid(String token, User user){
        String email = extractUserName(token);
        return email.equals(user.getEmail())
                &&isTokenExpired(token)
                &&user.getIsActive();
    }

}
