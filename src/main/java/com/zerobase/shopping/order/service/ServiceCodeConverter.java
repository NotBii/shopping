package com.zerobase.shopping.order.service;

import com.zerobase.shopping.commons.exception.impl.CodeNotFit;


public class ServiceCodeConverter {
  public static class payCheckConverter {

     public Integer textToCode(String text) {
      if ("결제대기중".equals(text)) {
        return 0;
      } else if ("결제완료".equals(text)) {
        return 1;
      }
      throw new CodeNotFit();
    }

    public String codeToText(Integer code) {
      if (0 == code) {
        return "결제대기중";
      } else if (1 == code) {
        return "결제완료";
      }
      throw new CodeNotFit();
    }
  }
  public static class statusConverter {

    public Integer textToCode(String text) {
      if ("결제대기중".equals(text)) {
        return 0;
      } else if ("상품준비중".equals(text)) {
        return 1;
      } else if ("배송중".equals(text)) {
        return 2;
      } else if ("배송완료".equals(text)) {
        return 3;
      }
      throw new CodeNotFit();
    }

    public String codeToText(Integer code) {
      if (code == 0) {
        return "결제대기중";
      } else if (code == 1) {
        return "상품준비중";
      } else if (code == 2) {
        return "배송중";
      } else if (code == 3) {
        return "배송완료";
      }
      throw new CodeNotFit();
    }
  }
}
