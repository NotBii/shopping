package com.zerobase.shopping.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
  private long productId;
  private long price;
  private String title;
  private String content;
  private int stock;
  private String date;
  private String modifiedDate;
  private String img;
  private int deleteYn;

}
