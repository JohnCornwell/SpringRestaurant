package com.project1.restaurant.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.restaurant.domain.PhoneNumber;
import com.project1.restaurant.services.PhoneNumberService;

@RestController
@RequestMapping("")
public class PhoneNumberController {

  @Autowired
  private PhoneNumberService phoneNumberService;

  @PreAuthorize("hasRole('Manager')")
  @PutMapping(path = "/contact_info/{contact_name}/{number}", produces = "application/json")
  public ResponseEntity<PhoneNumber> getLikeUsername(@PathVariable("contact_name") String contactName,
   @PathVariable String number) {
    PhoneNumber contact = phoneNumberService.addContact(contactName, number);
    return new ResponseEntity<>(contact, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('Manager')")
  @DeleteMapping(path = "/contact_info/{phone_id}", produces = "application/json")
  public ResponseEntity<Map<String, String>> getLikeUsername(@PathVariable("phone_id") Long id) {
    PhoneNumber contact = phoneNumberService.deleteContact(id);
    Map<String, String> res = new HashMap<>();
    if(contact != null) {
      res.put("message", "Delete successful.");
      return new ResponseEntity<>(res, HttpStatus.OK);
    }else {
      res.put("message", "Contact does not exist.");
      return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path = "/contact_info", produces = "application/json")
  public ResponseEntity<List<Map<String, String>>> getContacts() {
    List<PhoneNumber> contacts = phoneNumberService.getAll();
    List<Map<String, String>> contactList = new ArrayList<>();
    for(PhoneNumber c : contacts) {
      HashMap<String, String> mapping = new HashMap<>();
      mapping.put("phone_id", String.valueOf(c.getPhoneId()));
      mapping.put("contact_name", c.getContactName());
      mapping.put("number", c.getNumber());
      contactList.add(mapping);
    }
    return new ResponseEntity<>(contactList, HttpStatus.OK);
  }

}
