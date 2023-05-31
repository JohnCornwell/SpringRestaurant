package com.project1.restaurant.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the phone_number database table.
 *
 */
@Entity
@Table(name = "phone_number")
@NamedQuery(name = "PhoneNumber.findAll", query = "SELECT p FROM PhoneNumber p")
public class PhoneNumber implements Serializable {

  private static final long serialVersionUID = 1L;
  private long phoneId;
  private String contactName;
  private String number;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "phone_id", unique = true, nullable = false)
  public long getPhoneId() {
    return this.phoneId;
  }

  public void setPhoneId(long phoneId) {
    this.phoneId = phoneId;
  }

  @Column(name = "contact_name", nullable = false, length = 128)
  public String getContactName() {
    return this.contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  @Column(nullable = false, length = 24)
  public String getNumber() {
    return this.number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
