package com.project1.restaurant.services;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.restaurant.domain.StoreHour;
import com.project1.restaurant.repositories.StoreHourRepository;

@Service
public class StoreHourService {

  @Autowired
  private StoreHourRepository storeHourRepository;

  public List<StoreHour> getAll() {
    return storeHourRepository.findAll();
  }

  public StoreHour findByItemId(Long id) {
    return storeHourRepository.findByHoursId(id);
  }

  public boolean updateHours(int day, String open, String close) {
    if (day < 0 || day > 6 || !open.matches("^([0-1]\\d|2[0-3]):([0-5]\\d)$") || 
    !close.matches("^([0-1]\\d|2[0-3]):([0-5]\\d)$")) {
      return false;
    }
    Time newOpen = Time.valueOf(open + ":00");
    Time newClose = Time.valueOf(close + ":00");
    StoreHour myHours = storeHourRepository.findByDayOfWeek(day);
    myHours.setOpen(newOpen);
    myHours.setClose(newClose);
    storeHourRepository.save(myHours);
    return true;
  }
}
