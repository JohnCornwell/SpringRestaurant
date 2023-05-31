package com.project1.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.restaurant.domain.ListItem;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {
  List<ListItem> findAll();
  ListItem findByListId(Long id);
}
