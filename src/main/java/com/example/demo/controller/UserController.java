package com.example.demo.controller;

import com.example.demo.auth.Auth;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping()
  public Iterable<User> getUsers() {
    return this.userService.getUsers();
  }

  @Auth
  @RequestMapping("/me")
  public User getCurrentUser() {
    return this.userService.getCurrentUser();
  }
}
