package com.example.demo.controller

import com.example.demo.auth.Auth
import com.example.demo.model.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
  @get:GetMapping
  val users: Iterable<User>
    get() = userService.users

  @PostMapping
  fun createUser(@RequestBody @Valid user: User): User {
    return userService.createUser(user)
  }

  @get:GetMapping("/me")
  @get:Auth
  val currentUser: User?
    get() = userService.currentUser
}
