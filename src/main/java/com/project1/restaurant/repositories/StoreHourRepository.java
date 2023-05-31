package com.project1.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.restaurant.domain.StoreHour;

public interface StoreHourRepository extends JpaRepository<StoreHour, Long> {
  List<StoreHour> findAll();
  StoreHour findByHoursId(Long id);
  StoreHour findByDayOfWeek(int day);
}
