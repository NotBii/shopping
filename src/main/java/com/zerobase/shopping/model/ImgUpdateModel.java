package com.zerobase.shopping.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * remove : 제거된 이미지 여부 (있으면 1, 없으면 0)
 * add : 추가된 이미지 있음 (위와 동일)
 * removeNo : 삭제번호 list로 전달
 */
@Component
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgUpdateModel {
  private int remove;
  private int add;
  private List<Long> removeNo;



}
