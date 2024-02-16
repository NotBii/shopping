package com.zerobase.shopping.product.service;

import com.zerobase.shopping.imgUpload.service.ImgEntityConverter;
import com.zerobase.shopping.imgUpload.service.ImgService;
import com.zerobase.shopping.product.dto.CreateProduct;
import com.zerobase.shopping.product.dto.ProductDetail;
import com.zerobase.shopping.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductEntityConverter {
  private final ImgService imgService;

  public ProductEntity createProductToEntity(CreateProduct dto) {
    return ProductEntity.builder()
        .productId(dto.getProductId())
        .price(dto.getPrice())
        .title(dto.getTitle())
        .content(dto.getContent())
        .stock(dto.getStock())
        .date(dto.getDate())
        .modifiedDate(dto.getModifiedDate())
        .imgList(imgService.getImgEntityList(dto.getImgList()))
        .build();
  }


  public ProductDetail toProductDetail(ProductEntity entity) {
    return ProductDetail.builder()
        .productId(entity.getProductId())
        .price(entity.getPrice())
        .title(entity.getTitle())
        .content(entity.getContent())
        .stock(entity.getStock())
        .date(entity.getDate())
        .modifiedDate(entity.getModifiedDate())
        .imgList(ImgEntityConverter.entityListToDtoList(entity.getImgList()))
        .build();
  }

}
