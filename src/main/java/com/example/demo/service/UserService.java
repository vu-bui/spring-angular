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
    this.currentUser = new ThreadLocal<User>();
  }

  public User getCurrentUser() {
    return this.currentUser.get();
  }

  public void login() {
    this.currentUser.set(this.userRepository.findById(0L).get());
  }

  public void logout() {
    this.currentUser.remove();
  }

  public Iterable<User> getUsers() {
    return this.userRepository.findAll();
  }
}
