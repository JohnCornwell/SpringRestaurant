package com.project1.restaurant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.restaurant.domain.Item;
import com.project1.restaurant.repositories.ItemRepository;

@Service
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  public List<Item> getAll() {
    return itemRepository.findAll();
  }

  public Item findByItemId(Long id) {
    return itemRepository.findByItemId(id);
  }

  public void addItem(String name, double price, String photoLocation, byte availability) {
    Item myItem = new Item();
    myItem.setName(name);
    myItem.setPrice(price);
    myItem.setPhotoLocation(photoLocation);
    myItem.setAvailability(availability);
    itemRepository.save(myItem);
  }

  public String updateItem(long itemId, String name, double price, String photoLocation, byte availability) {
    Item myItem = itemRepository.findByItemId(itemId);
    String oldPhoto = myItem.getPhotoLocation();
    myItem.setName(name);
    myItem.setPrice(price);
    if(!photoLocation.equals("")) {
      myItem.setPhotoLocation(photoLocation);
    }
    myItem.setAvailability(availability);
    itemRepository.save(myItem);
    return oldPhoto;
  }

  public Item deleteItem(long id){
    Item myItem = itemRepository.findByItemId(id);
    if(myItem == null) {
      return null;
    }

    itemRepository.delete(myItem);
    return myItem;
  }
}
