package com.zerobase.shopping.order.dto;

import com.zerobase.shopping.product.dto.ProductSummary;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
  private Long orderId;
  private String title;
  private String address;
  private String phone;
  private String payCheck;
  private String status;
  private String recipient;
  private HashMap<ProductSummary, Integer> product;
  private long cost;
  private LocalDateTime createdDate;

}
