package com.resume.builder.reume_builder.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class jwtUtils {
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  public String generateToken(String username) {
    Date date = new Date();
    Date expireDate = new Date(date.getTime() + jwtExpirationMs);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(date)
        .setExpiration(expireDate)
        .signWith(getSignInKey())
        .compact();

  }

  private javax.crypto.SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String getuserIdfromToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(getSignInKey())
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean istokenEpire(String token) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(getSignInKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
      Date expiration = claims.getExpiration();
      return expiration.before(new Date());

    } catch (Exception e) {
      return true;
    }
  }
}
