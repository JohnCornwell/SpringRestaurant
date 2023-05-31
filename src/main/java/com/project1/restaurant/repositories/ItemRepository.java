package com.project1.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.restaurant.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
  List<Item> findAll();
  Item findByItemId(Long id);
}
