package com.zerobase.shopping.inquiry.repository;

import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.inquiry.repository.querydsl.InquiryRepositoryCustom;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long>,
    InquiryRepositoryCustom {

  Page<InquiryEntity> findByProductIdAndIsDeleted(Pageable pageable, ProductEntity productEntity,
      int isDeleted);

  Page <InquiryEntity> findByProductIdAndIsDeletedAndTitleContaining(Pageable pageable, ProductEntity entity, int isDeleted, String word);
  Page <InquiryEntity> findByProductIdAndIsDeletedAndWriter(Pageable pageable, ProductEntity entity, int isDeleted, MemberEntity writer);
  Page <InquiryEntity> findByProductIdAndIsDeletedAndContentContains(Pageable pageable, ProductEntity entity, int isDeleted, String content);

}
