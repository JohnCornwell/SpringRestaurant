package com.project1.restaurant.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the item database table.
 *
 */
@Entity
@Table(name = "item")
@NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
public class Item implements Serializable {

  private static final long serialVersionUID = 1L;
  private long itemId;
  private byte availability;
  private String name;
  private String photoLocation;
  private double price;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id", unique = true, nullable = false)
  public long getItemId() {
    return this.itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

  @Column(nullable = false)
  public byte getAvailability() {
    return this.availability;
  }

  public void setAvailability(byte availability) {
    this.availability = availability;
  }

  @Column(nullable = false, length = 128)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Lob
  @Column(name = "photo_location", nullable = false)
  public String getPhotoLocation() {
    return this.photoLocation;
  }

  public void setPhotoLocation(String photoLocation) {
    this.photoLocation = photoLocation;
  }

  @Column(nullable = false)
  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Map<String, String> toJSON() {
    Map<String, String> res = new HashMap<>();
    res.put("item_id", String.valueOf(this.getItemId()));
    res.put("name", this.getName());
    res.put("price", String.valueOf(this.getPrice()));
    res.put("photo_location", this.getPhotoLocation());
    res.put("availability", String.valueOf(this.getAvailability()));
    return res;
  }
}
