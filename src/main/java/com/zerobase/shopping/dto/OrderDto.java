package com.zerobase.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderDto {
  private int no;
  private String name;
  private String address;
  private String phone;
  private int payCheck;
  private String status;
  private String recipient;
  private long cost;


}
