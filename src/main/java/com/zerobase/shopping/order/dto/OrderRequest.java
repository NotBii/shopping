package com.zerobase.shopping.order.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
  private String username;
  private String address;
  private String phone;
  private String payCheck;
  private String status;
  private String recipient;
  private String title;
  private ArrayList<ProductCount> product;
  private long cost;

}
