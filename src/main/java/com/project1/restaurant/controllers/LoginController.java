package com.project1.restaurant.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.restaurant.domain.LoginDTO;
import com.project1.restaurant.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("")
/*
 * This controller is responsible for servicing a login request. It provides
 * a username and password to the authenticationManager to create a new token.
 * See SecurityConfiguration.java for how the authentication manager validates this
 * data against the database.
 */
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> authenticateUser(
    HttpServletRequest req, @RequestBody LoginDTO loginDto
  ) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDto.getUsername(),
        loginDto.getPassword()
      )
    );

    // This code is only reachable if the authenticate() method is able to
    // validate the credentials. Else, a BadCredentialsException or other
    // Exception is thrown.
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(authentication);
    // Persist the authentication in the session. Spring will reload the auth into 
    // the security context on subsequent requests.
    HttpSession session = req.getSession();
    session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

    Map<String, String> res = userService.findByUsername(loginDto.getUsername()).toJSON();
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
