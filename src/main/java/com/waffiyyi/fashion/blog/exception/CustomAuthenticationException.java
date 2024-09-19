package com.waffiyyi.fashion.blog.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class CustomAuthenticationException extends AuthenticationException {
  public CustomAuthenticationException(String msg) {
    super("Authentication failed. Please check your credentials and try again.");
  }

  public CustomAuthenticationException(String msg, Throwable t) {
    super(msg, t);
  }
}