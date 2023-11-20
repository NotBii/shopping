package com.zerobase.shopping.service;

import com.zerobase.shopping.commons.paging.Pagination;
import com.zerobase.shopping.commons.paging.PagingResponse;
import com.zerobase.shopping.dao.ProductDao;
import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.dto.SearchDto;
import com.zerobase.shopping.model.SearchModel;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductDao productDao;

  /**
   * 상품등록
   * @param productDto : 등록할상품정보
   * @return 등록된 번호
   */
  public int saveProduct(ProductDto productDto) {
    this.productDao.saveProduct(productDto);
    log.info(productDto.getNo() + "번 상품 등록 완료");
    return productDto.getNo();
  }

  public ProductDto getProductDetail(int no) {
    return this.productDao.getProductDetail(no);
  }

  /**
   *
   * @param searchModel : 검색정보
   * @return
   */
  public PagingResponse<ProductDto> getProductList(final SearchModel searchModel) {
    int count = productDao.count(searchModel.toDto());

    if (count < 1) {
      return new PagingResponse<>(Collections.emptyList(), null);
    }

    Pagination pagination = new Pagination(count, searchModel);
    searchModel.setPagination(pagination);
    List<ProductDto> result = this.productDao.getProductList(searchModel.toDto());
    log.info(result.toString());

    return new PagingResponse<>(result, pagination);
  }

  public ProductDto updateProduct(ProductDto productDto) {
    this.productDao.updateProduct(productDto);
    ProductDto result = this.productDao.getProductDetail(productDto.getNo());
    log.info("상품 " + productDto.getNo() + "수정");
    return result;
  }

  public void deleteProduct(int no) {
    this.productDao.deleteProduct(no);
    log.info("상품 " + no + "삭제");
  }

  public void updateSalesRate(int no) {
    this.productDao.updateSalesRate(no);
  }
}
