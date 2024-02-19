package com.zerobase.shopping.review.service;

import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.img.service.ImgEntityConverter;
import com.zerobase.shopping.review.dto.ReviewDetail;
import com.zerobase.shopping.review.dto.ReviewListResponse;
import com.zerobase.shopping.review.dto.ReviewWriteRequest;
import com.zerobase.shopping.review.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewEntityConverter {
  private final GetEntity getEntity;

  public ReviewEntity toEntity(ReviewWriteRequest request) {

    return ReviewEntity.builder()
        .reviewId(request.getReviewId())
        .writer(getEntity.memberEntity(request.getWriter()))
        .title(request.getTitle())
        .content(request.getContent())
        .product(getEntity.productEntity(request.getProductId()))
        .imgList(ImgEntityConverter.dtoListToEntityList(request.getImgList()))
        .readCount(request.getReadCount())
        .score(request.getScore())
        .isDeleted(request.getIsDeleted())
        .build();
  }

  public ReviewListResponse toReviewListResponse(ReviewEntity entity) {
    return ReviewListResponse.builder()
        .reviewId(entity.getReviewId())
        .writer(entity.getWriter().getUsername())
        .title(entity.getTitle())
        .content(entity.getContent())
        .isDeleted(entity.getIsDeleted())
        .score(entity.getScore())
        .createdDate(entity.getCreatedDate())
        .build();
  }

  public ReviewDetail toReviewDetail(ReviewEntity entity) {
    return ReviewDetail.builder()
        .reviewId(entity.getReviewId())
        .writer(entity.getWriter().getUsername())
        .imgList(ImgEntityConverter.entityListToDtoList(entity.getImgList()))
        .title(entity.getTitle())
        .content(entity.getContent())
        .isDeleted(entity.getIsDeleted())
        .score(entity.getScore())
        .createdDate(entity.getCreatedDate())
        .modifiedDate(entity.getModifiedDate())
        .recommended(entity.getRecommended())
        .build();
  }
}
