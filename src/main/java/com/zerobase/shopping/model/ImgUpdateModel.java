package com.zerobase.shopping.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * remove : 제거된 이미지 여부 (있으면 1, 없으면 0)
 * add : 추가된 이미지 있음 (위와 동일)
 * updatedImgs : 기존 글의 img list에서 수정된 이미지
 */
@Component
@Builder
@Data
public class ImgUpdateModel {
  private int remove;
  private int add;
  private String updatedImgs;



}
