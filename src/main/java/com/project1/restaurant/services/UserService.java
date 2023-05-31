package com.project1.restaurant.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project1.restaurant.domain.User;
import com.project1.restaurant.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired 
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JavaMailSender emailSender;

  private static Random random = new Random();

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User findByUserId(Long id) {
    return userRepository.findByUserId(id);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean changePassword(String username, String oldPass, String newPass){
    User myUser = userRepository.findByUsername(username);
    if(passwordEncoder.matches(oldPass, myUser.getPassword())) {
      myUser.setPassword(passwordEncoder.encode(newPass));
      myUser.setFirstLogin(false);
      userRepository.save(myUser);
      return true;
    } else {
      return false;
    }
  }

  public void createUser(String firstName, String lastName, String email) {
    User myUser = new User();

    // generate a random username that is not taken
    String username = lastName;
    // add two random numbers to the end of the lastName
    username = username.concat(String.valueOf(random.nextInt(10)))
    .concat(String.valueOf(random.nextInt(10)));

    User duplicate = userRepository.findByUsername(username);
    while(duplicate != null){
      username = username.concat(String.valueOf(random.nextInt(10)));
      duplicate = userRepository.findByUsername(username);
    }

    //generate a random password

    String password = getRandomPassword();

    myUser.setFirstName(firstName);
    myUser.setLastName(lastName);
    myUser.setUsername(username);
    myUser.setPassword(passwordEncoder.encode(password));
    myUser.setPermission("Employee");
    myUser.setFirstLogin(true);
    userRepository.save(myUser);

    // send email confirmation with password
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setFrom("frontdash@outlook.com");
    message.setSubject("Employee Account Created");
    message.setText("Hello Manager!\nYour new employee " +
                    firstName + " " + lastName +
                    " has been created with username " + username +
                    " and password " + password);
    try{
      emailSender.send(message);
    }catch(MailException e){
      userRepository.delete(myUser);
      throw e;
    }
  }

  public boolean deleteUser(long id) {
    User myUser = userRepository.findByUserId(id);
    if(myUser == null) {
      return false;
    }

    userRepository.delete(myUser);
    return true;
  }

  public List<User> getEmployees() {
    return userRepository.findByPermission("Employee");
  }

  public List<User> getLikeName(String name) {
    return userRepository.findLikeName(name);
  }


  // Source: https://www.baeldung.com/java-random-string
  private static String getRandomPassword(){
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 15;

    return random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
  }
}
