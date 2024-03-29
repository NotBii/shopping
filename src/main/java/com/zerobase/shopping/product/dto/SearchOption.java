package com.zerobase.shopping.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchOption {
  private String type;
  private String word;
  @Default
  private String sort = "productId";
  @Default
  private String sortDirection = "desc";

}
