package com.zerobase.shopping.commons.dto;

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
public class SearchOption {
  private String type;

  private String word;

  @Default
  private String sort = "inquiryId";

  @Default
  private String sortDirection = "desc";

}
