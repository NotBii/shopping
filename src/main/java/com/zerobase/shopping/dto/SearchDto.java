package com.zerobase.shopping.dto;

import com.zerobase.shopping.commons.paging.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
  private int page;
  private final int listSize = 10;
  private final int pageSize = 10;
  private String word;
  private String searchType;
  private Pagination pagination;



  public int getOffset() {
    return (page - 1) * listSize;
  }

}
