package com.zerobase.shopping.inquiry.repository;

import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
  Page<InquiryEntity> findByProductIdAndDeleteYn(Pageable pageable, ProductEntity productEntity, int deleteYn);

}
