package com.project1.restaurant.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the list_item database table.
 *
 */
@Entity
@Table(name = "list_item")
@NamedQuery(name = "ListItem.findAll", query = "SELECT l FROM ListItem l")
public class ListItem implements Serializable {

  private static final long serialVersionUID = 1L;
  private long listId;
  private String item;
  private double price;
  private Order order;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "list_id", unique = true, nullable = false)
  public long getListId() {
    return this.listId;
  }

  public void setListId(long listId) {
    this.listId = listId;
  }

  @Column(nullable = false, length = 256)
  public String getItem() {
    return this.item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  @Column(nullable = false)
  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  //bi-directional many-to-one association to Order
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  public Order getOrder() {
    return this.order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
