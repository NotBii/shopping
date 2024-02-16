package com.zerobase.shopping.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean success;
  private T data;
  private Error error;

  public static <T> ResponseDto<T> success(T data) {
    return new ResponseDto<>(true, data, null);
  }
  public static <T> ResponseDto<T> successHeader(T data, HttpHeaders headers) {
    return new ResponseDto<>(true, data, null);
  }
  public static <T> ResponseDto<T> fail(String code, String message) {
    return new ResponseDto<>(false, null, new Error(code, message));
  }
  @Getter
  @AllArgsConstructor
  static class Error {
    private String code;
    private String message;
  }
}
