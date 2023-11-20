package com.zerobase.shopping.model;

import com.zerobase.shopping.commons.paging.Pagination;
import com.zerobase.shopping.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class SearchModel {
  private int page;
  private String word;
  private String searchType;
  private int delete;
  private int salesRate;
  private Pagination pagination;
  public SearchDto toDto() {
    return SearchDto.builder()
        .page(this.page)
        .word(this.word)
        .searchType(this.searchType)
        .pagination(this.pagination)
        .build();
  }

}
