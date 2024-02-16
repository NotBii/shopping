package com.zerobase.shopping.product.dto;

import com.zerobase.shopping.imgUpload.dto.ImgDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProduct {
  private long productId;
  private long price;
  private String title;
  private String content;
  private int stock;
  private LocalDateTime date;
  private LocalDateTime modifiedDate;
  private List<ImgDto> imgList;

}
