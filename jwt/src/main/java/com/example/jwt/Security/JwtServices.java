package com.example.jwt.Security;

import com.example.jwt.Constants.AppConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServices {

    public SecretKey getSigningKey() {
        byte[] decode = Decoders.BASE64.decode(AppConstant.SECRET);
        return Keys.hmacShaKeyFor(decode);
    }

    public Long getExpirationTime() {
        return AppConstant.expirationTime;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.get("username"));
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails,AppConstant.expirationTime);
    }
    private String generateToken(UserDetails userDetails, Long expirationTime) {
        return Jwts.builder()
                .subject("Professional Page")
                .issuer("Abhijeet Tiwari")
                .claim("username", userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails details) {
        Claims claims = extractAllClaims(token);
        Object o = claims.get("username");
        Date expiration = claims.getExpiration();
        if (details.getUsername().equals(o) && expiration.after(new Date())) {
            return true;
        } else return false;
    }

}
