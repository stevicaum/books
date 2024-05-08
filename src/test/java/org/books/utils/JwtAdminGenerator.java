package org.books.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.books.config.utils.JwtTokenUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.books.config.security.UserProvider.ADMIN_DETAILS;
import static org.books.config.utils.JwtTokenUtil.SIGNING_KEY;

public class JwtAdminGenerator implements Serializable {

  public static void main(String[] args) {
    System.out.println(JwtAdminGenerator.generateToken(ADMIN_DETAILS, 360000));
  }


  public static String generateToken(UserDetails user, long validitySeconds) {
    Claims claims = Jwts.claims().subject(user.getUsername()).add("authorities", user.getAuthorities()).build();
    return Jwts.builder()
            .setClaims(claims)
            .setIssuer("http://books.com")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + validitySeconds * 1000))
            .signWith(SignatureAlgorithm.HS256, SIGNING_KEY.getBytes())
            .compact();
  }
}