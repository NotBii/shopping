package com.zerobase.shopping.commons.exception.impl;

import com.zerobase.shopping.commons.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class CodeNotFit extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "코드를 확인해주세요";
  }

}