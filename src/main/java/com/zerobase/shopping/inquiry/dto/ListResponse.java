package com.zerobase.shopping.inquiry.dto;

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
public class ListResponse {

  private long inquiryId;

  private String writer;

  private String title;

  private String content;

  private LocalDateTime createdDate;

  private long readCount;

  private int deleteYn;

  private long productId;

  private String productName;

}
