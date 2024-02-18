package com.zerobase.shopping.product.service;

import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.product.dto.CreateProduct;
import com.zerobase.shopping.product.dto.ProductDetail;
import com.zerobase.shopping.product.dto.SearchOption;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductEntityConverter productEntityConverter;

  /**
   * 상품등록
   *
   * @param dto : 등록 할 상품정보(CreateProduct)
   * @return 등록된 번호
   */
  public Long saveProduct(CreateProduct dto) {
    dto.setCreatedDate(LocalDateTime.now());
    ProductEntity entity = productEntityConverter.createProductToEntity(dto);
    productRepository.save(entity);

    log.info(entity.getProductId() + "번 상품 등록 완료");

    return entity.getProductId();
  }

  public ProductDetail getProductDetail(Long productId) {
    ProductEntity entity = productRepository.findByProductId(productId).orElseThrow(NoResult::new);

    return productEntityConverter.toProductDetail(entity);
  }

  public Page<ProductDetail> getProductList(int page, SearchOption option) {
    Page<ProductDetail> result;
    Pageable pageable;
    String word = option.getWord();
    String direction = option.getSortDirection();
    if (direction.equals("asc")) {
      pageable = PageRequest.of(page, 20, Sort.by(option.getSort()).ascending());
      System.out.println("asc");
    } else {
      pageable = PageRequest.of(page, 20, Sort.by(option.getSort()).descending());
      System.out.println("dsc");
    }

    if (word != null) {
      result = productRepository.findAllByTitleContainingAndDeleteYn(pageable, word, 0)
          .map(productEntityConverter::toProductDetail);
    } else {
      result = productRepository.findAllByDeleteYn(pageable, 0)
          .map(productEntityConverter::toProductDetail);
    }

    return result;
  }


  public Long updateProduct(CreateProduct request) {
    request.setModifiedDate(LocalDateTime.now());
    ProductEntity updateEntity = productEntityConverter.createProductToEntity(request);
    productRepository.save(updateEntity);
    log.info(updateEntity.getProductId() + " 수정 완료");
    return updateEntity.getProductId();
  }

  public void changeDeleteYn(Long productId, int deleteYn) {
    ProductEntity entity = getProductEntity(productId);
    entity.changeDeleteYn(deleteYn);
    if (deleteYn == 1) {
      log.info("상품 " + productId + "삭제");
    } else if (deleteYn == 0) {
      log.info("상품 " + productId + "복구");
    }
  }

   public ProductEntity getProductEntity(Long productId) {
    return productRepository.findByProductId(productId).orElseThrow(NoResult::new);
  }
}
