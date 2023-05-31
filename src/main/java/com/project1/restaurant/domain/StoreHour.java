package com.project1.restaurant.domain;

import java.io.Serializable;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * The persistent class for the store_hours database table.
 *
 */
@Entity
@Table(name = "store_hours")
@NamedQuery(name = "StoreHour.findAll", query = "SELECT s FROM StoreHour s")
public class StoreHour implements Serializable {

  private static final long serialVersionUID = 1L;
  private long hoursId;
  private Time close;
  private byte dayOfWeek;
  private Time open;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "hours_id", unique = true, nullable = false)
  public long getHoursId() {
    return this.hoursId;
  }

  public void setHoursId(long hoursId) {
    this.hoursId = hoursId;
  }

  @Column(nullable = false)
  public Time getClose() {
    return this.close;
  }

  public void setClose(Time close) {
    this.close = close;
  }

  @Column(name = "day_of_week", unique=true, nullable = false)
  public byte getDayOfWeek() {
    return this.dayOfWeek;
  }

  public void setDayOfWeek(byte dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  @Column(nullable = false)
  public Time getOpen() {
    return this.open;
  }

  public void setOpen(Time open) {
    this.open = open;
  }
}
