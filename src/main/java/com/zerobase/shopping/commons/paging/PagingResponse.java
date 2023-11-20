package com.zerobase.shopping.commons.paging;

import java.util.*;
import lombok.Getter;

@Getter
public class PagingResponse<T> {

  private List<T> list = new ArrayList<>();
  private Pagination pagination;

  public PagingResponse(List<T> list, Pagination pagination) {
    this.list.addAll(list);
    this.pagination = pagination;
  }

}
