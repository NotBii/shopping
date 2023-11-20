package com.zerobase.shopping.commons.paging;

import com.zerobase.shopping.dto.SearchDto;
import com.zerobase.shopping.model.SearchModel;
import lombok.Getter;

@Getter
public class Pagination {
  private int totalRecordCount;
  private int totalPageCount;
  private int startPage;
  private int endPage;
  private int limitStart;
  private boolean existPrevPage;
  private boolean existNextPage;
  private final int PAGE_SIZE = 10;
  private final int LIST_SIZE = 10;

  public Pagination(int totalRecordCount, SearchModel searchModel) {
    if (totalRecordCount > 0) {
      this.totalRecordCount = totalRecordCount;
      calculation(searchModel);
    }
  }

  private void calculation(SearchModel searchModel) {

    totalPageCount = ((totalRecordCount - 1) / PAGE_SIZE) + 1;

    if (searchModel.getPage() > totalPageCount) {
      searchModel.setPage(totalPageCount);
    }

    startPage = ((searchModel.getPage() - 1) / PAGE_SIZE) * PAGE_SIZE + 1;

    endPage = startPage + PAGE_SIZE - 1;

    if (endPage > totalPageCount) {
      endPage = totalPageCount;
    }

    limitStart = (searchModel.getPage() - 1) * LIST_SIZE;

    existPrevPage = startPage != 1;
    existNextPage = (endPage * LIST_SIZE) < totalPageCount;
  }
}
