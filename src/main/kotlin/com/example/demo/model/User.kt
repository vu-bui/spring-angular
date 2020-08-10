package com.example.demo.model

import javax.persistence.*

@Entity
@Table(name = "user", schema = "public")
data class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  override val id: Long = 0,
  var username: String? = null,
  var email: String? = null
) : BaseModel
