package com.zerobase.shopping.review.dto;

import com.zerobase.shopping.img.dto.ImgDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewWriteRequest {

  private long reviewId;
  private String writer;
  private String title;
  private String content;
  private long productId;
  @Default
  private List<ImgDto> imgList = new ArrayList<>();
  private long readCount;
  private int score;
  private int isDeleted;


}
