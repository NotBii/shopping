package com.zerobase.shopping.commons;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Utils {

  /**
   *
   * @param str 리스트로 변환할 문자열 (이미지번호 목록)
   * @return
   */
  public List<Long> stringToList(String str) {
    String[] arr= str.split(",");
    ArrayList<String> list = new ArrayList<>(Arrays.asList(arr));
    List<Long> newList = list.stream()
        .map(s -> Long.parseLong(s)).collect(Collectors.toList());

    return newList;
  }

}
