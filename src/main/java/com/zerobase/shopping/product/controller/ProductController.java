package com.zerobase.shopping.product.controller;

import com.zerobase.shopping.img.service.ImgService;
import com.zerobase.shopping.product.dto.CreateProduct;
import com.zerobase.shopping.product.dto.ProductDetail;
import com.zerobase.shopping.product.dto.SearchOption;
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

  /**상품등록
   *
   * @param request :
   *                createProduct
   * @return productId : 등록된 상품번호
   */
  @PostMapping("/save")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> addProduct(@RequestBody CreateProduct request) {

    Long productId = productService.saveProduct(request);

    return ResponseEntity.ok(productId);

  }

  /**상품상세보기
   *
   * @param productId - 상품번호
   * @return - productDto, imgDto가 있는 해쉬맵 반환
   */
  @GetMapping("/product-detail")
  public ResponseEntity<?> productDetail(@RequestParam Long productId) {

    ProductDetail result = this.productService.getProductDetail(productId);


    return ResponseEntity.ok(result);

  }

  /**상품목록보기
   *
   * @param page - 현재 상품목록페이지
   * @param option
   *        SearchOption
   *        String type : title
   *        String word
   *        String sort: price, date
   *        boolean desc
   *
   * @return - productDto 목록 list, 페이징 정보 pagination 반환
   */
  @GetMapping("/product-list")
  public ResponseEntity<?> getProductList(@RequestParam(defaultValue = "0") int page,
      @RequestBody SearchOption option) {
    Page<ProductDetail> result = productService.getProductList(page, option);

    return ResponseEntity.ok(result);
  }



  @PostMapping("/update")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateProduct(@RequestBody CreateProduct request) {
    long result = productService.updateProduct(request);
    return ResponseEntity.ok(result);
  }


  /** 상품삭제
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
