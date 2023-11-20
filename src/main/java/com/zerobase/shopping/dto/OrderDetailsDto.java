package com.zerobase.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetailsDto {
  private int no;
  private int orderNo;
  private int productNo;
  private int productPrice;
  private int productCount;
  private String name;
  private String status;


}
