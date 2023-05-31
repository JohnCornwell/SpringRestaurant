package com.project1.restaurant.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The persistent class for the order database table.
 *
 */
@Entity
@Table(name = "order")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order implements Serializable {

  private static final long serialVersionUID = 1L;
  private long orderId;
  private String email;
  private double total;
  private List<ListItem> listItems;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id", unique = true, nullable = false)
  public long getOrderId() {
    return this.orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  @Column(nullable = false, length = 128)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(nullable = false)
  public double getTotal() {
    return this.total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  //bi-directional many-to-one association to ListItem
  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
  public List<ListItem> getListItems() {
    return this.listItems;
  }

  public void setListItems(List<ListItem> listItems) {
    this.listItems = listItems;
  }

  public ListItem addListItem(ListItem listItem) {
    getListItems().add(listItem);
    listItem.setOrder(this);

    return listItem;
  }

  public ListItem removeListItem(ListItem listItem) {
    getListItems().remove(listItem);
    listItem.setOrder(null);

    return listItem;
  }
}
