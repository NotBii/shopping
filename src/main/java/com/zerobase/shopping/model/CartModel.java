package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.CartDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CartModel {
  private int no;
  private LocalDateTime date;
  private String name;
  private int productNo;
  private int productCount;

  public CartDto toDto() {
    return CartDto.builder()
        .no(this.no)
        .date(this.date)
        .name(this.name)
        .productNo(this.productNo)
        .productCount(this.productCount)
        .build();
  }

}
