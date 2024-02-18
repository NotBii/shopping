package com.zerobase.shopping.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatusRequest {
  private Long orderId;
  private String status;
}
