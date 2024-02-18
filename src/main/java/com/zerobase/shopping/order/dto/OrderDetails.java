package com.zerobase.shopping.order.dto;

import com.zerobase.shopping.product.dto.ProductSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
  private Long orderDetailsId;
  private Long orderId;
  private ProductSummary product;
  private int count;
  private String status;
  private Long cost;

}
