package com.zerobase.shopping.commons;

import com.zerobase.shopping.commons.dto.SearchOption;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageOptions {
  public static Pageable getPageable(SearchOption search, int page, int size) {
    Pageable pageable;
    Sort sort = Sort.by(search.getSort()).descending();
    if (search.getSortDirection().equals("asc")) {
      sort = Sort.by(search.getSort()).ascending();
    }
    pageable = PageRequest.of(page, size, sort);

    return pageable;
  }

}
