package com.project1.restaurant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.restaurant.domain.PhoneNumber;
import com.project1.restaurant.repositories.PhoneNumberRepository;

@Service
public class PhoneNumberService {

  @Autowired
  private PhoneNumberRepository phoneNumberRepository;

  public List<PhoneNumber> getAll() {
    return phoneNumberRepository.findAll();
  }

  public PhoneNumber findByPhoneId(Long id) {
    return phoneNumberRepository.findByPhoneId(id);
  }

  public PhoneNumber addContact(String contactName, String number) { 
    PhoneNumber contact = new PhoneNumber();
    contact.setContactName(contactName);
    contact.setNumber(number);
    phoneNumberRepository.save(contact);
    return contact;
  }

  public PhoneNumber deleteContact(Long id) {
    PhoneNumber contact = phoneNumberRepository.findByPhoneId(id);
    if(contact != null){
      phoneNumberRepository.delete(contact);
    }
    return contact;
  }
}
