package com.project1.restaurant.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project1.restaurant.domain.Item;
import com.project1.restaurant.services.ItemService;

@RestController
@RequestMapping("menu")
public class ItemController {

  @Autowired
  private ItemService itemService;

  @GetMapping(path = "/list", produces = "application/json")
  public List<Map<String, String>> getAllItems() {
    List<Item> items = itemService.getAll();
    List<Map<String, String>> res = new ArrayList<>();
    items.forEach(item -> res.add(item.toJSON()));
    return res;
  }

  @PreAuthorize("hasRole('Employee')")
  @PostMapping(path = "/add", produces = "application/json")
  public ResponseEntity<Map<String, String>> addItem(@RequestParam("image") MultipartFile file,
  @RequestParam("name") String name, @RequestParam("price") double price, 
  @RequestParam("availability") byte availability) {
    Map<String, String> res = new HashMap<>();
    File dest;
    String photoLocation = StringUtils.cleanPath(file.getOriginalFilename());
    dest = new File("C:/public/images/" + photoLocation);

    if(dest.exists()){
      res.put("message", "File name is already in use.");
      return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    // copy the temp upload to its spot in the public/images folder
    try{
      file.transferTo(dest);
    }catch(IOException e) {
      e.printStackTrace();
      res.put("message", "Error saving food image");
      return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // save new food to database
    itemService.addItem(name, price, photoLocation, availability);

    res.put("message", "Food added.");
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('Employee')")
  @PostMapping(path = "/update", produces = "application/json")
  public ResponseEntity<Map<String, String>> updateItem(@RequestParam(name="image", required=false) 
  MultipartFile file, @RequestParam("item_id") long itemId, @RequestParam("name") String name, 
  @RequestParam("price") double price, @RequestParam("availability") byte availability) {
    Map<String, String> res = new HashMap<>();

    String photoLocation = "";

    // update includes a new photo
    if(file != null) { 
      File dest;
      photoLocation = StringUtils.cleanPath(file.getOriginalFilename());
      dest = new File("C:/public/images/" + photoLocation);

      if(dest.exists()){
        res.put("message", "File name is already in use.");
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
      }

      // copy the temp upload to its spot in the public/images folder
      try{
        file.transferTo(dest);
      }catch(IOException e) {
        e.printStackTrace();
        res.put("message", "Error saving food image");
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    // save new food to database
    String oldPhoto = itemService.updateItem(itemId, name, price, photoLocation, availability);

    // must delete old item image that is no longer in use
    if(file != null) {
      File itemImage = new File("C:/public/images/" + oldPhoto);
      if(itemImage.exists()) {
        itemImage.delete();
      }
    }

    res.put("message", "Food updated.");
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('Employee')")
  @DeleteMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
    Item myItem = itemService.deleteItem(id);
    if (myItem != null) {
      // delete the stored item image

      File itemImage = new File("C:/public/images/" + myItem.getPhotoLocation());
      if(itemImage.exists()) {
        itemImage.delete();
      }

      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  
}
