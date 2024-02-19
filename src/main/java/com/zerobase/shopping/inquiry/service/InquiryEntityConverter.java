package com.zerobase.shopping.inquiry.service;

import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.img.service.ImgEntityConverter;
import com.zerobase.shopping.inquiry.dto.InquiryDetail;
import com.zerobase.shopping.inquiry.dto.ListResponse;
import com.zerobase.shopping.inquiry.dto.WriteRequest;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InquiryEntityConverter {
  private final GetEntity getEntity;

  public InquiryEntity toEntity(WriteRequest request) {
    List<InquiryEntity> children = new ArrayList<>();

    if (request.getChildren() != null) {
      List<Long> childrenId = request.getChildren();

      for (Long id : childrenId) {
        children.add(getEntity.inquiryEntity(id));
      }
    }
    System.out.println(request.getParentId());
    return InquiryEntity.builder()
        .inquiryId(request.getInquiryId())
        .writer(getEntity.memberEntity(request.getWriter()))
        .title(request.getTitle())
        .content(request.getContent())
        .productId(getEntity.productEntity(request.getProductId()))
        .imgList(ImgEntityConverter.dtoListToEntityList(request.getImgList()))
        .isDeleted(request.getIsDeleted())
        .createdDate(request.getCreatedDate())
        .modifiedDate(request.getModifiedDate())
        .parent(request.getParentId() == null? null : getEntity.inquiryEntity(request.getParentId()))
        .children(children)
        .isSecret(request.getIsSecret())
        .build();
  }

  public InquiryDetail toDetail(InquiryEntity entity) {

    return InquiryDetail.builder()
        .inquiryId(entity.getInquiryId())
        .writer(entity.getWriter().getUsername())
        .title(entity.getTitle())
        .content(entity.getContent())
        .productId(entity.getProductId().getProductId())
        .imgList(ImgEntityConverter.entityListToDtoList(entity.getImgList()))
        .isDeleted(entity.getIsDeleted())
        .createdDate(entity.getCreatedDate())
        .modifiedDate(entity.getModifiedDate())
        .parentId(entity.getParent() == null? null : entity.getParent().getInquiryId())
        .childrenId(entity.getChildren() == null? null: entity.getChildren().stream().map(InquiryEntity::getInquiryId).toList())
        .build();
  }

  public ListResponse toListResponse(InquiryEntity entity) {
    return ListResponse.builder()
        .inquiryId(entity.getInquiryId())
        .writer(entity.getWriter().getUsername())
        .readCount(entity.getReadCount())
        .content(entity.getContent())
        .isDeleted(entity.getIsDeleted())
        .createdDate(entity.getCreatedDate())
        .productId(entity.getProductId().getProductId())
        .productName(entity.getProductId().getTitle())
        .parentId(entity.getParent()== null? null : entity.getParent().getInquiryId())
        .childrenId(entity.getChildren() == null? null : entity.getChildren().stream().map(InquiryEntity::getInquiryId).toList())
        .build();
  }

}
