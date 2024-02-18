package com.zerobase.shopping.inquiry.repository;

import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
  Page<InquiryEntity> findByProductIdAndDeleteYn(Pageable pageable, Long productId, int deleteYn);

}
