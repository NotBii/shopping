package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.dto.ImgDto;
import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.dto.SearchDto;
import com.zerobase.shopping.model.ProductModel;
import com.zerobase.shopping.model.SearchModel;
import com.zerobase.shopping.service.ImgService;
import com.zerobase.shopping.service.ProductService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor

public class ProductController {
  private final ProductModel productModel;
  private final ProductService productService;
  private final ImgService imgService;
  //상품등록
  @PostMapping("/save")
  @PreAuthorize("ROLE_MANAGER")
  public ResponseEntity<?> addProduct(@RequestPart(value = "imgs", required = false) List<MultipartFile> uploadImgs, @RequestPart(value = "ProductModel") ProductModel productModel) {
    if (uploadImgs != null) {
      List<ImgDto> imgs = imgService.uploadImgs(uploadImgs);
      String fileNo = imgService.saveImgs(imgs);
      productModel.setImg(fileNo);
    }
    ProductDto productDto = productModel.toDto();
    int result = this.productService.saveProduct(productDto);

    return ResponseEntity.ok(result);

  }
  //상품상세보기
  @GetMapping("/product-detail")
  public ResponseEntity<?> productDetail(@RequestParam int no) {
  ProductDto result = this.productService.getProductDetail(no);

  return ResponseEntity.ok(result);
  }

  //상품전체보기
  @GetMapping("/product-list")
  public ResponseEntity<?> getProductList(@RequestParam(value ="page", required = false, defaultValue = "1") int page
                                        ,@RequestParam(value ="word", required = false) String word
                                        ,@RequestParam(value ="type", required = false) String searchType) {
    SearchModel searchModel = SearchModel.builder()
        .page(page)
        .word(word)
        .searchType(searchType)
        .build();
    log.info(searchModel.toString());
    List<ProductDto> result = this.productService.getProductList(searchModel.toDto());
    return ResponseEntity.ok(result);
  }

  //상품수정
  @PostMapping("/update")
  @PreAuthorize("ROLE_MANAGER")
  public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
    ProductDto result = this.productService.updateProduct(productDto);
    return ResponseEntity.ok(result);
  }


  //상품삭제
  @DeleteMapping("/delete")
  @PreAuthorize("ROLE_MANAGER")
  public ResponseEntity<?> deleteProduct(@RequestParam int no) {
    this.productService.deleteProduct(no);

    return ResponseEntity.ok(null);
  }


}
