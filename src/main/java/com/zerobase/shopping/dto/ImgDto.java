package com.zerobase.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ImgDto {
  private int no;
  private String fileName;
  private String savedFileName;
  private Long size;
  private String date;

}
