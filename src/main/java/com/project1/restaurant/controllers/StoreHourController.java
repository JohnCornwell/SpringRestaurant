package com.project1.restaurant.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.restaurant.domain.StoreHour;
import com.project1.restaurant.services.StoreHourService;

@RestController
@RequestMapping("hours")
public class StoreHourController {

  @Autowired
  private StoreHourService storeHourService;

  @GetMapping(path = "", produces = "application/json")
  public List<Map<String, String>> getHours() {
    List<StoreHour> hours = storeHourService.getAll();
    ArrayList<Map<String, String>> formatted = new ArrayList<>();
    hours.forEach(times -> {
        formatted.add(new HashMap<>());
        formatted.get(formatted.size() - 1).put("day_of_week", Integer.toString(times.getDayOfWeek()));
        formatted.get(formatted.size() - 1).put("open", times.getOpen().toString().substring(0, 5));
        formatted.get(formatted.size() - 1).put("close", times.getClose().toString().substring(0, 5));
    });
    return formatted;
  }

  @PreAuthorize("hasRole('Employee')")
  @PatchMapping(path = "{day}/{open}/{close}", produces = "application/json")
  public ResponseEntity<Map<String, String>> updateHours(@PathVariable("day") int day, 
  @PathVariable("open") String open, @PathVariable("close") String close) {
    boolean success = storeHourService.updateHours(day, open, close);
    Map<String, String> res = new HashMap<>();
    if(success) {
      res.put("message", "Store hours updated.");
      return new ResponseEntity<>(res, HttpStatus.OK);
    } else {
      res.put("message", "Day or hours value invalid.");
      return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
  }

}
