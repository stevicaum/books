package org.books.utils;

import org.books.config.utils.JwtTokenUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.books.config.security.UserProvider.USER_DETAILS;

public class JwtUserGenerator implements Serializable {

  public static void main(String[] args) {
    System.out.println(JwtAdminGenerator.generateToken(USER_DETAILS, 3600));
  }

}