package com.zerobase.shopping.order.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListRequest {
  private int page;
  private LocalDateTime from;
  private LocalDateTime to;
}
