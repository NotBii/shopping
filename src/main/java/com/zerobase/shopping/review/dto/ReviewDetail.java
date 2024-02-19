package com.zerobase.shopping.review.dto;

import com.zerobase.shopping.img.dto.ImgDto;
import java.time.LocalDateTime;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetail {
  private long reviewId;

  private String writer;

  @Default
  private List<ImgDto> imgList = new ArrayList<>();

  private String title;

  private String content;

  private int isDeleted;

  private int score;

  private LocalDateTime createdDate;

  private LocalDateTime modifiedDate;

  private long recommended;



}
