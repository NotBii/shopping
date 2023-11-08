package com.zerobase.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {
  private int no;
  private int price;
  private String title;
  private String content;
  private int stock;
  private String date;
  private String modifiedDate;
  private String img;
}
