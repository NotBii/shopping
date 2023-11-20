package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.commons.paging.PagingResponse;
import com.zerobase.shopping.dto.ImgDto;
import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.model.ImgUpdateModel;
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
  private final ProductService productService;
  private final ImgService imgService;

  /**상품등록
   *
   * @param uploadImgs -이미지 파일
   * @param productModel - 상품정보
   * @return
   */
  @PostMapping("/save")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
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

  /**상품상세보기
   *
   * @param no - 상품번호
   * @return - productDto, imgDto가 있는 해쉬맵 반환
   */
  @GetMapping("/product-detail")
  public ResponseEntity<?> productDetail(@RequestParam int no) {
    HashMap<String, Object> result = new HashMap<>();

    ProductDto productDto = this.productService.getProductDetail(no);
    List<ImgDto> imgDto = this.imgService.findAllByNo(productDto.getImg());
    result.put("productDto", productDto);
    result.put("imgDto", imgDto);

    return ResponseEntity.ok(result);

  }

  /**상품목록보기
   *
   * @param page - 현재 상품목록페이지
   * @param word  - 검색어
   * @param searchType - 검색타입(카테고리, 이름)
   * @return - productDto 목록 list, 페이징 정보 pagination 반환
   */
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
    PagingResponse<ProductDto> result = this.productService.getProductList(searchModel);
    return ResponseEntity.ok(result);
  }

  /** 상품수정
   *
   * @param uploadImgs 사진을 추가할 경우 사용
   * @param productModel 수정된 글의 model
   * @param imgUpdateModel 글에 첨부된 사진 목록의 추가 혹은 제거할 경우 사용, 이미지 삭제시 removeNo(List)로 해당 번호 전달
   * @return
   */
  @PostMapping("/update")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateProduct(@RequestPart(value = "imgs", required = false) List<MultipartFile> uploadImgs
                                        ,@RequestPart(value = "ProductModel") ProductModel productModel
                                        ,@RequestPart(value = "ImgUpdateModel") ImgUpdateModel imgUpdateModel) {

    if (imgUpdateModel.getRemove()==1) {
      String deletedImg = this.imgService.deleteAllByNo(productModel.getImg(), imgUpdateModel.getRemoveNo());
      productModel.setImg(deletedImg);
    }
    if (imgUpdateModel.getAdd()==1) {
      String updateImg = this.imgService.updateImg(productModel, uploadImgs, imgUpdateModel);
      productModel.setImg(updateImg);
    }

    ProductDto result = this.productService.updateProduct(productModel.toDto());
    return ResponseEntity.ok(result);
  }


  /** 상품삭제
   *
   * @param no - 상품번호
   * @return
   */
  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> deleteProduct(@RequestParam int no) {
    this.productService.deleteProduct(no);

    return ResponseEntity.ok(no + " 삭제완료");
  }


}
