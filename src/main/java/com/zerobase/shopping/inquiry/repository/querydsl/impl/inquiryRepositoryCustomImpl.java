package com.zerobase.shopping.inquiry.repository.querydsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.inquiry.entity.QInquiryEntity;
import com.zerobase.shopping.inquiry.repository.querydsl.InquiryRepositoryCustom;
import com.zerobase.shopping.product.entity.ProductEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class inquiryRepositoryCustomImpl implements InquiryRepositoryCustom {
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<InquiryEntity> findByProductIdAndIsDeleted(Pageable pageable,
      ProductEntity productEntity, int deleteYn) {

    QInquiryEntity inquiry = QInquiryEntity.inquiryEntity;
    List<InquiryEntity> entities =jpaQueryFactory.selectFrom(inquiry)
                      .leftJoin(inquiry.parent)
                      .fetchJoin()
                      .where(inquiry.productId.productId.eq(productEntity.getProductId()))
                      .orderBy(inquiry.parent.createdDate.desc().nullsFirst(),
                          inquiry.createdDate.desc())
                      .offset(pageable.getOffset())
                      .limit(pageable.getPageSize())
                      .fetch();
    System.out.println(entities.toString());
    return new PageImpl<>(entities, pageable, entities.size());
  }
}
