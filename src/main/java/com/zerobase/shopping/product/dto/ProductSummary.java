package com.zerobase.shopping.product.dto;

import com.zerobase.shopping.img.dto.ImgDto;
import java.time.LocalDateTime;
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
public class ProductSummary {

  private long productId;
  private long price;
  private String title;
  private int stock;
  private LocalDateTime createdDate;
  private ImgDto img;


}
