package com.project1.restaurant.domain;

import lombok.Data;

@Data
/**
 * A simple class to collect data from a login request.
 */
public class LoginDTO {

  private String username;
  private String password;
}
