package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.OrderDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetailsModel {
  private int no;
  private int orderNo;
  private int productNo;
  private int productPrice;
  private int productCount;
  private String name;
  private String status;

  public OrderDetailsDto toDto() {
    return OrderDetailsDto.builder()
        .no(this.no)
        .orderNo(this.orderNo)
        .productNo(this.productNo)
        .productPrice(this.productPrice)
        .productCount(this.productCount)
        .name(this.name)
        .status(this.status)
        .build();
  }

}
