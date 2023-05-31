package com.project1.restaurant;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
  

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  UnauthenticatedRequestHandler unauthenticatedRequestHandler() {
    return new UnauthenticatedRequestHandler();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .sessionManagement(management ->
        management.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
      )
      .authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
    http.csrf(csrf -> csrf.disable());
    http.httpBasic(Customizer.withDefaults());
    // only allow requests from localhost
    http.cors(Customizer.withDefaults());
    http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
    // Allows for authentication with headers per request. Needed to get generation of /logout path
    http.formLogin(login -> login.disable());
    http.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthenticatedRequestHandler()));
    return http.build();
  }

  /*
   * This Bean removes the "ROLE_" prefix from the role checker for @PreAuthorize checks. 
   */
  @Bean
  GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
  }

  /*
   * Disable redirect to login page when attempting to access resources that 
   * need authentication.
   */
  static class UnauthenticatedRequestHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
      response.setStatus(401);
    }
}
}
