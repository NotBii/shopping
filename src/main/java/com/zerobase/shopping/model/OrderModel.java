package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderModel {
  private int no;
  private String name;
  private String address;
  private String phone;
  private int payCheck;
  private String status;
  private String recipient;
  private long cost;

  public OrderDto toDto() {
    return OrderDto.builder()
        .no(this.no)
        .name(this.name)
        .address(this.address)
        .phone(this.phone)
        .payCheck(this.payCheck)
        .status(this.status)
        .recipient(this.recipient)
        .cost(this.cost)
        .build();
  }
}
