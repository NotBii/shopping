package com.zerobase.shopping.commons.exception.impl;

import com.zerobase.shopping.commons.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class DuplicatedMail extends AbstractException {


  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "이미 가입된 메일주소입니다.";
  }
}