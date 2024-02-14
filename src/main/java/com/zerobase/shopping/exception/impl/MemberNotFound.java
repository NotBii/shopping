package com.zerobase.shopping.exception.impl;

import com.zerobase.shopping.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class MemberNotFound extends AbstractException {


  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "아이디가 존재하지 않습니다";
  }
}
