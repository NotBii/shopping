package com.zerobase.shopping.product.dto;

import com.zerobase.shopping.img.dto.ImgDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

  private long productId;

  private long price;

  private String title;

  private String content;

  private int stock;

  private LocalDateTime createdDate;

  private LocalDateTime modifiedDate;

  private List<ImgDto> imgList;

}
