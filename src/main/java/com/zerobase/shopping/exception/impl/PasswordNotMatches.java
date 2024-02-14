package com.zerobase.shopping.exception.impl;

import com.zerobase.shopping.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatches extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "아이디와 패스워드를 확인해 주세요";
  }
}
