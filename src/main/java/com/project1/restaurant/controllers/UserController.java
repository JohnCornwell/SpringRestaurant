package com.project1.restaurant.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.restaurant.domain.User;
import com.project1.restaurant.services.UserService;

@RestController
@RequestMapping("")
public class UserController {

  @Autowired
  private UserService userService;

  private final String message = "message";

  @PreAuthorize("hasAnyRole('Employee','Manager')")
  @GetMapping(path = "/status", produces = "application/json")
  public Map<String, String> status() {
    SecurityContext cx = SecurityContextHolder.getContext();
    HashMap<String, String> res = new HashMap<>();
    res.put(message, cx.getAuthentication().getAuthorities().iterator().next().toString());
    return res;
  }

  @PreAuthorize("hasAnyRole('Employee','Manager')")
  @GetMapping(path = "/user", produces = "application/json")
  public ResponseEntity<Map<String, String>> currentUser() {
    SecurityContext cx = SecurityContextHolder.getContext();
    UserDetails user = (UserDetails)cx.getAuthentication().getPrincipal();
    User myUser = userService.findByUsername(user.getUsername());
    Map<String, String> res = myUser.toJSON();
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('Employee','Manager')")
  @PostMapping(path = "/user/password", produces = "application/json")
  public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Map<String, String> json) {
    SecurityContext cx = SecurityContextHolder.getContext();
    UserDetails user = (UserDetails)cx.getAuthentication().getPrincipal();
    Map<String, String> res = new HashMap<>();
    String newPass = json.get("new_password");
    String oldPass = json.get("old_password");
    if(oldPass == null || newPass == null) {
      res.put(message, "Must provide an old_password and a new_password");
      return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    boolean success = userService.changePassword(user.getUsername(), oldPass, newPass);
    if(success) {
      res.put(message, "Update successful");
      return new ResponseEntity<>(res, HttpStatus.OK);
    }else{
      res.put(message, "Incorrect Password");
      return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }
  }

  @PreAuthorize("hasRole('Manager')")
  @PostMapping(path = "/user/create", produces = "application/json")
  public ResponseEntity<Map<String, String>> createUser(@RequestBody Map<String, String> json) {
    String firstName = json.get("first_name");
    String lastName = json.get("last_name");
    String email = json.get("email");
    Map<String, String> res = new HashMap<String, String>();
    if(firstName == null || lastName == null || email == null){
      res.put(message, "Must provide a first_name, last_name, and email");
      return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    try{
      userService.createUser(firstName, lastName, email);
      res.put(message, "User Created successfully.");
      return new ResponseEntity<>(res, HttpStatus.OK);
    }catch(MailException e){
      userService.createUser(firstName, lastName, email);
      res.put(message, "Unable to send confirmation email.");
      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasRole('Manager')")
  @GetMapping(path = "/user/list", produces = "application/json")
  public List<Map<String, String>> getEmployees() {
    List<User> users = userService.getEmployees();
    List<Map<String, String>> res = new ArrayList<>();
    users.forEach(user -> res.add(user.toJSON()));
    return res;
  }

  @PreAuthorize("hasRole('Manager')")
  @GetMapping(path = "/user/search/{name}", produces = "application/json")
  public List<Map<String, String>> getLikeUsername(@PathVariable String name) {
    List<User> users =  userService.getLikeName(name);
    List<Map<String, String>> res = new ArrayList<>();
    users.forEach(user -> res.add(user.toJSON()));
    return res;
  }

  @PreAuthorize("hasRole('Manager')")
  @DeleteMapping(path = "user/{id}", produces = "application/json")
  public ResponseEntity<HttpStatus> getUser(@PathVariable long id) {
    boolean success = userService.deleteUser(id);
    if (success) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
