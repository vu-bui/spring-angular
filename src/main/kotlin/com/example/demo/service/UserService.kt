package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService constructor(private val userRepository: UserRepository) {
  private val _currentUser = ThreadLocal<User>()
  val currentUser: User?
    get() = _currentUser.get()

  fun login() {
    _currentUser.set(userRepository.findById(1L).orElse(null))
  }

  fun logout() {
    _currentUser.remove()
  }

  val users: Iterable<User>
    get() = userRepository.findAll()

  fun createUser(user: User): User {
    return userRepository.save(user)
  }
}
