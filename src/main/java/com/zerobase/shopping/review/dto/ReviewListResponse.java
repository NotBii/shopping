package com.zerobase.shopping.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReviewListResponse {
  private long reviewId;
  private String writer;
  private String title;
  private String content;
  private int isDeleted;
  private int score;
  private LocalDateTime createdDate;


}
