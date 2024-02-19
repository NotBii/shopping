package com.zerobase.shopping.inquiry.dto;

import com.zerobase.shopping.img.dto.ImgDto;
import java.time.LocalDateTime;
import java.util.List;
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
public class InquiryDetail {
  private Long inquiryId;
  private String writer;
  private String title;
  private String content;
  private Long productId;
  private List<ImgDto> imgList;
  private int isDeleted;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private long readCount;
  private Long parentId;
  private List<Long> childrenId;
  private int isSecret;


}
