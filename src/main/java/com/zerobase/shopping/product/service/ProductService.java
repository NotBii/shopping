package com.zerobase.shopping.product.service;

import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.commons.PageOptions;
import com.zerobase.shopping.commons.dto.SearchOption;
import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.product.dto.CreateProduct;
import com.zerobase.shopping.product.dto.ProductDetail;
import com.zerobase.shopping.product.dto.ProductSummary;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductEntityConverter productEntityConverter;
  private final GetEntity getEntity;

  /**
   * 상품등록
   *
   * @param dto : 등록 할 상품정보(CreateProduct)
   * @return 등록된 번호
   */
  public Long save(CreateProduct dto) {
    ProductEntity entity = productEntityConverter.createProductToEntity(dto);
    productRepository.save(entity);

    log.info(entity.getProductId() + "번 상품 등록 완료");

    return entity.getProductId();
  }

  /**
   * 상세보기
   * @param productId 상품번호
   * @return 상세보기 dto
   */
  public ProductDetail detail(Long productId) {
    ProductEntity entity = productRepository.findByProductId(productId).orElseThrow(NoResult::new);

    return productEntityConverter.toProductDetail(entity);
  }

  /**
   * 목록보기
   * @param page 페이지 번호
   * @param search 검색옵션
   * @param isDeleted 삭제여부
   * @return 상품요약dto Page객체
   */

  public Page<ProductSummary> list(int page, SearchOption search, int isDeleted) {
    Page<ProductSummary> result;
    String word = search.getWord();
    Pageable pageable = PageOptions.getPageable(search, page, 20);

    if (word != null) {
      result = productRepository.findAllByTitleContainingAndDeleteYn(pageable, word, isDeleted)
          .map(productEntityConverter::toProductSummary);
    } else {
      result = productRepository.findAllByDeleteYn(pageable, isDeleted)
          .map(productEntityConverter::toProductSummary);
    }

    return result;
  }

  /**
   * 수정
   * @param request 쓰기요청DTO
   * @return 글번호
   */
  public Long update(CreateProduct request) {
    ProductEntity updateEntity = productEntityConverter.createProductToEntity(request);
    productRepository.save(updateEntity);
    log.info(updateEntity.getProductId() + " 수정 완료");
    return updateEntity.getProductId();
  }

  /**
   * 삭제여부 수정
   * @param productId
   * @param deleteYn
   */
  public void changeDeleteYn(Long productId, int deleteYn) {
    ProductEntity entity = getEntity.productEntity(productId);
    entity.changeDeleteYn(deleteYn);
    if (deleteYn == 1) {
      log.info("상품 " + productId + "삭제");
    } else if (deleteYn == 0) {
      log.info("상품 " + productId + "복구");
    }
  }


}
