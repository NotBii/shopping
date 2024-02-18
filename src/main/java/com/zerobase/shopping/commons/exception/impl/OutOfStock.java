package com.zerobase.shopping.commons.exception.impl;

import com.zerobase.shopping.commons.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class OutOfStock extends AbstractException {

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @Override
  public String getMessage() {
    return "재고가 소진되었습니다";
  }
}
