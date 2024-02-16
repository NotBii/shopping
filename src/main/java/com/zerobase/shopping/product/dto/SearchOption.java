package com.zerobase.shopping.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchOption {
  private String type;
  private String word;
  private String sort;
  private String sortDirection;

}
