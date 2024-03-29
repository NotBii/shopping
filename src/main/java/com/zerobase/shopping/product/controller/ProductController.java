package com.zerobase.shopping.product.controller;

import com.zerobase.shopping.commons.dto.SearchOption;
import com.zerobase.shopping.product.dto.CreateProduct;
import com.zerobase.shopping.product.dto.ProductDetail;
import com.zerobase.shopping.product.dto.ProductSummary;
import com.zerobase.shopping.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  /**
   * 상품등록
   *
   * @param request : createProduct
   * @return productId : 등록된 상품번호
   */
  @PostMapping("/save")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> addProduct(@RequestBody CreateProduct request) {

    Long productId = productService.save(request);

    return ResponseEntity.ok(productId);

  }

  /**
   * 상품상세보기
   *
   * @param productId - 상품번호
   * @return - productDto, imgDto가 있는 해쉬맵 반환
   */
  @GetMapping("/product-detail")
  public ResponseEntity<?> productDetail(@RequestParam Long productId) {

    ProductDetail result = this.productService.detail(productId);

    return ResponseEntity.ok(result);

  }

  /**
   * 상품목록보기
   *
   * @param page   - 현재 상품목록페이지
   * @param search SearchOption String type : title String word String sort: price, date boolean
   *               desc
   * @return - productDto 목록 list, 페이징 정보 pagination 반환
   */
  @GetMapping("/list")
  public ResponseEntity<Page<ProductSummary>> getProductList(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestBody com.zerobase.shopping.commons.dto.SearchOption search) {
    Page<ProductSummary> result = productService.list(page, search, 0);

    return ResponseEntity.ok(result);
  }

  /**
   * 삭제된 상품 보기
   * @param page
   * @param option
   * @return
   */
  @GetMapping("/deleted-list")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<Page<ProductSummary>> deletedProductList(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestBody SearchOption option) {
    Page<ProductSummary> result = productService.list(page, option, 1);

    return ResponseEntity.ok(result);
  }


  @PostMapping("/update")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateProduct(@RequestBody CreateProduct request) {
    long result = productService.update(request);
    return ResponseEntity.ok(result);
  }


  /**
   * 상품삭제
   *
   * @param productId - 상품번호
   * @return
   */
  @PostMapping("/delete")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
    productService.changeDeleteYn(productId, 1);

    return ResponseEntity.ok(productId);
  }

  /**
   * 상품복구
   *
   * @param productId
   * @return
   */
  @PostMapping("/recover")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> recoverProduct(@RequestParam Long productId) {
    productService.changeDeleteYn(productId, 0);

    return ResponseEntity.ok(productId);
  }


}
