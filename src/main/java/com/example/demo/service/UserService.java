package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private UserRepository userRepository;
  private ThreadLocal<User> currentUser;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    currentUser = new ThreadLocal<>();
  }

  public User getCurrentUser() {
    return currentUser.get();
  }

  public void login() {
    currentUser.set(userRepository.findById(0L).orElse(null));
  }

  public void logout() {
    currentUser.remove();
  }

  public Iterable<User> getUsers() {
    return userRepository.findAll();
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }
}
