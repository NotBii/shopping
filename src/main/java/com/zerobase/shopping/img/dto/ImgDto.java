package com.zerobase.shopping.img.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgDto {
  private long imgId;
  private String originalName;
  private String savedName;
  private Long size;
  private LocalDateTime createAt;

}
