package com.example.demo.auth

import com.example.demo.service.UserService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class Auth

@Aspect
@Component
class AuthAspect(private val userService: UserService) {
  /**
   * Check for currentUser before going to the method annotated with @Auth.
   */
  @Around("execution(@com.example.demo.auth.Auth * *.*(..))")
  fun authPointcut(joinPoint: ProceedingJoinPoint) {
    userService.currentUser?.also { joinPoint.proceed() } ?: throw UnauthorizedException()
  }
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedException : Exception()
