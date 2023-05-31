package com.project1.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.restaurant.domain.PhoneNumber;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
  List<PhoneNumber> findAll();
  PhoneNumber findByPhoneId(Long id);
}
