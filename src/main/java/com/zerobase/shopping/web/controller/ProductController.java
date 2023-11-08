package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.dto.ImgDto;
import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.model.ProductModel;
import com.zerobase.shopping.service.ImgService;
import com.zerobase.shopping.service.ProductService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
  private final  ImgService imgService;
  //상품등록
  @PostMapping("/save")
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
  //상품전체보기
  //상품상세보기
  //상품수정
  //상품삭제


}
