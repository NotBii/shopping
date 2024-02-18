package com.zerobase.shopping.order.dto;

import com.zerobase.shopping.dto.ProductDto;
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
  private HashMap<ProductDto, Integer> product;
  private long cost;
  private LocalDateTime createdDate;

}
