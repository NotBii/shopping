package com.zerobase.shopping.product.service;

import com.zerobase.shopping.img.service.ImgEntityConverter;
import com.zerobase.shopping.img.service.ImgService;
import com.zerobase.shopping.product.dto.CreateProduct;
import com.zerobase.shopping.product.dto.ProductDetail;
import com.zerobase.shopping.product.dto.ProductSummary;
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
        .createdDate(dto.getCreatedDate())
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
        .createdDate(entity.getCreatedDate())
        .modifiedDate(entity.getModifiedDate())
        .imgList(ImgEntityConverter.entityListToDtoList(entity.getImgList()))
        .build();
  }

  public ProductSummary toProductSummary(ProductEntity entity) {
    return ProductSummary.builder()
        .productId(entity.getProductId())
        .price(entity.getPrice())
        .title(entity.getTitle())
        .stock(entity.getStock())
        .createdDate(entity.getCreatedDate())
        .img(ImgEntityConverter.getFirstImgDto(entity.getImgList()))
        .build();
  }

}
