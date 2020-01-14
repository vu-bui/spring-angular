package com.example.demo.auth;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {
  private UserService userService;

  @Autowired
  public AuthAspect(UserService userService) {
    this.userService = userService;
  }

  /**
   * Check for currentUser before going to the method annotated with @Auth.
   */
  @Around("execution(@com.example.demo.auth.Auth * *.*(..))")
  public void authPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
    User currentUser = this.userService.getCurrentUser();

    if (currentUser != null) {
      joinPoint.proceed();
    }

    throw new UnauthorizedException();
  }
}
