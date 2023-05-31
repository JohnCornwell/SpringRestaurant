package com.project1.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project1.restaurant.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findAll();
  User findByUserId(Long id);
  User findByUsername(String username);
  List<User> findByPermission(String permission);
  @Query(value = "SELECT * FROM user WHERE first_name LIKE %?1% OR last_name LIKE %?1%", nativeQuery = true)
  List<User> findLikeName(String name);
}
