package com.zerobase.shopping.inquiry.repository.querydsl;


import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepositoryCustom {

  Page<InquiryEntity> findByProductIdAndIsDeleted(Pageable pageable, ProductEntity productEntity,
      int isDeleted);

}
