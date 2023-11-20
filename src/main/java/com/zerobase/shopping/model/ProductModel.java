package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProductModel {
  private int no;
  private int price;
  private String title;
  private String content;
  private int stock;
  private String date;
  private String modifiedDate;
  private String img;

  public ProductDto toDto() {
    return ProductDto.builder()
        .no(this.no)
        .price(this.price)
        .title(this.title)
        .content(this.content)
        .stock(this.stock)
        .date(this.date)
        .modifiedDate(this.modifiedDate)
        .img(this.img)
        .build();
  }
}
