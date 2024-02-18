package com.zerobase.shopping.inquiry.service;

import com.zerobase.shopping.img.service.ImgEntityConverter;
import com.zerobase.shopping.img.service.ImgService;
import com.zerobase.shopping.inquiry.dto.ListResponse;
import com.zerobase.shopping.inquiry.dto.WriteRequest;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.member.service.MemberService;
import com.zerobase.shopping.product.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InquiryEntityConverter {
  private final ImgService imgService;
  private final MemberService memberService;
  private final ProductService productService;

  public InquiryEntity toEntity(WriteRequest request) {

    return InquiryEntity.builder()
        .inquiryId(request.getInquiryId())
        .writer(memberService.getMemberEntity(request.getWriter()))
        .title(request.getTitle())
        .content(request.getContent())
        .productId(productService.getProductEntity(request.getProductId()))
        .imgList(ImgEntityConverter.dtoListToEntityList(request.getImgList()))
        .deleteYn(request.getDeleteYn())
        .createdDate(request.getCreatedDate())
        .modifiedDate(request.getModifiedDate())
        .build();
  }

  public WriteRequest toDetail(InquiryEntity entity) {
    return WriteRequest.builder()
        .inquiryId(entity.getInquiryId())
        .writer(entity.getWriter().getUsername())
        .title(entity.getTitle())
        .content(entity.getContent())
        .productId(entity.getProductId().getProductId())
        .imgList(ImgEntityConverter.entityListToDtoList(entity.getImgList()))
        .deleteYn(entity.getDeleteYn())
        .createdDate(entity.getCreatedDate())
        .modifiedDate(entity.getModifiedDate())
        .build();
  }

  public ListResponse toListResponse(InquiryEntity entity) {
    return ListResponse.builder()
        .inquiryId(entity.getInquiryId())
        .writer(entity.getWriter().getUsername())
        .readCount(entity.getReadCount())
        .content(entity.getContent())
        .deleteYn(entity.getDeleteYn())
        .createdDate(entity.getCreatedDate())
        .productId(entity.getProductId().getProductId())
        .productName(entity.getProductId().getTitle())
        .build();
  }

}
