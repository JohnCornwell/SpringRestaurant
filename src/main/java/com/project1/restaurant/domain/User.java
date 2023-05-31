package com.project1.restaurant.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the user database table.
 *
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  private long userId;
  private String firstName;
  private String lastName;
  private String password;
  private String permission;
  private String username;
  private Boolean firstLogin;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", unique = true, nullable = false)
  public long getUserId() {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  @Column(name = "first_name", nullable = false, length = 64)
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "last_name", nullable = false, length = 64)
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name = "first_login", nullable = false)
  public Boolean getFirstLogin() {
    return this.firstLogin;
  }

  public void setFirstLogin(Boolean firstLogin) {
    this.firstLogin = firstLogin;
  }

  @Column(nullable = false, length = 256)
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(nullable = false, length = 32)
  public String getPermission() {
    return this.permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  @Column(nullable = false, length = 128)
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Map<String, String> toJSON() {
    Map<String, String> res = new HashMap<>();
    res.put("user_id", String.valueOf(this.getUserId()));
    res.put("first_name", this.getFirstName());
    res.put("last_name", this.getLastName());
    res.put("password", "[PROTECTED]");
    res.put("permission", this.getPermission());
    res.put("username", this.getUsername());
    res.put("first_login", String.valueOf(this.getFirstLogin()));
    return res;
  }
}
