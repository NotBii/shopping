package com.zerobase.shopping.commons.exception.impl;

import com.zerobase.shopping.commons.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class DuplicatedNickname extends AbstractException {


  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "이미 존재하는 닉네임입니다";
  }

}
